package com.sxtanna.mc.element.tuples;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public enum Tuple
{
    ;


    @Contract(value = " -> new", pure = true)
    public static <K, V> @NotNull Collector<Tuple2<K, V>, ?, Map<K, V>> toMap()
    {
        return Collectors.toMap(Tuple2::a, Tuple2::b);
    }

    @Contract(value = " -> new", pure = true)
    public static <K, V> @NotNull Collector<Entry<K, V>, ?, List<Tuple2<K, V>>> toList()
    {
        return Collectors.mapping(e -> of(e.getKey(), e.getValue()), Collectors.toList());
    }


    @Contract(value = "_ -> new", pure = true)
    public static <A> @NotNull Tuple1<A>
    of(final A a)
    {
        return new Tuple1<>()
        {
            @Override
            public A a()
            {
                return a;
            }
        };
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <A, B> @NotNull Tuple2<A, B>
    of(final A a,
       final B b)
    {
        return new Tuple2<>()
        {
            @Override
            public A a()
            {
                return a;
            }

            @Override
            public B b()
            {
                return b;
            }
        };
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static <A, B, C> @NotNull Tuple3<A, B, C>
    of(final A a,
       final B b,
       final C c)
    {
        return new Tuple3<>()
        {
            @Override
            public A a()
            {
                return a;
            }

            @Override
            public B b()
            {
                return b;
            }

            @Override
            public C c()
            {
                return c;
            }
        };
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static <A, B, C, D> @NotNull Tuple4<A, B, C, D>
    of(final A a,
       final B b,
       final C c,
       final D d)
    {
        return new Tuple4<>()
        {
            @Override
            public A a()
            {
                return a;
            }

            @Override
            public B b()
            {
                return b;
            }

            @Override
            public C c()
            {
                return c;
            }

            @Override
            public D d()
            {
                return d;
            }
        };
    }

    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static <A, B, C, D, E> @NotNull Tuple5<A, B, C, D, E>
    of(final A a,
       final B b,
       final C c,
       final D d,
       final E e)
    {
        return new Tuple5<>()
        {
            @Override
            public A a()
            {
                return a;
            }

            @Override
            public B b()
            {
                return b;
            }

            @Override
            public C c()
            {
                return c;
            }

            @Override
            public D d()
            {
                return d;
            }

            @Override
            public E e()
            {
                return e;
            }
        };
    }

    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static <A, B, C, D, E, F> @NotNull Tuple6<A, B, C, D, E, F>
    of(final A a,
       final B b,
       final C c,
       final D d,
       final E e,
       final F f)
    {
        return new Tuple6<>()
        {
            @Override
            public A a()
            {
                return a;
            }

            @Override
            public B b()
            {
                return b;
            }

            @Override
            public C c()
            {
                return c;
            }

            @Override
            public D d()
            {
                return d;
            }

            @Override
            public E e()
            {
                return e;
            }

            @Override
            public F f()
            {
                return f;
            }
        };
    }

}
