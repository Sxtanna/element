package com.sxtanna.mc.element.bukkit;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public enum Ticks
{
    ;

    public static final long TPS = 20L;
    public static final long MPT = 1000L / TPS;


    @Contract(pure = true)
    public static long convertToTicks(final long mill) {
        return mill / MPT;
    }

    @Contract(pure = true)
    public static long convertToTicks(final long time, @NotNull final TimeUnit unit) {
        return convertToTicks(TimeUnit.MILLISECONDS.convert(time, unit));
    }

    @Contract(pure = true)
    public static long convertToTicks(final long time, @NotNull final ChronoUnit unit) {
        return convertToTicks(time, TimeUnit.of(unit));
    }

}
