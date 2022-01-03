package com.sxtanna.mc.element.remote;

import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.common.state.State;
import com.sxtanna.mc.element.future.Async;
import com.sxtanna.mc.element.result.handling.Result;

import java.util.concurrent.CompletableFuture;

public interface RemoteDatabase<C> extends State
{

    @Override
    void load();

    @Override
    void kill();


    default boolean ready()
    {
        return true;
    }


    @NotNull Result<C> connect();


    default @NotNull CompletableFuture<C> connectAsync()
    {
        return Async.get(this::connect).thenApply(it -> it.asSuccess().some());
    }

}
