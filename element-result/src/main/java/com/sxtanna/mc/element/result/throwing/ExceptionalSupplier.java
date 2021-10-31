package com.sxtanna.mc.element.result.throwing;

@FunctionalInterface
public interface ExceptionalSupplier<O>
{

    O get() throws Throwable;

}