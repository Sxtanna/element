package com.sxtanna.mc.element.bukkit;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sxtanna.mc.element.bukkit.item.ItemModifier;

import java.util.function.Consumer;

public enum Items
{
    ;


    @Contract("_ -> new")
    public static @NotNull ItemModifier edit(@NotNull final ItemStack item)
    {
        return new ItemModifier(item);
    }


    @Contract("_ -> new")
    public static @NotNull ItemStack item(@NotNull final Material type)
    {
        return item(type, 1);
    }

    @Contract("_, _ -> new")
    public static @NotNull ItemStack item(@NotNull final Material type, final int amount)
    {
        return item(type, amount, ($) -> {});
    }

    @Contract("_, _ -> new")
    public static @NotNull ItemStack item(@NotNull final Material type, @NotNull final Consumer<ItemMeta> consumer)
    {
        return item(type, 1, consumer);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull ItemStack item(@NotNull final Material type, final int amount, @NotNull final Consumer<ItemMeta> consumer)
    {
        return meta(new ItemStack(type, amount), consumer);
    }


    @Contract("_, _ -> param1")
    public static @NotNull ItemStack meta(@NotNull final ItemStack item, @NotNull final Consumer<ItemMeta> consumer)
    {
        item.editMeta(consumer);
        return item;
    }

    @Contract("_, _, _ -> param1")
    public static <M extends ItemMeta> @NotNull ItemStack meta(@NotNull final ItemStack item, @NotNull final Class<M> clazz, @NotNull final Consumer<M> consumer)
    {
        item.editMeta(clazz, consumer);
        return item;
    }

}
