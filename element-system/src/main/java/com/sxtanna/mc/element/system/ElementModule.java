package com.sxtanna.mc.element.system;

import org.jetbrains.annotations.NotNull;

public interface ElementModule extends ElementBinder
{

    void setup(@NotNull final ElementSystem system);


    @Override
    default void bind(@NotNull final ElementSystem system)
    {
        system.with(this);
    }

}
