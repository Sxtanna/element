package com.sxtanna.mc.element.bukkit;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.sxtanna.mc.element.future.Async;
import com.sxtanna.mc.element.result.throwing.ExceptionalSupplier;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public enum Tasks
{
    ;


    public enum Context
    {
        MAIN,
        WORK,
    }


    public static @NotNull BukkitTask main(@NotNull final Plugin plugin, @NotNull final Runnable runnable)
    {
        return Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public static @NotNull BukkitTask work(@NotNull final Plugin plugin, @NotNull final Runnable runnable)
    {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }


    public static @NotNull BukkitTask delay(@NotNull final Plugin plugin, @NotNull final Context context, final long time, @NotNull final Runnable runnable)
    {
        return switch (context)
                {
                    case MAIN -> Tasks.Main.delay(plugin, time, runnable);
                    case WORK -> Tasks.Work.delay(plugin, time, runnable);
                };
    }

    public static @NotNull BukkitTask delay(@NotNull final Plugin plugin, @NotNull final Context context, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable)
    {
        return switch (context)
                {
                    case MAIN -> Tasks.Main.delay(plugin, time, unit, runnable);
                    case WORK -> Tasks.Work.delay(plugin, time, unit, runnable);
                };
    }


    public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, @NotNull final Context context, final long time, @NotNull final Runnable runnable)
    {
        return timer(plugin, context, time, false, runnable);
    }

    public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, @NotNull final Context context, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable)
    {
        return timer(plugin, context, time, unit, false, runnable);
    }


    public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, @NotNull final Context context, final long time, final boolean now, @NotNull final Runnable runnable)
    {
        return switch (context)
                {
                    case MAIN -> Tasks.Main.timer(plugin, time, now, runnable);
                    case WORK -> Tasks.Work.timer(plugin, time, now, runnable);
                };
    }

    public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, @NotNull final Context context, final long time, @NotNull final TimeUnit unit, final boolean now, @NotNull final Runnable runnable)
    {
        return switch (context)
                {
                    case MAIN -> Tasks.Main.timer(plugin, time, unit, now, runnable);
                    case WORK -> Tasks.Work.timer(plugin, time, unit, now, runnable);
                };
    }


    public static @NotNull CompletableFuture<Void> mainAwait(@NotNull final Plugin plugin, @NotNull final Runnable runnable)
    {
        final var done = Async.<Void>await();
        final var task = main(plugin, () ->
        {
            try
            {
                runnable.run();
                done.complete(null);
            }
            catch (final Throwable ex)
            {
                done.completeExceptionally(ex);
            }
        });

        return done.whenComplete(($, error) -> {
            if (error instanceof CancellationException)
            {
                task.cancel();
            }
        });
    }

    public static @NotNull CompletableFuture<Void> workAwait(@NotNull final Plugin plugin, @NotNull final Runnable runnable)
    {
        final var done = Async.<Void>await();
        final var task = work(plugin, () ->
        {
            try
            {
                runnable.run();
                done.complete(null);
            }
            catch (final Throwable ex)
            {
                done.completeExceptionally(ex);
            }
        });

        return done.whenComplete(($, error) -> {
            if (error instanceof CancellationException)
            {
                task.cancel();
            }
        });
    }

    public static <T> @NotNull CompletableFuture<T> mainAwait(@NotNull final Plugin plugin, @NotNull final ExceptionalSupplier<T> supplier)
    {
        final var done = Async.<T>await();
        final var task = main(plugin, () ->
        {
            try
            {
                done.complete(supplier.get());
            }
            catch (final Throwable ex)
            {
                done.completeExceptionally(ex);
            }
        });

        return done.whenComplete(($, error) -> {
            if (error instanceof CancellationException)
            {
                task.cancel();
            }
        });
    }

    public static <T> @NotNull CompletableFuture<T> workAwait(@NotNull final Plugin plugin, @NotNull final ExceptionalSupplier<T> supplier)
    {
        final var done = Async.<T>await();
        final var task = work(plugin, () ->
        {
            try
            {
                done.complete(supplier.get());
            }
            catch (final Throwable ex)
            {
                done.completeExceptionally(ex);
            }
        });

        return done.whenComplete(($, error) -> {
            if (error instanceof CancellationException)
            {
                task.cancel();
            }
        });
    }


    public enum Main
    {
        ;

        public static @NotNull BukkitTask delay(@NotNull final Plugin plugin, final long time, @NotNull final Runnable runnable)
        {
            return Bukkit.getScheduler().runTaskLater(plugin, runnable, time);
        }

        public static @NotNull BukkitTask delay(@NotNull final Plugin plugin, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable)
        {
            return Bukkit.getScheduler().runTaskLater(plugin, runnable, Ticks.convertToTicks(time, unit));
        }


        public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, final long time, @NotNull final Runnable runnable)
        {
            return timer(plugin, time, false, runnable);
        }

        public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable)
        {
            return timer(plugin, time, unit, false, runnable);
        }


        public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, final long time, final boolean now, @NotNull final Runnable runnable)
        {
            return Bukkit.getScheduler().runTaskTimer(plugin, runnable, now ? 0L : time, time);
        }

        public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, final long time, @NotNull final TimeUnit unit, final boolean now, @NotNull final Runnable runnable)
        {
            return Bukkit.getScheduler().runTaskTimer(plugin, runnable, now ? 0L : Ticks.convertToTicks(time, unit), Ticks.convertToTicks(time, unit));
        }

    }

    public enum Work
    {
        ;

        public static @NotNull BukkitTask delay(@NotNull final Plugin plugin, final long time, @NotNull final Runnable runnable)
        {
            return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, time);
        }

        public static @NotNull BukkitTask delay(@NotNull final Plugin plugin, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable)
        {
            return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, Ticks.convertToTicks(time, unit));
        }


        public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, final long time, @NotNull final Runnable runnable)
        {
            return timer(plugin, time, false, runnable);
        }

        public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable)
        {
            return timer(plugin, time, unit, false, runnable);
        }


        public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, final long time, final boolean now, @NotNull final Runnable runnable)
        {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, now ? 0L : time, time);
        }

        public static @NotNull BukkitTask timer(@NotNull final Plugin plugin, final long time, @NotNull final TimeUnit unit, final boolean now, @NotNull final Runnable runnable)
        {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, now ? 0L : Ticks.convertToTicks(time, unit), Ticks.convertToTicks(time, unit));
        }

    }

}
