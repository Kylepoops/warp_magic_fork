package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.warps.warp.data.WarpManager;
import com.eclipsekingdom.warpmagic.warps.warp.data.WarpNumManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class WarpHelp {

    public static void showTo(Player player){
        int plotsUsed = warpManager.getUsedWarpCount(player);
        int plotsMax = warpNumManager.getUnlockedWarpNum(player);
        player.sendMessage(WarpMagic.themeLight +""+ ChatColor.BOLD + "WarpMagic - Warps" + ChatColor.ITALIC+""+WarpMagic.themeDark + " - your warps: (" + plotsUsed + "/" + plotsMax + ")");
        player.sendMessage(ChatColor.YELLOW + "-------" + ChatColor.GOLD + " Commands " + ChatColor.YELLOW + "-------");

        for(Map.Entry<String, String> cmdDescriptString: commandToDescription.entrySet()){
            String cmdComponent = ChatColor.GOLD + "/" + cmdDescriptString.getKey();
            cmdComponent = cmdComponent.replace("[", ChatColor.RED +"[");
            cmdComponent = cmdComponent.replace("]", "]" + ChatColor.GOLD);

            String descriptComponent = ChatColor.RESET + cmdDescriptString.getValue();
            descriptComponent= descriptComponent.replace("[", ChatColor.RED + "[");
            descriptComponent = descriptComponent.replace("]", "]" + ChatColor.RESET);

            player.sendMessage(cmdComponent + ": " + descriptComponent);
        }

    }

    private static LinkedHashMap<String,String> commandToDescription = buildCommandMap();

    private static LinkedHashMap<String, String> buildCommandMap(){
        LinkedHashMap<String,String> commandMap = new LinkedHashMap<>();
        //commands
        commandMap.put("warp [name]", "teleport to [name]");
        commandMap.put("warp help", "show this message");
        commandMap.put("warp set [name]","set warp at location");
        commandMap.put("warp del [name]","remove warp");
        commandMap.put("warp list [name]", "list all warps");
        return commandMap;
    }

    private static final WarpManager warpManager = WarpManager.getInstance();
    private static final WarpNumManager warpNumManager = WarpNumManager.getInstance();
}
