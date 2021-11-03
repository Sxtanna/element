package com.sxtanna.mc.element.plugin.timing;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;

import com.sxtanna.mc.element.plugin.ElementPlugin;
import com.sxtanna.mc.element.system.ElementCloses;

import co.aikar.timings.Timing;
import co.aikar.timings.Timings;
import co.aikar.timings.TimingsManager;

public record ElementTimings(@NotNull Plugin plugin, @NotNull Timing timing) implements ElementCloses
{

    public ElementTimings(@NotNull final ElementPlugin plugin)
    {
        this(plugin, Timings.of(plugin, "Combined Total", TimingsManager.PLUGIN_GROUP_HANDLER));
    }


    @Override
    public void close()
    {
        this.timing().close();
    }


    public @NotNull Timing time(@NotNull final String name)
    {
        return Timings.ofStart(plugin(), name, timing());
    }

    public @NotNull Timing time(@NotNull final String name, @NotNull final Runnable timed)
    {
        final var timing = time(name);

        try (timing)
        {
            timed.run();
        }

        return timing;
    }

}
