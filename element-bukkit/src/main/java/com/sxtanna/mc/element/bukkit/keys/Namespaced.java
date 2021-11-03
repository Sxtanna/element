package com.sxtanna.mc.element.bukkit.keys;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.Keyed;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public interface Namespaced extends Keyed
{

    default boolean has(@NotNull final PersistentDataHolder holder, @NotNull final PersistentDataType<?, ?> type)
    {
        return has(holder.getPersistentDataContainer(), type);
    }

    default boolean has(@NotNull final PersistentDataContainer container, @NotNull final PersistentDataType<?, ?> type)
    {
        return container.has(getKey(), type);
    }


    default <T> @Nullable T get(@NotNull final PersistentDataHolder holder, @NotNull final PersistentDataType<?, T> type)
    {
        return get(holder.getPersistentDataContainer(), type);
    }

    default <T> @Nullable T get(@NotNull final PersistentDataContainer container, @NotNull final PersistentDataType<?, T> type)
    {
        return container.get(getKey(), type);
    }


    default <T> void set(@NotNull final PersistentDataHolder holder, @NotNull final PersistentDataType<?, T> type, @Nullable final T value)
    {
        set(holder.getPersistentDataContainer(), type, value);
    }

    default <T> void set(@NotNull final PersistentDataContainer container, @NotNull final PersistentDataType<?, T> type, @Nullable final T value)
    {
        if (value == null)
        {
            container.remove(getKey());
        }
        else
        {
            container.set(getKey(), type, value);
        }
    }

}