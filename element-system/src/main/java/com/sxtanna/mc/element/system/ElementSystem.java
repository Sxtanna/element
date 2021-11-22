package com.sxtanna.mc.element.system;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

public interface ElementSystem extends ElementCloses
{

    <T extends AutoCloseable> @NotNull T bind(@NotNull final T closes);

    <T extends ElementModule> @NotNull T with(@NotNull final T module);


    default void bind(@NotNull final Collection<? extends AutoCloseable> closes)
    {
        closes.forEach(this::bind);
    }

    default void with(@NotNull final Collection<? extends ElementModule> modules)
    {
        modules.forEach(this::with);
    }



    class CloseException extends Exception
    {

        @NotNull
        private final List<? extends Throwable> causes;


        public CloseException(@NotNull final List<? extends Throwable> causes)
        {
            this.causes = causes;
        }


        @Override
        public void printStackTrace()
        {
            super.printStackTrace();

            causes.forEach(Throwable::printStackTrace);
        }

        @Override
        public void printStackTrace(final PrintStream s)
        {
            super.printStackTrace(s);

            causes.forEach(it -> it.printStackTrace(s));
        }

        @Override
        public void printStackTrace(final PrintWriter s)
        {
            super.printStackTrace(s);

            causes.forEach(it -> it.printStackTrace(s));
        }

    }

}
