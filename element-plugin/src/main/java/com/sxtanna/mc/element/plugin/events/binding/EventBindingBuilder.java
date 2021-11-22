package com.sxtanna.mc.element.plugin.events.binding;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import com.sxtanna.mc.element.common.model.Builder1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class EventBindingBuilder<E extends Event> implements Builder1<@NotNull Plugin, @NotNull EventBinding<E>>
{

    @NotNull
    private final Class<E> clazz;


    private EventPriority priority = EventPriority.NORMAL;

    private final List<Predicate<E>> filters  = new ArrayList<>();
    private final List<Consumer<E>>  handlers = new ArrayList<>();


    @Contract(pure = true)
    public EventBindingBuilder(final @NotNull Class<E> clazz)
    {
        this.clazz = clazz;
    }


    @Contract(value = "_ -> this", mutates = "this")
    public @NotNull EventBindingBuilder<E> at(@NotNull final EventPriority priority)
    {
        this.priority = priority;
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public @NotNull EventBindingBuilder<E> filter(@NotNull final Predicate<E> predicate)
    {
        this.filters.add(predicate);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public @NotNull EventBindingBuilder<E> handle(@NotNull final Consumer<E> consumer)
    {
        this.handlers.add(consumer);
        return this;
    }


    @Contract("_ -> new")
    @Override
    public @NotNull EventBinding<E> build(@NotNull final Plugin value)
    {
        return new EventBinding<>(this.clazz, this.priority, value, List.copyOf(this.filters), List.copyOf(this.handlers));
    }

}
