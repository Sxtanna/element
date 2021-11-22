package com.sxtanna.mc.element.system;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public enum Sys
{
    ;


    @Contract(" -> new")
    public static @NotNull ElementSystem create()
    {
        return create(new ConcurrentLinkedDeque<>());
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull ElementSystem create(@NotNull final Deque<AutoCloseable> deque)
    {
        return new ElementSystem()
        {
            @Override
            public <T extends AutoCloseable> @NotNull T bind(@NotNull final T closes)
            {
                deque.addFirst(closes);
                return closes;
            }

            @Override
            public <T extends ElementModule> @NotNull T with(@NotNull final T module)
            {
                module.setup(this);
                return module;
            }


            @Override
            public void close() throws Exception
            {
                final var caught = new ArrayList<Throwable>();

                for (final var closes : deque)
                {
                    try
                    {
                        closes.close();
                    }
                    catch (final Throwable ex)
                    {
                        caught.add(ex);
                    }
                }

                deque.clear();

                if (!caught.isEmpty())
                {
                    throw new ElementSystem.CloseException(caught);
                }
            }
        };
    }

}
