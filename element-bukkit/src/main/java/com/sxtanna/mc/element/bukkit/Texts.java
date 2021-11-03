package com.sxtanna.mc.element.bukkit;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.ChatColor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.function.Consumer;

public enum Texts
{
    ;

    public static final char CHAT_CHAR = '&';
    public static final char GAME_CHAR = '§';


    @Contract("_ -> new")
    public static @NotNull String coloured(@NotNull final String text)
    {
        return coloured(text, CHAT_CHAR);
    }

    @Contract("_, _ -> new")
    public static @NotNull String coloured(@NotNull final String text, final char colorChar)
    {
        return ChatColor.translateAlternateColorCodes(colorChar, text);
    }


    @Contract("_ -> new")
    public static @NotNull String stripped(@NotNull final String text)
    {
        return stripped(text, CHAT_CHAR);
    }

    @Contract("_, _ -> new")
    public static @NotNull String stripped(@NotNull final String text, final char colorChar)
    {
        return ChatColor.stripColor(coloured(text, colorChar));
    }


    public static @NotNull Component component(@NotNull final String text)
    {
        return component(text, false);
    }

    public static @NotNull Component component(@NotNull final String text, final boolean section)
    {
        return component(text, section ?
                               LegacyComponentSerializer.legacySection() :
                               LegacyComponentSerializer.legacyAmpersand());
    }

    public static @NotNull Component component(@NotNull final String text, @NotNull final LegacyComponentSerializer serializer)
    {
        return serializer.deserialize(text);
    }


    @Contract("_ -> new")
    public static @NotNull Component build(@NotNull final Consumer<TextComponent.Builder> consumer)
    {
        return Component.text(consumer);
    }

    @Contract("_, _ -> new")
    public static @NotNull Component build(@NotNull final String text, @NotNull final Consumer<TextComponent.Builder> consumer)
    {
        final var builder = Component.text(text).toBuilder();

        consumer.accept(builder);

        return builder.build();
    }

}
