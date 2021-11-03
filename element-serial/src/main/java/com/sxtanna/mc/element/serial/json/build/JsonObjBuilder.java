package com.sxtanna.mc.element.serial.json.build;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public record JsonObjBuilder(@NotNull JsonObject json)
{

    public JsonObjBuilder()
    {
        this(new JsonObject());
    }


    @Contract("_, _ -> this")
    public @NotNull JsonObjBuilder add(@NotNull final String name, @NotNull final JsonElement json)
    {
        this.json().add(name, json);
        return this;
    }

    @Contract("_ -> this")
    public @NotNull JsonObjBuilder add(@NotNull final Map<String, JsonElement> json)
    {
        json.forEach(this::add);
        return this;
    }

}