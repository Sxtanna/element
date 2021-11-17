package com.sxtanna.mc.element.common.model;

import java.util.function.Supplier;

@FunctionalInterface
public interface Builder<T> extends Supplier<T>
{

    T build();


    @Override
    default T get()
    {
        return build();
    }

}
