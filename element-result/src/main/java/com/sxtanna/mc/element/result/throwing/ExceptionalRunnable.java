package com.sxtanna.mc.element.result.throwing;

@FunctionalInterface
public interface ExceptionalRunnable
{

    void run() throws Throwable;

}