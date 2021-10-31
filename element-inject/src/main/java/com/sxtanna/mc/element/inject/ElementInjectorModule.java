package com.sxtanna.mc.element.inject;

import com.google.inject.AbstractModule;

public abstract class ElementInjectorModule extends AbstractModule
{

    @Override
    protected abstract void configure();

}
