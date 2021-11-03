package com.sxtanna.mc.element.tuples;

import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface Tuple3<A, B, C> extends Tuple2<A, B>
{

    C c();

}
