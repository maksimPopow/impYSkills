package com.luxoft;

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
        Map<String, Object> map = null;

        if (t != null && t.length > 0) {
            if (t[0] instanceof Map) {
                map = (HashMap<String, Object>) t[0];

                for (int i = 0; i < chunks.size(); i++) {
                    if (chunks.get(i) instanceof KeyChunk) {
                        KeyChunk ch = (KeyChunk) chunks.get(i);
                        Object o = map.get(ch.getValue());
                        if (o == null) sb.append("");
                        else sb.append(o);
                    } else
                        sb.append(chunks.get(i).getValue());

                }
                text = sb.toString();
            }
        }
        return text;
    }
}
