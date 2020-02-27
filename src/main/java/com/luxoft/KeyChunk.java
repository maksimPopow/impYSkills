package com.luxoft;

public class KeyChunk implements Chunk {
    private String value;

    KeyChunk(String value) {
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
