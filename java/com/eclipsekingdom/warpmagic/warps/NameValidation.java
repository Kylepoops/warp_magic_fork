package com.eclipsekingdom.warpmagic.warps;

import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NameValidation {

    public enum Status {

        VALID("success"),
        SPECIAL_CHARACTERS("Plot names must consist of only a-z, A-Z, 0-9, _, and -"),
        TOO_LONG("Plot names must be 20 characters or less"),
        NAME_TAKEN("You already have a plot with that name"),
        RESERVED_WORD("The name you selected is reserved by WarpMagic");

        Status(String message){
            this.message = message;
        }

        public final String message;

    }

    public static Status clean(Player player, String name){
        if (!name.matches("^[a-zA-Z0-9\\_\\-]+$")) {
            return Status.SPECIAL_CHARACTERS;
        }else if(name.length() > 20){
            return Status.TOO_LONG;
        }else if(reservedWords.contains(name)) {
            return Status.RESERVED_WORD;
        }else{
            boolean foundMatch = false;
            for(Warp warp: warpManager.getWarps(player)){
                if(warp.getName().equalsIgnoreCase(name)){
                    foundMatch = true;
                    break;
                }
            }
            if(foundMatch){
                return Status.NAME_TAKEN;
            }else{
                return Status.VALID;
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