package com.sxtanna.mc.element.plugin;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.java.JavaPlugin;

import com.sxtanna.mc.element.common.state.Inits;
import com.sxtanna.mc.element.common.state.State;
import com.sxtanna.mc.element.inject.ElementInjector;
import com.sxtanna.mc.element.plugin.timing.ElementTimings;
import com.sxtanna.mc.element.result.Try;
import com.sxtanna.mc.element.system.ElementSystem;
import com.sxtanna.mc.element.system.Sys;

public abstract class ElementPlugin extends JavaPlugin implements Inits, State
{

    @NotNull
    private final ElementSystem   system = Sys.create();
    @NotNull
    private final ElementTimings  timing = new ElementTimings(this);
    @NotNull
    private final ElementInjector inject = new ElementInjector(new ElementPluginModule(this));


    @Internal
    @Override
    public final void onLoad()
    {
        Try.twr(timing.time("init"), $ -> {this.init();});
    }

    @Internal
    @Override
    public final void onEnable()
    {
        Try.twr(timing.time("load"), $ -> {this.load();});
    }

    @Internal
    @Override
    public final void onDisable()
    {
        Try.twr(timing.time("kill"), $ -> {this.kill();});
    }


    @OverrideOnly
    @Override
    public void init()
    {

    }

    @OverrideOnly
    @Override
    public void load()
    {

    }

    @OverrideOnly
    @Override
    public void kill()
    {

    }


    @Contract(pure = true)
    public final @NotNull ElementInjector inject()
    {
        return this.inject;
    }

    @Contract(pure = true)
    public final @NotNull ElementTimings timing()
    {
        return this.timing;
    }

    @Contract(pure = true)
    public final @NotNull ElementSystem system()
    {
        return this.system;
    }

}
