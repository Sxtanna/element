package com.sxtanna.mc.element.remote.mysql;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.future.Async;
import com.sxtanna.mc.element.remote.RemoteDatabase;
import com.sxtanna.mc.element.result.Res;
import com.sxtanna.mc.element.result.handling.Result;
import com.sxtanna.mc.element.result.throwing.ExceptionalConsumer;
import com.sxtanna.mc.element.result.throwing.ExceptionalFunction;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class RemoteDatabaseMySQL implements RemoteDatabase<Connection>
{

    @NotNull
    private static final String MARIADB_FQN = "org.mariadb.jdbc.Driver";
    @NotNull
    private static final String MARIADB_URL = "jdbc:mariadb://%s:%d/%s?useSSL=false";


    @NotNull
    private final HikariConfig                      config;
    @NotNull
    private final AtomicBoolean                     loaded = new AtomicBoolean();
    @NotNull
    private final AtomicReference<HikariDataSource> source = new AtomicReference<>();


    public RemoteDatabaseMySQL(@NotNull final String host, final int port, @NotNull final String username, @NotNull final String password, @NotNull final String database)
    {
        final var config = new HikariConfig();

        config.setUsername(username);
        config.setPassword(password);

        config.setDriverClassName(MARIADB_FQN);
        config.setJdbcUrl(MARIADB_URL.formatted(host, port, database));

        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("cacheCallableStmts", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("useLocalSessionState", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("alwaysSendSetIsolation", false);

        config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");

        this.config = config;
    }


    @Override
    public void load()
    {
        if (this.loaded.get())
        {
            return;
        }

        final var prev = this.source.getAndSet(new HikariDataSource(this.config));
        if (prev != null)
        {
            prev.close();
        }

        this.loaded.set(true);
    }

    @Override
    public void kill()
    {
        if (!this.loaded.get())
        {
            return;
        }

        final var prev = this.source.getAndSet(null);
        if (prev != null)
        {
            prev.close();
        }

        this.loaded.set(false);
    }


    @Override
    public boolean ready()
    {
        return this.loaded.get();
    }


    @Override
    public @NotNull Result<Connection> connect()
    {
        final var source = this.source.get();

        if (source != null)
        {
            return Res.of(() -> source.getConnection());
        }
        else
        {
            return Res.failure(new IllegalStateException("Database is not loaded"));
        }
    }


    @Contract("_ -> new")
    public @NotNull CompletableFuture<Void> run(@NotNull final ExceptionalConsumer<Connection> consumer)
    {
        return Async.run(() -> {
            if (!this.ready())
            {
                spinWaitUntilReady(this.loaded, 5L, TimeUnit.SECONDS).join();
            }

            try (final var connection = this.connect().asSuccess().some())
            {
                consumer.accept(connection);
            }
            catch (final Throwable ex)
            {
                throw new CompletionException(ex);
            }
        });
    }

    @Contract("_ -> new")
    public <T> @NotNull CompletableFuture<T> get(@NotNull final ExceptionalFunction<Connection, T> function)
    {
        return Async.get(() -> {
            if (!this.ready())
            {
                spinWaitUntilReady(this.loaded, 5L, TimeUnit.SECONDS).join();
            }

            try (final var connection = this.connect().asSuccess().some())
            {
                return function.apply(connection);
            }
            catch (final Throwable ex)
            {
                throw new CompletionException(ex);
            }
        });
    }


    private static CompletableFuture<Void> spinWaitUntilReady(@NotNull final AtomicBoolean ready, final long timeoutTime, @NotNull final TimeUnit timeoutUnit)
    {
        return Async.run(() -> {
            while (!ready.get())
            {
                Thread.onSpinWait();
            }
        }).orTimeout(timeoutTime, timeoutUnit);
    }

}
