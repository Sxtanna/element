package com.sxtanna.mc.element.inject;

import com.google.inject.Module;
import com.google.inject.*;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Message;
import com.google.inject.spi.ProvisionListener;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeListener;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class ElementInjectorModule extends AbstractModule
{

    @Override
    protected abstract void configure();


    @Override
    public Binder binder()
    {
        return super.binder();
    }

    @Override
    public void bindScope(final Class<? extends Annotation> scopeAnnotation, final Scope scope)
    {
        super.bindScope(scopeAnnotation, scope);
    }

    @Override
    public <T> LinkedBindingBuilder<T> bind(final Key<T> key)
    {
        return super.bind(key);
    }

    @Override
    public <T> AnnotatedBindingBuilder<T> bind(final TypeLiteral<T> typeLiteral)
    {
        return super.bind(typeLiteral);
    }

    @Override
    public <T> AnnotatedBindingBuilder<T> bind(final Class<T> clazz)
    {
        return super.bind(clazz);
    }

    @Override
    public AnnotatedConstantBindingBuilder bindConstant()
    {
        return super.bindConstant();
    }

    @Override
    public void install(final Module module)
    {
        super.install(module);
    }

    @Override
    public void addError(final String message, final Object... arguments)
    {
        super.addError(message, arguments);
    }

    @Override
    public void addError(final Throwable t)
    {
        super.addError(t);
    }

    @Override
    public void addError(final Message message)
    {
        super.addError(message);
    }

    @Override
    public void requestInjection(final Object instance)
    {
        super.requestInjection(instance);
    }

    @Override
    public void requestStaticInjection(final Class<?>... types)
    {
        super.requestStaticInjection(types);
    }

    @Override
    public void bindInterceptor(final Matcher<? super Class<?>> classMatcher, final Matcher<? super Method> methodMatcher, final MethodInterceptor... interceptors)
    {
        super.bindInterceptor(classMatcher, methodMatcher, interceptors);
    }

    @Override
    public void requireBinding(final Key<?> key)
    {
        super.requireBinding(key);
    }

    @Override
    public void requireBinding(final Class<?> type)
    {
        super.requireBinding(type);
    }

    @Override
    public <T> Provider<T> getProvider(final Key<T> key)
    {
        return super.getProvider(key);
    }

    @Override
    public <T> Provider<T> getProvider(final Class<T> type)
    {
        return super.getProvider(type);
    }

    @Override
    public void convertToTypes(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeConverter converter)
    {
        super.convertToTypes(typeMatcher, converter);
    }

    @Override
    public Stage currentStage()
    {
        return super.currentStage();
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(final Class<T> type)
    {
        return super.getMembersInjector(type);
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(final TypeLiteral<T> type)
    {
        return super.getMembersInjector(type);
    }

    @Override
    public void bindListener(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeListener listener)
    {
        super.bindListener(typeMatcher, listener);
    }

    @Override
    public void bindListener(final Matcher<? super Binding<?>> bindingMatcher, final ProvisionListener... listener)
    {
        super.bindListener(bindingMatcher, listener);
    }

}
