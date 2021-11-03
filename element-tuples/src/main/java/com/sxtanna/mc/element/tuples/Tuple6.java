package com.sxtanna.mc.element.tuples;

import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface Tuple6<A, B, C, D, E, F> extends Tuple5<A, B, C, D, E>
{

    F f();

}
