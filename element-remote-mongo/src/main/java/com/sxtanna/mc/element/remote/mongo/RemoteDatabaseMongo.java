package com.sxtanna.mc.element.remote.mongo;

import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.remote.RemoteDatabase;
import com.sxtanna.mc.element.result.Res;
import com.sxtanna.mc.element.result.handling.Result;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class RemoteDatabaseMongo implements RemoteDatabase<MongoClient>
{

    @NotNull
    private final String                       config;
    @NotNull
    private final AtomicBoolean                loaded = new AtomicBoolean();
    @NotNull
    private final AtomicReference<MongoClient> client = new AtomicReference<>();


    public RemoteDatabaseMongo(@NotNull final String connectionString)
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

        final var prev = this.client.getAndSet(MongoClients.create(this.config));
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

        final var prev = this.client.getAndSet(null);
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
    public @NotNull Result<MongoClient> connect()
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
