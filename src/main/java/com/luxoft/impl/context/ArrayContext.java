package com.luxoft.impl.context;

import com.luxoft.ChunkType;
import com.luxoft.impl.chunks.IndexedChunk;
import com.luxoft.interf.Chunk;
import com.luxoft.interf.Context;

import java.util.List;

public class ArrayContext implements Context {
    private List<Chunk> chunks;

    public ArrayContext(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    @Override
    public String format(Object ... objects) {
        StringBuilder sb = new StringBuilder();

        if (objects != null && objects.length > 0) {
            for (Chunk chunk : chunks) {
                if (chunk.getChunkType() == ChunkType.NUMERIC || chunk.getChunkType() == ChunkType.EMPTY) {
                    IndexedChunk ch = (IndexedChunk) chunk;
                    if (ch.getValue() < objects.length) sb.append(objects[ch.getValue()]);
                    else sb.append("null");
                } else sb.append(chunk.getValue());
            }
        }

        return sb.toString().trim();
    }
}
