package com.sxtanna.mc.element.common.state;

public interface State extends Loads, Kills
{

    @Override
    void load();

    @Override
    void kill();

}
