package com.sxtanna.mc.element.common.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public enum Times
{
    ;


    public @Nullable TimeUnit asTimeUnit(@NotNull final ChronoUnit unit)
    {
        return GenericUnits.of(unit).map(GenericUnits::getTimeUnit).orElse(null);
    }

    public @Nullable ChronoUnit asChronoUnit(@NotNull final TimeUnit unit)
    {
        return GenericUnits.of(unit).map(GenericUnits::getChronoUnit).orElse(null);
    }


    public interface GenericUnit
    {

        @NotNull Duration toDuration();

    }

    public enum GenericUnits implements GenericUnit
    {

        NANOSECOND(TimeUnit.NANOSECONDS, ChronoUnit.NANOS),
        MICROSECOND(TimeUnit.MICROSECONDS, ChronoUnit.MICROS),
        MILLISECOND(TimeUnit.MILLISECONDS, ChronoUnit.MILLIS),

        TICKS()
                {
                    @NotNull
                    private static final Duration MINECRAFT_TICK_DURATION = Duration.of(1000L / 20L, ChronoUnit.MILLIS);

                    @Override
                    public @NotNull Duration toDuration()
                    {
                        return MINECRAFT_TICK_DURATION;
                    }
                },

        SECOND(TimeUnit.SECONDS, ChronoUnit.SECONDS),
        MINUTE(TimeUnit.MINUTES, ChronoUnit.MINUTES),
        HOUR(TimeUnit.HOURS, ChronoUnit.HOURS),
        DAY(TimeUnit.DAYS, ChronoUnit.DAYS),
        WEEK(ChronoUnit.WEEKS),
        MONTH(ChronoUnit.MONTHS),
        YEAR(ChronoUnit.YEARS),
        DECADE(ChronoUnit.DECADES),
        CENTURY(ChronoUnit.CENTURIES),
        MILLENNIA(ChronoUnit.MILLENNIA),
        ERA(ChronoUnit.ERAS),
        FOREVER(ChronoUnit.FOREVER);


        @NotNull
        private static final GenericUnits[] values = values();


        public static @NotNull Optional<GenericUnits> of(@NotNull final TimeUnit unit)
        {
            return Arrays.stream(values).filter(it -> it.getTimeUnit() == unit).findFirst();
        }

        public static @NotNull Optional<GenericUnits> of(@NotNull final ChronoUnit unit)
        {
            return Arrays.stream(values).filter(it -> it.getChronoUnit() == unit).findFirst();
        }


        @Nullable
        private final TimeUnit   timeUnit;
        @Nullable
        private final ChronoUnit chronoUnit;


        @Contract(pure = true)
        GenericUnits()
        {
            this(null, null);
        }

        @Contract(pure = true)
        GenericUnits(@Nullable final TimeUnit timeUnit)
        {
            this(timeUnit, null);
        }

        @Contract(pure = true)
        GenericUnits(@Nullable final ChronoUnit chronoUnit)
        {
            this(null, chronoUnit);
        }

        @Contract(pure = true)
        GenericUnits(@Nullable final TimeUnit timeUnit, @Nullable final ChronoUnit chronoUnit)
        {
            this.timeUnit   = timeUnit;
            this.chronoUnit = chronoUnit;
        }


        @Contract(pure = true)
        public final @Nullable TimeUnit getTimeUnit()
        {
            return this.timeUnit;
        }

        @Contract(pure = true)
        public final @Nullable ChronoUnit getChronoUnit()
        {
            return this.chronoUnit;
        }


        @Override
        public @NotNull Duration toDuration()
        {
            final var timeUnit = getTimeUnit();
            if (timeUnit != null)
            {
                return Duration.ofNanos(TimeUnit.NANOSECONDS.convert(1, timeUnit));
            }

            final var chronoUnit = getChronoUnit();
            if (chronoUnit != null)
            {
                return chronoUnit.getDuration();
            }

            return Duration.ZERO;
        }

    }

}
