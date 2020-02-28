package com.luxoft;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class MainApp {


    public static void main(String... args) throws Exception {
//case 1
      String str = "{null} {null} some {null} additional string {null} wow";
        Map<String, Object> map = new HashMap<>();
        map.put("o1", "wednesday");
        map.put("name", "Masha");
        map.put("name1", null);
        map.put("z", "zerro");
        System.out.println(Formatter.forArgs(str).format(map, new Object()));

    }
}

