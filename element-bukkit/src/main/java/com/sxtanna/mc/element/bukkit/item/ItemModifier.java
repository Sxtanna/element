package com.sxtanna.mc.element.bukkit.item;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sxtanna.mc.element.bukkit.Texts;

import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.function.Consumer;

public record ItemModifier(@NotNull ItemStack item)
{

    @Contract("_ -> this")
    public @NotNull ItemModifier mod(@NotNull final Consumer<ItemMeta> consumer)
    {
        this.item().editMeta(consumer);
        return this;
    }

    @Contract("_, _ -> this")
    public <M extends ItemMeta> @NotNull ItemModifier mod(@NotNull final Class<M> clazz, @NotNull final Consumer<M> consumer)
    {
        this.item().editMeta(clazz, consumer);
        return this;
    }


    @Contract("_ -> this")
    public @NotNull ItemModifier nameText(@NotNull final String name)
    {
        return nameComp(Texts.component(name));
    }

    @Contract("_ -> this")
    public @NotNull ItemModifier nameComp(@NotNull final Component name)
    {
        return mod(meta -> meta.displayName(name));
    }


    @Contract("_ -> this")
    public @NotNull ItemModifier loreText(@NotNull final String... lore)
    {
        return loreText(List.of(lore));
    }

    @Contract("_ -> this")
    public @NotNull ItemModifier loreComp(@NotNull final Component... lore)
    {
        return loreComp(List.of(lore));
    }

    @Contract("_ -> this")
    public @NotNull ItemModifier loreText(@NotNull final List<String> lore)
    {
        return loreComp(lore.stream().map(Texts::component).toList());
    }

    @Contract("_ -> this")
    public @NotNull ItemModifier loreComp(@NotNull final List<Component> lore)
    {
        return mod(meta -> meta.lore(lore));
    }


    @Contract("_ -> this")
    public @NotNull ItemModifier flag(@NotNull final ItemFlag... flags)
    {
        return mod(meta -> meta.addItemFlags(flags));
    }


    @Contract("_ -> this")
    public @NotNull ItemModifier enchant(@NotNull final Enchantment enchantment)
    {
        return enchant(enchantment, enchantment.getStartLevel());
    }

    @Contract("_, _ -> this")
    public @NotNull ItemModifier enchant(@NotNull final Enchantment enchantment, final int level)
    {
        return mod(meta -> meta.addEnchant(enchantment, level, true));
    }

}
