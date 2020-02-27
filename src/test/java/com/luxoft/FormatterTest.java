package com.luxoft;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FormatterTest {
    Map<String, Object> map = null;
    private static final String ERROR = "Строка некорректна: \n   1. Пустая строка; \n   2. Строка == null;" +
            "\n   3. Должно быть равное количество открывающих '{' и закрывающих '}' place holder'ов";

    @Before
    public void createMap(){
        map = new HashMap<>();
        map.put("o1", "wednesday");
        map.put("name", "Masha");
        map.put("name1", null);
        map.put("z", "zerro");

    }


    @Test
    public void forArgs() throws Exception {

        //Должен вернуть ERROR, так как строка пустая
        assertEquals(ERROR, Formatter.forArgs("             ").format(new Object()));

        //Должен вернуть ERROR, так как строка содержит не закрывающиеся place holder'ы
        assertEquals(ERROR, Formatter.forArgs("{1} some failed string } ").format(new Object()));

        //Должен вернуть ERROR, так как строка ссылается на null
        assertEquals(ERROR, Formatter.forArgs(null).format(new Object()));

        //Должен расставить аргументы согласно их следованию в методе format(), лишние - игнорируются
        assertEquals("new Object() null some true additional string key", Formatter
                .forArgs("{} {} some {} additional string {}").format("new Object()", null, true, "key", "sword", "Messi"));

        // Должен расставить аргументы согласно их следованию в методе format().
        // При меньшем количестве аргументов в методе format() - place holder'ы из результирующей строки удаляются
        assertEquals("1 2 some  additional string ", Formatter
                .forArgs("{} {} some {} additional string {}").format("1", "2"));






        //Должен вернуть ту же строку, что и получил, так как она без place holder'ов
        assertEquals("to to to    ", Formatter.forArgs("to to to    ").format(new Object()));


        assertEquals("wednesday  some Masha additional string  wow", Formatter
                .forArgs("{   o1   } {     name1        } some {name } additional string { null} wow").format(map));

    }

    @Test
    public void testForArgs() {
    }
}