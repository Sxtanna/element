package com.sxtanna.mc.element.finder;

import org.jetbrains.annotations.NotNull;

import io.github.classgraph.ClassGraph;

import java.util.Collection;

public final class ElementScanner
{

    @NotNull
    private final ClassGraph scanner;


    public ElementScanner(@NotNull final ClassGraph scanner)
    {
        this.scanner = scanner;
    }


    public <T> Collection<Class<T>> find(@NotNull final Class<T> clazz)
    {
        return scanner.enableClassInfo().ignoreClassVisibility().scan().getClassesImplementing(clazz).loadClasses(clazz, true);
    }

}
