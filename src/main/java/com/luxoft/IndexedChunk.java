package com.luxoft;

public class IndexedChunk implements Chunk{
    private Integer value;

    IndexedChunk(Integer value) {
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return value;
    }


    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
