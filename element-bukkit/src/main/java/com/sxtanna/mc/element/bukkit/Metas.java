package com.sxtanna.mc.element.bukkit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

public enum Metas
{
    ;


    public static boolean has(@NotNull final Metadatable source, @NotNull final String name)
    {
        return source.hasMetadata(name);
    }


    public static void set(@NotNull final Plugin plugin, @NotNull final Metadatable source, @NotNull final String name, @Nullable final Object value)
    {
        if (value == null)
        {
            source.removeMetadata(name, plugin);
        }
        else
        {
            source.setMetadata(name, new FixedMetadataValue(plugin, value));
        }
    }


    public static @Nullable MetadataValue get(@NotNull final Plugin plugin, @NotNull final Metadatable source, @NotNull final String name)
    {
        final var stored = source.getMetadata(name);
        if (stored.isEmpty())
        {
            return null;
        }

        for (final var metadata : stored)
        {
            if (!plugin.equals(metadata.getOwningPlugin()))
            {
                continue;
            }

            return metadata;
        }

        return null;
    }


    public static <T> @Nullable T get(@NotNull final Plugin plugin, @NotNull final Metadatable source, @NotNull final String name, @NotNull final Class<T> clazz)
    {
        final var metadata = get(plugin, source, name);
        if (metadata == null)
        {
            return null;
        }

        final var value = metadata.value();

        return !clazz.isInstance(value) ? null : clazz.cast(value);
    }

}
