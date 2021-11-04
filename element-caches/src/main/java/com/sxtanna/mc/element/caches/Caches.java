package com.sxtanna.mc.element.caches;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.caches.lazy.Lazy;
import com.sxtanna.mc.element.caches.lazy.LazyTimed;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public enum Caches
{
    ;

    @Contract(value = "-> new", pure = true)
    static <K, V> @NotNull Caffeine<K, V> caffeine()
    {
        //noinspection unchecked
        return (Caffeine<K, V>) Caffeine.newBuilder();
    }


    @Contract(value = "_ -> new", pure = true)
    static <T> @NotNull Lazy<T> lazy(@NotNull final Supplier<@NotNull T> supplier)
    {
        return Lazy.lazy(supplier);
    }

    @Contract(value = "_ -> new", pure = true)
    static <T> @NotNull Lazy<T> weak(@NotNull final Supplier<@NotNull T> supplier)
    {
        return Lazy.weak(supplier);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    static <T> @NotNull LazyTimed<T> time(final long time, @NotNull final TimeUnit unit, @NotNull final Supplier<@NotNull T> supplier)
    {
        return Lazy.time(time, unit, supplier);
    }

}
