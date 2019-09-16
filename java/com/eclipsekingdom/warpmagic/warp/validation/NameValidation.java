package com.eclipsekingdom.warpmagic.warp.validation;

import java.util.ArrayList;
import java.util.List;

public class NameValidation {



    public static NameStatus clean(String name){
        if (!name.matches("^[a-zA-Z0-9\\_\\-]+$")) {
            return NameStatus.SPECIAL_CHARACTERS;
        }else if(name.length() > 20){
            return NameStatus.TOO_LONG;
        }else if(reservedWords.contains(name)) {
            return NameStatus.RESERVED_WORD;
        }else{
            return NameStatus.VALID;
        }
    }

    private static List<String> reservedWords = buildReservedWordList();

    private static List<String> buildReservedWordList(){
        List<String> reservedWords = new ArrayList<>();
        reservedWords.add("help");
        reservedWords.add("set");
        reservedWords.add("update");
        reservedWords.add("del");
        reservedWords.add("invite");
        reservedWords.add("uninvite");
        reservedWords.add("list");
        reservedWords.add("mylist");
        reservedWords.add("flist");
        reservedWords.add("fclear");
        return reservedWords;
    }


}