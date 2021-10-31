package com.sxtanna.mc.element.result.throwing;

@FunctionalInterface
public interface ExceptionalFunction<I, O>
{

    O apply(final I input) throws Throwable;

}