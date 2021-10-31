package com.sxtanna.mc.element.result.handling;

import org.jetbrains.annotations.NotNull;

public record Success<T>(@NotNull T some) implements Result<T>
{

}
