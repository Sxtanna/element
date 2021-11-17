package com.sxtanna.mc.element.common.model;

import java.util.function.Function;

@FunctionalInterface
public interface Builder1<I, T> extends Function<I, T>
{

    T build(I value);


    @Override
    default T apply(I i)
    {
        return build(i);
    }

}
