package com.sxtanna.mc.element.serial;

import com.sxtanna.mc.element.serial.json.Json;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JsonTests
{

    final JsonElement arr = Json.buildArr()
                                .add(Json.primitive(1))
                                .add(Json.primitive(2))
                                .add(Json.primitive(3))
                                .json();

    final JsonElement obj = Json.buildObj()
                                .add("usd", Json.primitive(100))
                                .add("cad", Json.primitive(128.12))
                                .json();


    @Test
    void testEncodeArr()
    {
        assertEquals(arr,
                     assertDoesNotThrow(() -> Json.encodeToJson(List.of(1, 2, 3))));
    }

    @Test
    void testDecodeArr()
    {
        assertEquals(List.of(1, 2, 3),
                     assertDoesNotThrow(() -> Json.decode(new TypeToken<List<Integer>>() {}, arr)));
    }


    @Test
    void testEncodeObj()
    {
        assertEquals(obj,
                     assertDoesNotThrow(() -> Json.encodeToJson(Map.of("usd", 100, "cad", 128.12))));
    }

    @Test
    void testDecodeObj()
    {
        assertEquals(Map.of("usd", BigDecimal.valueOf(100), "cad", BigDecimal.valueOf(128.12)),
                     assertDoesNotThrow(() -> Json.decode(new TypeToken<Map<String, BigDecimal>>() {}, obj)));
    }

}
