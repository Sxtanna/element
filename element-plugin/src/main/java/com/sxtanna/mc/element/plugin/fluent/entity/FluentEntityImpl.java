package com.sxtanna.mc.element.plugin.fluent.entity;

import org.jetbrains.annotations.NotNull;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityEvent;

import com.sxtanna.mc.element.plugin.events.Events;
import com.sxtanna.mc.element.plugin.events.binding.EventBindingBuilder;
import com.sxtanna.mc.element.system.ElementModule;
import com.sxtanna.mc.element.system.ElementSystem;
import com.sxtanna.mc.element.system.Sys;

public record FluentEntityImpl(@NotNull Entity source, @NotNull ElementSystem system) implements FluentEntity
{

    public FluentEntityImpl(@NotNull final Entity source)
    {
        this(source, Sys.create());
    }


    @Override
    public void close() throws Exception
    {
        this.system().close();
    }


    @Override
    public <T extends AutoCloseable> @NotNull T bind(@NotNull final T closes)
    {
        return this.system().bind(closes);
    }

    @Override
    public <T extends ElementModule> @NotNull T with(@NotNull final T module)
    {
        return this.system().with(module);
    }


    @Override
    public @NotNull <E extends EntityEvent> EventBindingBuilder<E> on(@NotNull final Class<E> clazz)
    {
        return Events.on(clazz).filter(it -> it.getEntity().equals(this.source()));
    }

}
