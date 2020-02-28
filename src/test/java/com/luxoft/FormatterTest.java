package com.luxoft;


import com.luxoft.exceptions.FormatException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FormatterTest {
    Map<String, Object> map = null;

    private static final String ERROR = "Строка некорректна: \n   1. Пустая строка; \n   2. Строка == null;" +
            "\n   3. Должно быть равное количество открывающих '{' и закрывающих '}' place holder'ов";

    public void createMap() {
        map = new HashMap<>();
        map.put("o1", "wednesday");
        map.put("name", "Masha");
        map.put("name1", null);
        map.put("z", "zerro");
    }


    //Должен вернуть ERROR, так как строка содержит не закрывающиеся placeholder'ы
    //Должен вернуть ERROR, так как строка пустая
    //Должен вернуть ERROR, так как строка ссылается на null
    @ParameterizedTest
    @ValueSource(strings = "{1} some failed string } ")
    @NullAndEmptySource
    public void checkingEmptyAndIncorrectArgs(String input) throws FormatException {
        assertEquals(ERROR, Formatter.forArgs(input).format("1", "2"));
    }


    // Должен расставить аргументы согласно их следованию в методе format();
    // Должен расставить аргументы согласно их следованию в методе format() или null, если placeholder'ов меньше;
    // Должен расставить аргументы согласно их следованию в методе forArgs(), лишние - игнорируются;
    // Должен расставить null если в {index} index больше количества аргументов в методе format()
    @ParameterizedTest
    @MethodSource("argumentsStringProvider")
    public void checkingEmptyAndNumericArgs(String input, String expected) throws FormatException {
        assertEquals(expected, Formatter.forArgs(input).format("", null, true, "key", "sword", "Messi"));
    }

    //Должен вернуть ту же строку, что и получил, так как она без placeholder'ов
    @ParameterizedTest
    @CsvSource({"to to to     ,to to to     ",
            " i i i i ,  i i i i "})
    public void checkingTheSameString(String input, String expected) throws FormatException {
        assertEquals(expected, Formatter.forArgs(input).format("1", "2"));
    }



    @ParameterizedTest
    @MethodSource("argumentsStringProviderForMap")
    public void checkingKeyArgs(String input, String expected) throws FormatException{
        createMap();

        assertEquals(expected, Formatter.forArgs(input).format(map, "1", new Object()));
    }


//@Test(expected = FormatException.class)
//@ValueSource(strings = "{1} some failed string {}")
//public void checkingFormatException() throws FormatException{
//        assertEquals(ERROR, Formatter.forArgs("{1} some failed string } ").format(new Object()));
//
//}

    private static Stream<Arguments> argumentsStringProvider() {
        return Stream.of(
                Arguments.of("{} {} some {} additional string {}", "null some true additional string key"),
                Arguments.of("{} {} some {} additional {} and {} string {} {} {}", "null some true additional key and sword string Messi null null"),
                Arguments.of("{3} {2} some {1} additional string {4}", "key true some null additional string sword"),
                Arguments.of("{6} {7} some {8} additional string {9}", "null null some null additional string null")
        );
    }

    private static Stream<Arguments> argumentsStringProviderForMap() {
        return Stream.of(
                Arguments.of("{o1} {name1} some {name} additional string {z} wow", "wednesday null some Masha additional string zerro wow"),
                Arguments.of("{ o1 } {  name1} some {name  } additional string {   z   } wow", "wednesday null some Masha additional string zerro wow"),
                Arguments.of("{null} {null} some {null} additional string {null} wow", "null null some null additional string null wow")
        );
    }
}