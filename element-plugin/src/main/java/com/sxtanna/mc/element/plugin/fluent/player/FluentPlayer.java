package com.sxtanna.mc.element.plugin.fluent.player;

import org.jetbrains.annotations.NotNull;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import com.sxtanna.mc.element.plugin.events.binding.EventBindingBuilder;
import com.sxtanna.mc.element.plugin.fluent.source.FluentSource;

public interface FluentPlayer extends FluentSource<Player>
{

    <E extends PlayerEvent> @NotNull EventBindingBuilder<E> on(@NotNull final Class<E> clazz);

}
