package com.luxoft.impl.context;

import com.luxoft.ChunkType;
import com.luxoft.impl.chunks.KeyChunk;
import com.luxoft.interf.Chunk;
import com.luxoft.interf.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapContext implements Context {
    private List<Chunk> chunks;

    public MapContext(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    @Override
    public String format(Object... t) {
        String text = "Первым аргументом в метод format() передать HashMap<String, Object>";
        StringBuilder sb = new StringBuilder();
        Map<String, Object> map;

        if (t != null && t.length > 0 && t[0] instanceof Map) {

            try {
                map = (HashMap<String, Object>) t[0];
            } catch (ClassCastException e) {
                return "Первый аргумент необходимо передать HashMap<String, Object>";
            }

            for (Chunk chunk : chunks) {
                if (chunk.getChunkType() == ChunkType.KEY) {
                    KeyChunk ch = (KeyChunk) chunk;
                        sb.append(map.get(ch.getValue()));
                } else
                    sb.append(chunk.getValue());

            }
            text = sb.toString().trim();
        }
        return text;
    }
}
