package com.sxtanna.mc.element.result.throwing;

@FunctionalInterface
public interface ExceptionalConsumer<I>
{

    void accept(final I value) throws Throwable;

}