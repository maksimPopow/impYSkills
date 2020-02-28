package com.luxoft;

public class IndexedChunk implements Chunk{
    private final Integer value;
    private final ChunkType chunkType;


    public IndexedChunk(Integer value, ChunkType chunkType) {
        this.value = value;
        this.chunkType = chunkType;
    }

    public ChunkType getChunkType() {
        return chunkType;
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
