package com.luxoft.impl.chunks;

import com.luxoft.ChunkType;
import com.luxoft.interf.Chunk;

public class KeyChunk implements Chunk {
    private final String value;
    private final ChunkType chunkType;

    public KeyChunk(String value, ChunkType chunkType) {
        this.value = value;
        this.chunkType = chunkType;
    }

    public ChunkType getChunkType() {
        return chunkType;
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
