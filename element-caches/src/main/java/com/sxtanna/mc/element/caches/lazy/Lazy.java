package com.sxtanna.mc.element.caches.lazy;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@FunctionalInterface
public interface Lazy<T> extends Supplier<T>
{

    @Override
    @NotNull T get();


    @Contract(value = "_ -> new", pure = true)
    static <T> @NotNull Lazy<T> lazy(@NotNull final Supplier<@NotNull T> supplier)
    {
        return new LazyFinal<>(supplier);
    }

    @Contract(value = "_ -> new", pure = true)
    static <T> @NotNull Lazy<T> weak(@NotNull final Supplier<@NotNull T> supplier)
    {
        final var lazy = new LazyFinal<>(() -> new WeakReference<>(supplier.get()));

        return () ->
        {
            final var prev = lazy.get().get();
            if (prev != null)
            {
                return prev;
            }

            final var next = supplier.get();

            lazy.stored.set(new WeakReference<>(next));

            return next;
        };
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    static <T> @NotNull LazyTimed<T> time(final long time, @NotNull final TimeUnit unit, @NotNull final Supplier<@NotNull T> supplier)
    {
        return new LazyTimed<>(Duration.of(time, unit.toChronoUnit()), supplier);
    }

}
