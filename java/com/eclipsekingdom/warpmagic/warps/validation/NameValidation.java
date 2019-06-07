package com.eclipsekingdom.warpmagic.warps.validation;

import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.warps.warp.data.WarpManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NameValidation {

    public static NameStatus clean(Player player, String name){
        if (!name.matches("^[a-zA-Z0-9\\_\\-]+$")) {
            return NameStatus.SPECIAL_CHARACTERS;
        }else if(name.length() > 20){
            return NameStatus.TOO_LONG;
        }else if(reservedWords.contains(name)) {
            return NameStatus.RESERVED_WORD;
        }else{
            boolean foundMatch = false;
            for(Warp warp: warpManager.getWarps(player)){
                if(warp.getName().equalsIgnoreCase(name)){
                    foundMatch = true;
                    break;
                }
            }
            if(foundMatch){
                return NameStatus.NAME_TAKEN;
            }else{
                return NameStatus.VALID;
            }
        }
    }

    private static WarpManager warpManager = WarpManager.getInstance();

    private static List<String> reservedWords = buildReservedWordList();

    private static List<String> buildReservedWordList(){
        List<String> reservedWords = new ArrayList<>();
        reservedWords.add("list");
        reservedWords.add("invite");
        reservedWords.add("set");
        reservedWords.add("del");
        reservedWords.add("uninvite");
        reservedWords.add("help");
        return reservedWords;
    }


}