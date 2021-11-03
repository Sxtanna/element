package com.sxtanna.mc.element.result.test;

import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.result.Res;
import com.sxtanna.mc.element.result.handling.Failure;
import com.sxtanna.mc.element.result.handling.Result;
import com.sxtanna.mc.element.result.handling.Success;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class ResultTest
{

    @Test
    void success()
    {
        final var result = Res.success(21);
        assertInstanceOf(Success.class, result);

        assertDoesNotThrow(result::asSuccess);
        assertNotNull(result.asSuccessOrNull());

        common(result);
    }

    @Test
    void failure()
    {
        final var result = Res.failure(new NumberFormatException("failed to do number things"));
        assertInstanceOf(Failure.class, result);

        assertDoesNotThrow(result::asFailure);
        assertNotNull(result.asFailureOrNull());

        common(result);
    }


    void common(@NotNull final Result<?> result)
    {
        assertDoesNotThrow(() -> result.map(it -> it));
        assertDoesNotThrow(() -> result.map(it -> {throw new IllegalStateException("teehee");}));

        assertDoesNotThrow(() -> result.let(Res::success));
        assertDoesNotThrow(() -> result.let(it -> {throw new IllegalStateException("teehee");}));

        assertDoesNotThrow(() -> result.cast(Number.class));
        assertDoesNotThrow(() -> result.cast(String.class));
    }

}
