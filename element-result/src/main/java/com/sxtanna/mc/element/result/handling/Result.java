package com.sxtanna.mc.element.result.handling;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.sxtanna.mc.element.result.Opt;

import java.util.Optional;

public interface Result<T>
{

    default @NotNull Optional<T> asOptional()
    {
        return this instanceof Success<T> success ? Opt.of(success.some()) : Optional.empty();
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

}
