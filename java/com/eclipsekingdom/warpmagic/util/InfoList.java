package com.eclipsekingdom.warpmagic.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoList {

    private static final String EMPTY_SLOT = "-";

    private String title;
    private List<String> items;
    private int pageItemCount;
    private String command;

    public InfoList(String title, List<String> items, int pageItemCount, String command){
        this.title = title;
        this.items = items;
        this.pageItemCount = pageItemCount;
        this.command = command;
    }

    public void displayTo(Player player, int page){
        if(page < 1){
            page = 1;
        }
        player.sendMessage(title);

        int startIndex = (page-1)*pageItemCount;
        for(int i= startIndex; i < startIndex+pageItemCount; i++){
            if(i < items.size()){
                player.sendMessage(items.get(i));
            }else{
                player.sendMessage(EMPTY_SLOT);
            }
        }

        player.sendMessage(buildNextPageTip(command, page));

    }

    private static final String buildNextPageTip(String command, int page){
        return (ChatColor.DARK_GRAY + "Use" + ChatColor.GRAY +" /" + command + " " + (page + 1) + ChatColor.DARK_GRAY + " for the next page");
    }

}