package com.sxtanna.mc.element.tuples;

import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface Tuple5<A, B, C, D, E> extends Tuple4<A, B, C, D>
{

    E e();

}
