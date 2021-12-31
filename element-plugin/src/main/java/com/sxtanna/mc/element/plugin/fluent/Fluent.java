package com.sxtanna.mc.element.plugin.fluent;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sxtanna.mc.element.caches.Caches;
import com.sxtanna.mc.element.plugin.fluent.entity.FluentEntity;
import com.sxtanna.mc.element.plugin.fluent.entity.FluentEntityImpl;
import com.sxtanna.mc.element.plugin.fluent.player.FluentPlayer;
import com.sxtanna.mc.element.plugin.fluent.player.FluentPlayerImpl;
import com.sxtanna.mc.element.plugin.fluent.server.FluentServer;
import com.sxtanna.mc.element.plugin.fluent.server.FluentServerImpl;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.UUID;

public enum Fluent
{
    ;

    @NotNull
    private static final FluentServer SERVER = new FluentServerImpl(Bukkit.getServer());

    @NotNull
    private static final Cache<UUID, FluentEntity> ENTITY_CACHE = Caches.caffeine().build();
    @NotNull
    private static final Cache<UUID, FluentPlayer> PLAYER_CACHE = Caches.caffeine().build();


    static
    {
        SERVER.bind(() -> {
            ENTITY_CACHE.invalidateAll();
            PLAYER_CACHE.invalidateAll();
        });
    }


    @Contract(pure = true)
    public static @NotNull FluentServer server()
    {
        return SERVER;
    }


    public static @NotNull FluentEntity of(@NotNull final Entity entity)
    {
        return ENTITY_CACHE.get(entity.getUniqueId(), $ -> new FluentEntityImpl(entity));
    }

    public static @NotNull FluentPlayer of(@NotNull final Player player)
    {
        return PLAYER_CACHE.get(player.getUniqueId(), $ -> new FluentPlayerImpl(player));
    }

}
