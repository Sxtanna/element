package com.sxtanna.mc.element.plugin;

import org.jetbrains.annotations.NotNull;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

import com.sxtanna.mc.element.inject.ElementInjectorModule;
import com.sxtanna.mc.element.serial.json.Json;

import com.google.gson.Gson;
import com.google.inject.name.Names;

import java.nio.file.Path;

final class ElementPluginInjectorModule extends ElementInjectorModule
{

    @NotNull
    private final ElementPlugin plugin;


    ElementPluginInjectorModule(@NotNull final ElementPlugin plugin)
    {
        this.plugin = plugin;
    }


    @Override
    protected void configure()
    {
        bind(Plugin.class)
                .toInstance(this.plugin);
        bind(Server.class)
                .toProvider(Bukkit::getServer);


        bind(Path.class)
                .annotatedWith(Names.named("config"))
                .toProvider(() -> this.plugin.getDataFolder().toPath().resolve("conf"));


        bind(Gson.class)
                .toProvider(Json::normal);

        bind(Gson.class)
                .annotatedWith(Names.named("pretty"))
                .toProvider(Json::pretty);


        bind(PluginManager.class)
                .toProvider(Bukkit::getPluginManager);

        bind(ServicesManager.class)
                .toProvider(Bukkit::getServicesManager);

        bind(BukkitScheduler.class)
                .toProvider(Bukkit::getScheduler);

        bind(ScoreboardManager.class)
                .toProvider(Bukkit::getScoreboardManager);
    }

}
