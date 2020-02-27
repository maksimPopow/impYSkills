package com.luxoft;

import java.util.List;

public class ArrayContext implements Context {
    private List<Chunk> chunks;

    public ArrayContext(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    @Override
    public String format(Object[] objects) {
        StringBuilder sb = new StringBuilder();

        if (objects != null && objects.length > 0) {
            for (int i = 0; i < chunks.size(); i++) {
                if (chunks.get(i) instanceof IndexedChunk) {
                    IndexedChunk ch = (IndexedChunk) chunks.get(i);
                    if (ch.getValue() < objects.length) sb.append(objects[ch.getValue()]);
                    else sb.append("");
                } else sb.append(chunks.get(i).getValue());
            }
        }

        return sb.toString();
    }
}
