package com.sxtanna.mc.element.result;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.sxtanna.mc.element.result.handling.Failure;
import com.sxtanna.mc.element.result.handling.Result;
import com.sxtanna.mc.element.result.handling.Success;
import com.sxtanna.mc.element.result.throwing.ExceptionalRunnable;
import com.sxtanna.mc.element.result.throwing.ExceptionalSupplier;

import java.util.Objects;

public enum Res
{
    ;

    public enum Unit
    {
        INSTANCE
    }


    @NotNull
    private static final Result<Unit> UNIT_RESULT = new Success<>(Unit.INSTANCE);


    @Contract("_ -> new")
    public static <T> @NotNull Success<T> success(@NotNull final T some)
    {
        return new Success<>(some);
    }

    @Contract("_ -> new")
    public static <T> @NotNull Failure<T> failure(@NotNull final Throwable none)
    {
        return new Failure<>(none);
    }


    public static @NotNull Result<Unit> of(@NotNull final ExceptionalRunnable runnable)
    {
        try
        {
            runnable.run();
            return UNIT_RESULT;
        }
        catch (final Throwable ex)
        {
            return failure(ex);
        }
    }

    public static <T> @NotNull Result<T> of(@NotNull final ExceptionalSupplier<@Nullable T> supplier)
    {
        try
        {
            return success(Objects.requireNonNull(supplier.get()));
        }
        catch (final Throwable ex)
        {
            return failure(ex);
        }
    }


    public static <T> @NotNull Result<T> opt(@Nullable final T value)
    {
        if (value != null)
        {
            //noinspection unchecked
            return value == Unit.INSTANCE ? (Result<T>) UNIT_RESULT : Res.success(value);
        }
        else
        {
            return Res.failure(new NullPointerException("missing value"));
        }
    }

}
