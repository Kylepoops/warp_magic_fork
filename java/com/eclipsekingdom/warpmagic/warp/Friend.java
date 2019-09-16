package com.eclipsekingdom.warpmagic.warp;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Friend {

    private UUID ID;
    private String name;

    public Friend(UUID ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public UUID getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline(){
        return Bukkit.getPlayer(ID) != null;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(ID);
    }

    public static Friend from(String targetName){
        Player onlineTarget = Bukkit.getPlayer(targetName);
        if(onlineTarget != null){
            return new Friend(onlineTarget.getUniqueId(), onlineTarget.getName());
        }else{
            OfflinePlayer offlineTarget = null;
            for(OfflinePlayer offlinePlayer: Bukkit.getOfflinePlayers()){
                if(offlinePlayer.getName().equalsIgnoreCase(targetName)){
                    offlineTarget = offlinePlayer;
                    break;
                }
            }
            if(offlineTarget != null){
                return new Friend(offlineTarget.getUniqueId(), offlineTarget.getName());
            }else{
                return null;
            }
        }
    }

}
