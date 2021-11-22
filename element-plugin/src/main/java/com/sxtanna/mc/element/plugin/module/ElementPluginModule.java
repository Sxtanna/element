package com.sxtanna.mc.element.plugin.module;

import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.system.ElementCloses;
import com.sxtanna.mc.element.system.ElementModule;
import com.sxtanna.mc.element.system.ElementSystem;

public interface ElementPluginModule extends ElementCloses, ElementModule
{

    @Override
    void close() throws Exception;

    @Override
    void setup(@NotNull final ElementSystem system);


    @Override
    default void bind(@NotNull final ElementSystem system)
    {
        ElementModule.super.bind(system);
    }

}
