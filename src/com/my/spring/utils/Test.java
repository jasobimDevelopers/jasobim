package com.my.spring.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) {
       String str = new String("12311");
       List<String> list = new ArrayList<String>();
       list.add(str);
       str="aaaaa";
       System.out.println(list.get(0));
    }
}
