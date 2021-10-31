package com.sxtanna.mc.element.result.handling;

import org.jetbrains.annotations.NotNull;

public record Failure<T>(@NotNull Throwable none) implements Result<T>
{

}
