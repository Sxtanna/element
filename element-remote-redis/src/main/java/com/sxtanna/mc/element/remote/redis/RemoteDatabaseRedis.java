package com.sxtanna.mc.element.remote.redis;

import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.remote.RemoteDatabase;
import com.sxtanna.mc.element.result.Res;
import com.sxtanna.mc.element.result.handling.Result;

import io.lettuce.core.RedisClient;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class RemoteDatabaseRedis implements RemoteDatabase<RedisClient>
{

    @NotNull
    private final String                       config;
    @NotNull
    private final AtomicBoolean                loaded = new AtomicBoolean();
    @NotNull
    private final AtomicReference<RedisClient> client = new AtomicReference<>();


    public RemoteDatabaseRedis(@NotNull final String connectionString)
    {
        this.config = connectionString;
    }


    @Override
    public void load()
    {
        if (this.loaded.get())
        {
            return;
        }

        final var prev = this.client.getAndSet(RedisClient.create(this.config));
        if (prev != null)
        {
            prev.shutdown();
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

        final var prev = this.client.getAndSet(null);
        if (prev != null)
        {
            prev.shutdown();
        }

        this.loaded.set(false);
    }


    @Override
    public boolean ready()
    {
        return this.loaded.get();
    }


    @Override
    public @NotNull Result<RedisClient> connect()
    {
        final var client = this.client.get();

        if (client != null)
        {
            return Res.success(client);
        }
        else
        {
            return Res.failure(new IllegalStateException("Database is not loaded"));
        }
    }

}
