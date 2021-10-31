package com.sxtanna.mc.element.inject.test;

import com.sxtanna.mc.element.inject.ElementInjector;
import com.sxtanna.mc.element.inject.ElementInjectorModule;

import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public final class ElementInjectorTest
{


    @Singleton
    public static final class Main
    {
        public final int value = ThreadLocalRandom.current().nextInt(0, 100);
    }


    public interface Skeleton
    {
        String name();
    }


    public static final class ModuleOne extends ElementInjectorModule
    {

        @Override
        protected void configure()
        {
            bind(Main.class).toInstance(new Main());

            bind(Skeleton.class).annotatedWith(Names.named("one")).toInstance(new Skeleton() {
                @Override
                public String name()
                {
                    return "one";
                }
            });
        }

    }

    public static final class ModuleTwo extends ElementInjectorModule
    {

        @Override
        protected void configure()
        {
            bind(Skeleton.class).annotatedWith(Names.named("two")).toInstance(new Skeleton() {
                @Override
                public String name()
                {
                    return "two";
                }
            });
        }

    }



    @Test
    void test_add_module()
    {
        final var injector = new ElementInjector(new ModuleOne());

        final var main1 = injector.get(Main.class);
        System.out.println(main1.value);

        injector.add(new ModuleTwo());

        final var main2 = injector.get(Main.class);
        System.out.println(main2.value);

        System.out.println(main1 == main2);
    }

}
