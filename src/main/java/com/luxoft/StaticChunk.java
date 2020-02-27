package com.luxoft;

public class StaticChunk implements Chunk{
    private final String value;

    StaticChunk(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
