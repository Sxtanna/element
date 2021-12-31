package com.sxtanna.mc.element.plugin.fluent.player;

import org.jetbrains.annotations.NotNull;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import com.sxtanna.mc.element.plugin.events.Events;
import com.sxtanna.mc.element.plugin.events.binding.EventBindingBuilder;
import com.sxtanna.mc.element.system.ElementModule;
import com.sxtanna.mc.element.system.ElementSystem;
import com.sxtanna.mc.element.system.Sys;

public record FluentPlayerImpl(@NotNull Player source, @NotNull ElementSystem system) implements FluentPlayer
{

    public FluentPlayerImpl(@NotNull final Player source)
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
    public @NotNull <E extends PlayerEvent> EventBindingBuilder<E> on(@NotNull final Class<E> clazz)
    {
        return Events.on(clazz).filter(it -> it.getPlayer().equals(this.source()));
    }

}