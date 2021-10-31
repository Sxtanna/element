package com.sxtanna.mc.element.config.resource;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import ch.jalu.configme.resource.YamlFileResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AutoYamlFileResource extends YamlFileResource
{

    public AutoYamlFileResource(@NotNull final Path path)
    {
        super(ensureExists(path));
    }


    @Contract("_ -> param1")
    private static Path ensureExists(@NotNull final Path path)
    {
        if (Files.exists(path))
        {
            return path;
        }

        try
        {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }
        catch (final IOException ignored)
        {
            /* ignored exception */
        }

        return path;
    }

}