package com.luxoft;

import java.util.List;
import java.util.stream.Collectors;

public class NullContext implements Context {
    private List<Chunk> chunks;

    public NullContext(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    @Override
    public String format(Object... t) {
        if (!chunks.isEmpty())
            return chunks.stream().map(a -> a.getValue() + "").collect(Collectors.joining());
        else
            return "Строка некорректна: \n   1. Пустая строка; \n   2. Строка == null;" +
                    "\n   3. Должно быть равное количество открывающих '{' и закрывающих '}' place holder'ов" ;
    }
}
