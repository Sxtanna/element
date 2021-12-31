package com.sxtanna.mc.element.plugin;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.sxtanna.mc.element.bukkit.Tasks;
import com.sxtanna.mc.element.bukkit.task.TaskScheduler;
import com.sxtanna.mc.element.common.state.Inits;
import com.sxtanna.mc.element.common.state.State;
import com.sxtanna.mc.element.finder.ElementScanner;
import com.sxtanna.mc.element.inject.ElementInjector;
import com.sxtanna.mc.element.inject.ElementInjectorModule;
import com.sxtanna.mc.element.plugin.finder.Requested;
import com.sxtanna.mc.element.plugin.module.ElementPluginModule;
import com.sxtanna.mc.element.plugin.timing.ElementTimings;
import com.sxtanna.mc.element.result.Try;
import com.sxtanna.mc.element.result.throwing.ExceptionalSupplier;
import com.sxtanna.mc.element.system.ElementModule;
import com.sxtanna.mc.element.system.ElementSystem;
import com.sxtanna.mc.element.system.Sys;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class ElementPlugin extends JavaPlugin implements Inits, State, ElementSystem, TaskScheduler
{

    @NotNull
    private final ElementSystem   system = Sys.create();
    @NotNull
    private final ElementTimings  timing = new ElementTimings(this);
    @NotNull
    private final ElementInjector inject = new ElementInjector(createInjectorModule());


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
        Try.twr(timing.time("load"), $ ->
        {
            Try.twr(timing().time("load_plugin"), $0 ->
            {
                this.load();
            });

            final var modules = new ArrayList<ElementPluginModule>();

            Try.twr(timing().time("scan_module"), $0 ->
            {
                final var scanner = new ElementScanner(ElementPlugin.this.getClassLoader());

                scanner.find(ElementPluginModule.class)
                       .stream()
                       .filter(it -> Modifier.isFinal(it.getModifiers()))
                       .filter(it -> !it.isAnnotationPresent(Requested.class))
                       .map(clazz -> Try.opt(() -> inject().get(clazz)))
                       .flatMap(Optional::stream)
                       .forEach(modules::add);
            });


            this.system().bind(modules);
            this.system().bind(this.timing());


            Try.twr(timing().time("load_module"), $0 ->
            {
                modules.forEach(module -> Try.run(() -> this.system().with(module)));
            });
        });
    }

    @Internal
    @Override
    public final void onDisable()
    {
        Try.twr(timing.time("kill"), $ ->
        {
            this.kill();

            this.system().closeAndReport();
        });
    }


    @Override
    public final void close() throws Exception
    {
        this.system().close();
    }

    @Override
    public final <T extends AutoCloseable> @NotNull T bind(@NotNull final T closes)
    {
        return this.system().bind(closes);
    }

    @Override
    public final <T extends ElementModule> @NotNull T with(@NotNull final T module)
    {
        return this.system().with(module);
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


    protected @NotNull ElementInjectorModule createInjectorModule()
    {
        return new ElementPluginInjectorModule(this);
    }


    @Override
    public final @NotNull BukkitTask main(@NotNull final Runnable runnable)
    {
        return Tasks.main(this, runnable);
    }

    @Override
    public final @NotNull BukkitTask work(@NotNull final Runnable runnable)
    {
        return Tasks.work(this, runnable);
    }


    @Override
    public final @NotNull BukkitTask delay(@NotNull final Tasks.Context context, final long time, @NotNull final Runnable runnable)
    {
        return Tasks.delay(this, context, time, runnable);
    }

    @Override
    public final @NotNull BukkitTask delay(@NotNull final Tasks.Context context, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable)
    {
        return Tasks.delay(this, context, time, unit, runnable);
    }


    @Override
    public final @NotNull BukkitTask timer(@NotNull final Tasks.Context context, final long time, @NotNull final Runnable runnable)
    {
        return timer(context, time, false, runnable);
    }

    @Override
    public final @NotNull BukkitTask timer(@NotNull final Tasks.Context context, final long time, @NotNull final TimeUnit unit, @NotNull final Runnable runnable)
    {
        return timer(context, time, unit, false, runnable);
    }


    @Override
    public final @NotNull BukkitTask timer(@NotNull final Tasks.Context context, final long time, final boolean now, @NotNull final Runnable runnable)
    {
        return Tasks.timer(this, context, time, now, runnable);
    }

    @Override
    public final @NotNull BukkitTask timer(@NotNull final Tasks.Context context, final long time, @NotNull final TimeUnit unit, final boolean now, @NotNull final Runnable runnable)
    {
        return Tasks.timer(this, context, time, unit, now, runnable);
    }


    @Override
    public final @NotNull CompletableFuture<Void> mainAwait(@NotNull final Runnable runnable)
    {
        return Tasks.mainAwait(this, runnable);
    }

    @Override
    public final @NotNull CompletableFuture<Void> workAwait(@NotNull final Runnable runnable)
    {
        return Tasks.workAwait(this, runnable);
    }

    @Override
    public final @NotNull <T> CompletableFuture<T> mainAwait(@NotNull final ExceptionalSupplier<T> supplier)
    {
        return Tasks.mainAwait(this, supplier);
    }

    @Override
    public final @NotNull <T> CompletableFuture<T> workAwait(@NotNull final ExceptionalSupplier<T> supplier)
    {
        return Tasks.workAwait(this, supplier);
    }

}
