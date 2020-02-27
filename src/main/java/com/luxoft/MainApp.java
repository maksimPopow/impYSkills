package com.luxoft;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class MainApp {


    public static void main(String... args) throws Exception {
//case 1
//      String str = "{   o1   } {     name1        } some {name } additional string { null} wow";
//        Map<String, Object> map = new HashMap<>();
//        map.put("o1", "wednesday");
//        map.put("name", "Masha");
//        map.put("name1", null);
//        map.put("z", "zerro");
//        System.out.println(Formatter.forArgs(str).format(map));

//case 2
//        String str = "to tot to to to";
//        System.out.println(Formatter.forArgs(str).format(new Object()));


//case 3
//    String str = null;
//        Formatter.forArgs(str).format(new Object());


//case 4
//        String str = "     ";
//        System.out.println(Formatter.forArgs(str).format("1", "2"));

//case 5
        String str = "{5} {0} some {3} additional string {2}";
        System.out.println(Formatter.forArgs(str).format("new Object()", null, true, "key"));


//case 6
//        String str = "5 } some additional string ";
//        Formatter.forArgs(str).format(new HashMap<String, Object>(), null, true, "key");
    }
}

