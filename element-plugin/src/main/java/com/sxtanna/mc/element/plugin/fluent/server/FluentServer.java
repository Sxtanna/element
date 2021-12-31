package com.sxtanna.mc.element.plugin.fluent.server;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Server;
import org.bukkit.event.server.ServerEvent;

import com.sxtanna.mc.element.plugin.events.binding.EventBindingBuilder;
import com.sxtanna.mc.element.plugin.fluent.source.FluentSource;

public interface FluentServer extends FluentSource<Server>
{

    <E extends ServerEvent> @NotNull EventBindingBuilder<E> on(@NotNull final Class<E> clazz);

}
