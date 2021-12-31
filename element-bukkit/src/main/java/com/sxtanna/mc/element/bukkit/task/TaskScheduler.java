package com.sxtanna.mc.element.bukkit.task;

import org.jetbrains.annotations.NotNull;

import org.bukkit.scheduler.BukkitTask;

import com.sxtanna.mc.element.bukkit.Tasks;
import com.sxtanna.mc.element.result.throwing.ExceptionalSupplier;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface TaskScheduler
{

    @NotNull BukkitTask main(@NotNull final Runnable runnable);

    @NotNull BukkitTask work(@NotNull final Runnable runnable);


    @NotNull BukkitTask delay(@NotNull final Tasks.Context context, final long time, @NotNull final Runnable runnable);

    @NotNull BukkitTask delay(@NotNull final Tasks.Context context, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable);


    @NotNull BukkitTask timer(@NotNull final Tasks.Context context, final long time, @NotNull final Runnable runnable);

    @NotNull BukkitTask timer(@NotNull final Tasks.Context context, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable);


    @NotNull BukkitTask timer(@NotNull final Tasks.Context context, final long time, final boolean now, @NotNull final Runnable runnable);

    @NotNull BukkitTask timer(@NotNull final Tasks.Context context, final long time, @NotNull final TimeUnit unit, final boolean now, @NotNull final Runnable runnable);


    @NotNull CompletableFuture<Void> mainAwait(@NotNull final Runnable runnable);

    @NotNull CompletableFuture<Void> workAwait(@NotNull final Runnable runnable);


    <T> @NotNull CompletableFuture<T> mainAwait(@NotNull final ExceptionalSupplier<T> supplier);

    <T> @NotNull CompletableFuture<T> workAwait(@NotNull final ExceptionalSupplier<T> supplier);

}
