package com.sxtanna.mc.element.tuples;

import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface Tuple4<A, B, C, D> extends Tuple3<A, B, C>
{

    D d();

}
