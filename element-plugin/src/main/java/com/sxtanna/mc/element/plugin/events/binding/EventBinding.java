package com.sxtanna.mc.element.plugin.events.binding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import com.sxtanna.mc.element.plugin.ElementPlugin;
import com.sxtanna.mc.element.system.ElementCloses;
import com.sxtanna.mc.element.system.ElementModule;
import com.sxtanna.mc.element.system.ElementSystem;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public record EventBinding<E extends Event>(@NotNull Class<E> clazz,
                                            @NotNull EventPriority priority,
                                            @NotNull Plugin plugin,

                                            @NotNull @Unmodifiable List<Predicate<E>> filters,
                                            @NotNull @Unmodifiable List<Consumer<E>> handlers) implements ElementCloses, ElementModule, EventExecutor, Listener
{


    public EventBinding
    {
        if (this.plugin() instanceof ElementPlugin elementPlugin)
        {
            setup(elementPlugin.system());
        }
    }


    @Override
    public void close() throws Exception
    {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void setup(@NotNull final ElementSystem system)
    {
        system.bind(this);

        this.plugin().getServer().getPluginManager().registerEvent(this.clazz(), this, this.priority(), this, this.plugin());
    }


    @Override
    public void bind(@NotNull final ElementSystem system)
    {
        ElementModule.super.bind(system);
    }

    @Override
    public void execute(@NotNull final Listener listener, @NotNull final Event e) throws EventException
    {
        if (!clazz().isInstance(e))
        {
            return;
        }

        final var event = clazz().cast(e);

        for (final Predicate<E> filter : filters())
        {
            if (!filter.test(event))
            {
                return;
            }
        }
    }

}
