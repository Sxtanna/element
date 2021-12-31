package com.sxtanna.mc.element.plugin.fluent.source;

import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.system.ElementSystem;

public interface FluentSource<T> extends ElementSystem
{

    @NotNull T source();

}
