package com.sxtanna.mc.element.tuples;

import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface Tuple2<A, B> extends Tuple1<A>
{

    B b();

}
