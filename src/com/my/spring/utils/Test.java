package com.my.spring.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String in=new String("thanks");
        List<String> list=Arrays.asList(in.split(""));
        Collections.shuffle(list);
        String out=new String();
        for(String s:list){
            out+=s;
        }
        System.out.println(out);
    }
}
