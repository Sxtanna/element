package com.sxtanna.mc.element.stream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

public enum Mappers
{
    ;

    /**
     * Allows for a kotlin like `chunked` batch processing of a stream.
     *
     * <br> <br>
     * Usage: {@code Stream<T>#collect(Mappers.chunked(5)) -> Stream<List<T>>}
     *
     * @see <a href="https://stackoverflow.com/a/50875545/6114308">StackOverflow Comment</a> <3
     */
    @Contract("_ -> new")
    public static <T> @NotNull Collector<T, List<List<T>>, Stream<List<T>>> chunked(int size)
    {
        return Collector.of(ArrayList::new,
                            (list, item) -> {
                                if (list.isEmpty() || list.get(list.size() - 1).size() >= size)
                                {
                                    list.add(new ArrayList<>(size));
                                }

                                list.get(list.size() - 1).add(item);
                            },
                            (a, b) -> {
                                a.addAll(b);
                                return a;
                            },
                            List::stream,
                            Collector.Characteristics.UNORDERED);
    }

}
