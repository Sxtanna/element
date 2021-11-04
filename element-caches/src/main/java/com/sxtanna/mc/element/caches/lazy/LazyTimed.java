package com.sxtanna.mc.element.caches.lazy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public final class LazyTimed<T> implements Lazy<T>
{

    @NotNull
    final AtomicReference<T>       stored = new AtomicReference<>();
    @NotNull
    final AtomicReference<Instant> expire = new AtomicReference<>();


    @NotNull
    private final Duration    duration;
    @NotNull
    private final Supplier<T> supplier;


    LazyTimed(@NotNull final Duration duration, @NotNull final Supplier<@NotNull T> supplier)
    {
        this.duration = duration;
        this.supplier = supplier;
    }


    public @Nullable T reset()
    {
        return this.stored.getAndSet(null);
    }


    @Override
    public @NotNull T get()
    {
        final var unt = this.expire.get();
        final var val = this.stored.get();

        if (val == null || unt == null || Instant.now().isAfter(unt))
        {
            final var next = this.supplier.get();

            this.stored.set(next);
            this.expire.set(Instant.now().plus(this.duration));

            return next;
        }

        return val;
    }

}
