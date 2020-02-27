package com.luxoft;

import java.util.*;

public class Formatter {


    public static Context forArgs(String message) throws Exception {
        int i;
        int counter = 0;
        boolean isEmpty = false;
        boolean isNumeric = false;
        boolean isString = false;

        List<Chunk> chunks = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        if (message != null && !message.isEmpty()) {

            if(!isCorrect(message)) return new NullContext(chunks);

            for (i = 0; i < message.length(); i++) {

                char outPlHol = message.charAt(i);

                if (outPlHol == '{') {
                    if (sb.length() > 0)
                        chunks.add(new StaticChunk(sb.toString()));

                    sb.setLength(0);

                    for (int j = i + 1; j < message.length(); j++) {

                        char inPlHol = message.charAt(j);

                        if (inPlHol == '}') {

                            String str = sb.toString().trim();
                            //Поиск первого цифрового значения в place holder'е
                            if (!isNumeric) {
                                isNumeric = isNumeric(str);
                            }
                            //Поиск первого пустого значения в place holder'е
                            if (!isEmpty) {
                                isEmpty = str.isEmpty();
                            }
                            //Поиск первого строкового значения в place holder'е
                            if (!isString && !str.isEmpty()) {
                                isString = !isNumeric(str);
                            }

                            if ((isNumeric && isEmpty) || (isEmpty && isString) || (isNumeric && isString)) {
                                throw new Exception("Разные значения в place holder'ах");
                            } else {
                                if (isNumeric) chunks.add(new IndexedChunk(Integer.parseInt(str)));
                                else if (isEmpty) chunks.add(new IndexedChunk(counter++));
                                else if (isString) chunks.add(new KeyChunk(str));
                            }
                            sb.setLength(0);

                            i = j;
                            break;
                        } else sb.append(inPlHol);
                    }
                } else sb.append(outPlHol);
            }


            if(sb.toString().trim().length() > 0) chunks.add(new StaticChunk(sb.toString()));
            sb = null;
            if (isNumeric || isEmpty) return new ArrayContext(chunks);

            else if (isString) return new MapContext(chunks);

        }
        return new NullContext(chunks);
    }

    private static boolean isNumeric(String sb) {
        try {
            Integer.parseInt(sb);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isCorrect(String str) {
        Deque<Character> stack = new ArrayDeque<>();
        boolean isTrue = true;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case '{':
                    stack.push(ch);
                    break;
                case '}':
                    if (stack.isEmpty() || stack.peek() != '{') {
                        isTrue = false;
                        break;
                    } else stack.pop();
            }
        }
        if (!stack.isEmpty()) isTrue = false;
        return isTrue;
    }
}
