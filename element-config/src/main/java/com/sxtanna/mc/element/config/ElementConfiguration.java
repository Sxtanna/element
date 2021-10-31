package com.sxtanna.mc.element.config;

import org.jetbrains.annotations.NotNull;

import com.sxtanna.mc.element.config.resource.AutoYamlFileResource;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.SettingsManagerImpl;
import ch.jalu.configme.configurationdata.ConfigurationDataBuilder;
import ch.jalu.configme.migration.PlainMigrationService;
import ch.jalu.configme.properties.Property;

import java.nio.file.Path;
import java.util.Optional;

public abstract class ElementConfiguration extends SettingsManagerImpl
{

    @SafeVarargs
    protected ElementConfiguration(@NotNull final Path path, @NotNull final String name, @NotNull final Class<? extends SettingsHolder>... holders)
    {
        super(new AutoYamlFileResource(path.resolve(name + ".yml")), ConfigurationDataBuilder.createConfiguration(holders), new PlainMigrationService());
    }


    public final <T> @NotNull T get(@NotNull final Property<T> property)
    {
        Optional<T> opt;

        try
        {
            opt = Optional.ofNullable(getProperty(property));
        }
        catch (final Throwable ignored)
        {
            opt = Optional.empty();
        }

        return opt.orElse(property.getDefaultValue());
    }

}
