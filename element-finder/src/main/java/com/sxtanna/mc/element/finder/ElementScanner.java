package com.sxtanna.mc.element.finder;

import org.jetbrains.annotations.NotNull;

import io.github.classgraph.ClassGraph;

import java.util.Collection;

public record ElementScanner(@NotNull ClassGraph scanner)
{

    public ElementScanner()
    {
        this(new ClassGraph());
    }

    public ElementScanner(@NotNull final ClassLoader loader)
    {
        this(new ClassGraph().addClassLoader(loader));
    }


    public <T> Collection<Class<T>> find(@NotNull final Class<T> clazz)
    {
        return this.scanner().enableClassInfo().ignoreClassVisibility().scan().getClassesImplementing(clazz).loadClasses(clazz, true);
    }

}
