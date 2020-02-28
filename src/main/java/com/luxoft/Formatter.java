package com.luxoft;

import com.luxoft.impl.chunks.IndexedChunk;
import com.luxoft.impl.chunks.KeyChunk;
import com.luxoft.impl.chunks.StaticChunk;
import com.luxoft.impl.context.ArrayContext;
import com.luxoft.impl.context.MapContext;
import com.luxoft.impl.context.NullContext;
import com.luxoft.interf.Chunk;
import com.luxoft.interf.Context;
import com.luxoft.exceptions.FormatException;

import java.util.*;

public class Formatter {

    private Formatter() {
    }


    public static Context forArgs(String message) throws FormatException {

        List<Chunk> chunks = new ArrayList<>();

        if (message != null && !message.isEmpty()) {

            if (!isCorrectString(message)) return new NullContext(chunks);

            chunks = stringSeparator(message, chunks);
            
            ChunkType chunkType = defineType(chunks);

            if(chunkType == ChunkType.EMPTY || chunkType == ChunkType.NUMERIC) return new ArrayContext(chunks);
            else if(chunkType == ChunkType.KEY) return new MapContext(chunks);
            else return new NullContext(chunks);
        }
        return new NullContext(chunks);
    }

    private static List<Chunk> stringSeparator(String message, List<Chunk> chunks) {
        int i;
        int counter = 0;

        StringBuilder sb = new StringBuilder();

        for (i = 0; i < message.length(); i++) {

            char outPlHol = message.charAt(i);

            if (outPlHol == '{') {
                if (sb.length() > 0)
                    chunks.add(new StaticChunk(sb.toString(), ChunkType.STRING));
                sb.setLength(0);

                for (int j = i + 1; j < message.length(); j++) {

                    char inPlHol = message.charAt(j);

                    if (inPlHol == '}') {
                        String str = sb.toString().trim();


//                      надо заменить как то условный оператор вызовом метода
                        if (str.isEmpty()) chunks.add(chunkHelper(str, counter++));
                        else chunks.add(chunkHelper(str, -1));

                        sb.setLength(0);

                        i = j;
                        break;
                    } else sb.append(inPlHol);
                }
            } else sb.append(outPlHol);
        }

        if (sb.toString().trim().length() > 0) chunks.add(new StaticChunk(sb.toString(), ChunkType.STRING));

        return chunks;
    }

    private static Chunk chunkHelper(String subStr, int index) {
        if (isNumeric(subStr)) return new IndexedChunk(Integer.parseInt(subStr), ChunkType.NUMERIC);
        else if (index != -1) return new IndexedChunk(index, ChunkType.EMPTY);
        else return new KeyChunk(subStr, ChunkType.KEY);
    }

    private static ChunkType defineType(List<Chunk> chunks) throws FormatException {
        boolean isKey = false;
        boolean isNumeric = false;
        boolean isEmpty = false;
        ChunkType chunkType = ChunkType.STRING;
        
        for (Chunk chunk : chunks) {
            if (chunk.getChunkType() == ChunkType.EMPTY) {
                isEmpty = true;
                chunkType = ChunkType.EMPTY;
            } else if (chunk.getChunkType() == ChunkType.NUMERIC) {
                isNumeric = true;
                chunkType = ChunkType.NUMERIC;
            } else if (chunk.getChunkType() == ChunkType.KEY) {
                isKey = true;
                chunkType = ChunkType.KEY;
            }
        }

        if ((isEmpty && isKey) || (isEmpty && isNumeric) || (isNumeric && isKey))
            throw new FormatException("Разные типы аргументов в placeholder'ах");

        return chunkType;
    }

     private static boolean isNumeric(String sb) {
        try {
            Integer.parseInt(sb);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    private static boolean isCorrectString(String str) {
        Deque<Character> stack = new ArrayDeque<>();
        boolean isTrue = true;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '{') {
                stack.push(ch);
            }
            if (ch == '}') {
                if (stack.isEmpty() || stack.peek() != '{') {
                    isTrue = false;
                } else stack.pop();
            }
        }
        if (!stack.isEmpty()) isTrue = false;
        return isTrue;
    }
}
