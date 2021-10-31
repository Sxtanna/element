package com.sxtanna.mc.element.inject;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import java.util.concurrent.atomic.AtomicReference;

public final class ElementInjector
{

    @NotNull
    private final AtomicReference<Injector> injector = new AtomicReference<>();


    public ElementInjector(@NotNull final ElementInjectorModule root)
    {
        add(root);
    }


    public void add(@NotNull final ElementInjectorModule module)
    {
        this.injector.set(create(module));
    }


    public <T> @NotNull T get(@NotNull final Class<T> clazz)
    {
        return this.injector.get().getInstance(clazz);
    }

    public <T> @NotNull T get(@NotNull final Class<T> clazz, @NotNull final String name)
    {
        return this.injector.get().getInstance(Key.get(clazz, Names.named(name)));
    }


    private @NotNull Injector create(final @NotNull ElementInjectorModule module)
    {
        final var prev = this.injector.get();

        if (prev == null)
        {
            return Guice.createInjector(module);
        }
        else
        {
            return prev.createChildInjector(module);
        }
    }

}
