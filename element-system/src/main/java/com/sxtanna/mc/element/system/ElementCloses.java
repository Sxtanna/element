package com.sxtanna.mc.element.system;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface ElementCloses extends AutoCloseable, ElementBinder
{

    @Override
    void close() throws Exception;


    @Override
    default void bind(@NotNull final ElementSystem system)
    {
        system.bind(this);
    }


    default void closeAndIgnore()
    {
        try
        {
            close();
        }
        catch (final Throwable ignored)
        {
            /* ignored exception */
        }
    }

    default void closeAndReport()
    {
        closeAndReport(System.err);
    }

    default void closeAndReport(@NotNull final PrintWriter pw)
    {
        try
        {
            close();
        }
        catch (final Throwable ex)
        {
            ex.printStackTrace(pw);
        }
    }

    default void closeAndReport(@NotNull final PrintStream ps)
    {
        try
        {
            close();
        }
        catch (final Throwable ex)
        {
            ex.printStackTrace(ps);
        }
    }

}
