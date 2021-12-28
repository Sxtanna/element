package com.sxtanna.mc.element.result;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.DoubleFunction;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public enum Opt
{
    ;


    @Contract(pure = true)
    public static <T> @NotNull Optional<T> of(@Nullable final T value)
    {
        return Optional.ofNullable(value);
    }


    @Contract(pure = true)
    public static <T> @NotNull Optional<T> some(@NotNull final T value)
    {
        return Optional.of(value);
    }

    @Contract(pure = true)
    public static <T> @NotNull Optional<T> none()
    {
        return Optional.empty();
    }


    @Contract(pure = true)
    public static @NotNull OptionalInt of(@Nullable final Integer value)
    {
        return value == null ? OptionalInt.empty() : OptionalInt.of(value);
    }


    @Contract(pure = true)
    public static @NotNull OptionalInt ofInt(final int value)
    {
        return OptionalInt.of(value);
    }

    @Contract(pure = true)
    public static @NotNull OptionalInt noInt()
    {
        return OptionalInt.empty();
    }


    @Contract(pure = true)
    public static @NotNull OptionalLong of(@Nullable final Long value)
    {
        return value == null ? OptionalLong.empty() : OptionalLong.of(value);
    }


    @Contract(pure = true)
    public static @NotNull OptionalLong ofLong(final long value)
    {
        return OptionalLong.of(value);
    }

    @Contract(pure = true)
    public static @NotNull OptionalLong noLong()
    {
        return OptionalLong.empty();
    }


    @Contract(pure = true)
    public static @NotNull OptionalDouble of(@Nullable final Double value)
    {
        return value == null ? OptionalDouble.empty() : OptionalDouble.of(value);
    }


    @Contract(pure = true)
    public static @NotNull OptionalDouble noDouble(final double value)
    {
        return OptionalDouble.of(value);
    }

    @Contract(pure = true)
    public static @NotNull OptionalDouble noDouble()
    {
        return OptionalDouble.empty();
    }


    public static <T> @NotNull Optional<T> map(@NotNull final OptionalInt opt, @NotNull IntFunction<T> function)
    {
        return opt.isEmpty() ? Optional.empty() : of(function.apply(opt.getAsInt()));
    }

    public static <T> @NotNull Optional<T> map(@NotNull final OptionalLong opt, @NotNull LongFunction<T> function)
    {
        return opt.isEmpty() ? Optional.empty() : of(function.apply(opt.getAsLong()));
    }

    public static <T> @NotNull Optional<T> map(@NotNull final OptionalDouble opt, @NotNull DoubleFunction<T> function)
    {
        return opt.isEmpty() ? Optional.empty() : of(function.apply(opt.getAsDouble()));
    }


    public static <T> @NotNull Optional<T> flatMap(@NotNull final OptionalInt opt, @NotNull IntFunction<Optional<T>> function)
    {
        return opt.isEmpty() ? Optional.empty() : function.apply(opt.getAsInt());
    }

    public static <T> @NotNull Optional<T> flatMap(@NotNull final OptionalLong opt, @NotNull LongFunction<Optional<T>> function)
    {
        return opt.isEmpty() ? Optional.empty() : function.apply(opt.getAsLong());
    }

    public static <T> @NotNull Optional<T> flatMap(@NotNull final OptionalDouble opt, @NotNull DoubleFunction<Optional<T>> function)
    {
        return opt.isEmpty() ? Optional.empty() : function.apply(opt.getAsDouble());
    }

}
