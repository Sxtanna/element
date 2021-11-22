package com.sxtanna.mc.element.plugin.events;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import com.sxtanna.mc.element.plugin.events.binding.EventBindingBuilder;

import java.util.function.Consumer;

public enum Events
{
    ;


    @Contract("_ -> new")
    public static <E extends Event> @NotNull EventBindingBuilder<E> on(@NotNull final Class<E> clazz)
    {
        return new EventBindingBuilder<>(clazz);
    }

    @Contract("_, _ -> new")
    public static <E extends Event> @NotNull EventBindingBuilder<E> on(@NotNull final Class<E> clazz, @NotNull final EventPriority priority)
    {
        return on(clazz).at(priority);
    }


    @Contract("_, _ -> new")
    public static <E extends Event> @NotNull EventBindingBuilder<E> on(@NotNull final Class<E> clazz, @NotNull final Consumer<E> consumer)
    {
        return on(clazz).handle(consumer);
    }

    @Contract("_, _, _ -> new")
    public static <E extends Event> @NotNull EventBindingBuilder<E> on(@NotNull final Class<E> clazz, @NotNull final EventPriority priority, @NotNull final Consumer<E> consumer)
    {
        return on(clazz, priority).handle(consumer);
    }

}
