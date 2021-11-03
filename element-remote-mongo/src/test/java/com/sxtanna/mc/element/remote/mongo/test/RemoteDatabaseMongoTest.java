package com.sxtanna.mc.element.remote.mongo.test;

import com.sxtanna.mc.element.remote.mongo.RemoteDatabaseMongo;
import com.sxtanna.mc.element.result.handling.Success;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class RemoteDatabaseMongoTest
{

    @Test
    void connect()
    {
        final var remote = new RemoteDatabaseMongo("mongodb://localhost:27017/?ssl=false");

        assertDoesNotThrow(remote::load);
        assertInstanceOf(Success.class, remote.connect());
    }

}
