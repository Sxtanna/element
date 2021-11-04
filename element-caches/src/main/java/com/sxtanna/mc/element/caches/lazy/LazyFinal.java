package com.sxtanna.mc.element.caches.lazy;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public final class LazyFinal<T> implements Lazy<T>
{

    @NotNull
    final AtomicReference<T> stored = new AtomicReference<>();


    @NotNull
    private final Supplier<T> supplier;


    LazyFinal(@NotNull final Supplier<T> supplier)
    {
        this.supplier = supplier;
    }


    @Override
    public @NotNull T get()
    {
        final var prev = this.stored.get();
        if (prev != null)
        {
            return prev;
        }

        final var next = this.supplier.get();

        this.stored.set(next);

        return next;
    }

}
