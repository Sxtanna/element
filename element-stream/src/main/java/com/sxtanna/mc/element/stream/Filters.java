package com.sxtanna.mc.element.stream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public enum Filters
{
    ;

    /**
     * Allows for a kotlin like `isInstance` filtering of a stream.
     *
     * <br> <br>
     * Usage: {@code Stream<T>#mapMulti(Filters.isInstance(SomeClass.class)) -> Stream<@NotNull SomeClass>}
     */
    @Contract(pure = true)
    public static <T, R> @NotNull BiConsumer<@Nullable T, @NotNull Consumer<R>> isInstance(@NotNull final Class<R> clazz)
    {
        return (t, consumer) -> {
            if (clazz.isInstance(t))
            {
                consumer.accept(clazz.cast(t));
            }
        };
    }

    /**
     * Allows for a kotlin like `isInstance` filtering of a stream.
     *
     * <br> <br>
     * Usage: {@code Stream<T>#map(Filters.isInstanceNullable(SomeClass.class)) -> Stream<@Nullable SomeClass>}
     */
    @Contract(pure = true)
    public static <T, R> @NotNull Function<@Nullable T, @Nullable R> isInstanceNullable(@NotNull final Class<R> clazz)
    {
        return t -> !clazz.isInstance(t) ? null : clazz.cast(t);
    }

}
