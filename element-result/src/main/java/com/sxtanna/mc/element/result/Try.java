package com.sxtanna.mc.element.result;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.sxtanna.mc.element.result.throwing.ExceptionalConsumer;
import com.sxtanna.mc.element.result.throwing.ExceptionalFunction;
import com.sxtanna.mc.element.result.throwing.ExceptionalRunnable;
import com.sxtanna.mc.element.result.throwing.ExceptionalSupplier;

import java.util.Optional;

public enum Try
{
    ;


    public static void run(@NotNull final ExceptionalRunnable runnable)
    {
        try
        {
            runnable.run();
        }
        catch (final Throwable ignored)
        {
            /* ignored exception */
        }
    }

    public static <T> @Nullable T get(@NotNull final ExceptionalSupplier<@Nullable T> supplier)
    {
        try
        {
            return supplier.get();
        }
        catch (final Throwable ignored)
        {
            return null;
        }
    }


    public static <R extends AutoCloseable> void twr(@NotNull final R resource, @NotNull final ExceptionalConsumer<@NotNull R> consumer)
    {
        try (resource)
        {
            consumer.accept(resource);
        }
        catch (final Throwable ex)
        {
            /* ignored exception */
        }
    }

    public static <R extends AutoCloseable, T> @Nullable T twr(@NotNull final R resource, @NotNull final ExceptionalFunction<@NotNull R, @Nullable T> function)
    {
        try (resource)
        {
            return function.apply(resource);
        }
        catch (final Throwable ex)
        {
            return null;
        }
    }


    public static <T> @NotNull Optional<T> opt(@NotNull final ExceptionalSupplier<@Nullable T> supplier)
    {
        return Opt.of(get(supplier));
    }

}
