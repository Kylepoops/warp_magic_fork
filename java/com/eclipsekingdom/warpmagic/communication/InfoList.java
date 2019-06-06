package com.eclipsekingdom.warpmagic.communication;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoList {

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

    private static final String EMPTY_SLOT = "-";

    private final String title;
    private final List<String> items;
    private final int pageItemCount;
    private final String command;

    private static final String buildNextPageTip(String command, int page){
        return (ChatColor.DARK_GRAY + "Use" + ChatColor.GRAY +" /" + command + ChatColor.DARK_GRAY +" " + (page + 1) + " for the next page");
    }

}