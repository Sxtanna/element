package com.sxtanna.mc.element.result.handling;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.sxtanna.mc.element.result.Opt;
import com.sxtanna.mc.element.result.Res;
import com.sxtanna.mc.element.result.throwing.ExceptionalConsumer;
import com.sxtanna.mc.element.result.throwing.ExceptionalFunction;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public interface Result<T>
{

    default @NotNull Optional<T> asOptional()
    {
        return this instanceof Success<T> success ? Opt.of(success.some()) : Opt.none();
    }


    default @NotNull Success<T> asSuccess()
    {
        final var success = asSuccessOrNull();
        if (success != null)
        {
            return success;
        }


        final var failure = asFailureOrNull();
        if (failure != null)
        {
            throw new IllegalStateException(failure.none());
        }


        throw new IllegalStateException("invalid result instance");
    }

    default @NotNull Failure<T> asFailure()
    {
        final var failure = asFailureOrNull();
        if (failure != null)
        {
            return failure;
        }


        throw new IllegalStateException("result is success");
    }


    default @Nullable Success<T> asSuccessOrNull()
    {
        return this instanceof Success<T> success ? success : null;
    }

    default @Nullable Failure<T> asFailureOrNull()
    {
        return this instanceof Failure<T> failure ? failure : null;
    }


    default @NotNull Optional<Success<T>> asSuccessOptional()
    {
        return Opt.of(asSuccessOrNull());
    }

    default @NotNull Optional<Failure<T>> asFailureOptional()
    {
        return Opt.of(asFailureOrNull());
    }


    default <O> @NotNull Result<O> map(@NotNull final ExceptionalFunction<@NotNull T, @Nullable O> function)
    {
        final var success = asSuccessOrNull();
        if (success == null)
        {
            return ((Failure<O>) this);
        }

        try
        {
            return Res.success(Objects.requireNonNull(function.apply(success.some())));
        }
        catch (final Throwable ex)
        {
            return Res.failure(ex);
        }
    }

    default <O> @NotNull Result<O> let(@NotNull final ExceptionalFunction<@NotNull T, @Nullable Result<O>> function)
    {
        final var success = asSuccessOrNull();
        if (success == null)
        {
            return ((Failure<O>) this);
        }

        try
        {
            return Objects.requireNonNull(function.apply(success.some()));
        }
        catch (final Throwable ex)
        {
            return Res.failure(ex);
        }
    }


    default <O> @NotNull Result<O> cast(@NotNull final Class<O> clazz)
    {
        return map(clazz::cast);
    }


    default <O> @NotNull Result<O> fold(@NotNull final ExceptionalFunction<T, O> whenSome, @NotNull final ExceptionalFunction<Throwable, O> whenNone)
    {
        if (this instanceof Success<T>)
        {
            return map(whenSome);
        }

        try
        {
            return Res.of(() -> whenNone.apply(asFailure().none()));
        }
        catch (final Throwable ex)
        {
            return Res.failure(ex);
        }
    }

    default @NotNull Result<T> fold(@NotNull final ExceptionalConsumer<T> whenSome, @NotNull final ExceptionalConsumer<Throwable> whenNone)
    {
        try
        {
            if (this instanceof Success<T> success)
            {
                whenSome.accept(success.some());
            }

            if (this instanceof Failure<T> failure)
            {
                whenNone.accept(failure.none());
            }

            return this;
        }
        catch (final Throwable ex)
        {
            return Res.failure(ex);
        }
    }


    default void done(@NotNull final Consumer<T> successConsumer, @NotNull final Consumer<Throwable> failureConsumer)
    {
        fold(successConsumer::accept, failureConsumer::accept);
    }

}
