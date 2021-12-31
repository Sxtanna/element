package com.sxtanna.mc.element.plugin.fluent.entity;

import org.jetbrains.annotations.NotNull;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityEvent;

import com.sxtanna.mc.element.plugin.events.binding.EventBindingBuilder;
import com.sxtanna.mc.element.plugin.fluent.source.FluentSource;

public interface FluentEntity extends FluentSource<Entity>
{

    <E extends EntityEvent> @NotNull EventBindingBuilder<E> on(@NotNull final Class<E> clazz);

}
