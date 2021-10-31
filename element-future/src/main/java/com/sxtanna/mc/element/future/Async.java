package com.sxtanna.mc.element.future;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.sxtanna.mc.element.result.throwing.ExceptionalRunnable;
import com.sxtanna.mc.element.result.throwing.ExceptionalSupplier;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public enum Async
{
    ;


    @Contract(value = " -> new", pure = true)
    public static <T> @NotNull CompletableFuture<T> await()
    {
        return new CompletableFuture<>();
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T> @NotNull CompletableFuture<T> await(final long time, @NotNull final TimeUnit unit)
    {
        return new CompletableFuture<T>().orTimeout(time, unit);
    }


    public static <T> @NotNull CompletableFuture<T> none()
    {
        return done(null);
    }

    public static <T> @NotNull CompletableFuture<T> done(@Nullable final T value)
    {
        return CompletableFuture.completedFuture(value);
    }

    public static <T> @NotNull CompletableFuture<T> fail(@NotNull final Throwable ex)
    {
        return CompletableFuture.failedFuture(ex);
    }


    @Contract("_ -> new")
    public static @NotNull CompletableFuture<Void> run(@NotNull final ExceptionalRunnable runnable)
    {
        return CompletableFuture.runAsync(() -> {
            try
            {
                runnable.run();
            }
            catch (final Throwable ex)
            {
                throw new CompletionException(ex);
            }
        });
    }

    @Contract("_ -> new")
    public static <O> @NotNull CompletableFuture<O> get(@NotNull final ExceptionalSupplier<@Nullable O> supplier)
    {
        return CompletableFuture.supplyAsync(() -> {
            try
            {
                return supplier.get();
            }
            catch (final Throwable ex)
            {
                throw new CompletionException(ex);
            }
        });
    }


    public static <T> @NotNull CompletableFuture<List<T>> all(@NotNull final Collection<CompletableFuture<T>> futures)
    {
        final var future = new CompletableFuture<List<T>>();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                         .thenApplyAsync($ -> futures.stream().map(CompletableFuture::join).toList())
                         .whenComplete((pass, fail) -> {
                             if (fail == null)
                             {
                                 future.complete(pass);
                             }
                             else
                             {
                                 future.completeExceptionally(fail);
                             }
                         });

        return future;
    }


    @Contract(" -> new")
    public static <T> @NotNull Collector<CompletableFuture<T>, ?, CompletableFuture<List<T>>> collector()
    {
        return Collectors.collectingAndThen(Collectors.toList(), Async::all);
    }

}
