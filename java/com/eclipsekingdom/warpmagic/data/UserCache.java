package com.eclipsekingdom.warpmagic.data;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.warp.Friend;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserCache implements Listener {

    private static HashMap<UUID, UserData> userToData = new HashMap<>();

    public UserCache(){
        WarpMagic plugin = WarpMagic.plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        load();
    }

    private void load(){
        for(Player player: Bukkit.getOnlinePlayers()){
            cache(player.getUniqueId());
            UserData userData = userToData.get(player.getUniqueId());
            for(Friend friend: userData.getFriends()){
                cache(friend.getID());
            }
        }
    }

    public static void save(){
        for(Map.Entry<UUID,UserData> entry: userToData.entrySet()){
            UserFlatFile.store(entry.getKey(), entry.getValue());
        }
    }

    @EventHandler
    public static void onJoin(PlayerJoinEvent e){
        UUID playerID = e.getPlayer().getUniqueId();
        cache(playerID);
        UserData userData = userToData.get(playerID);
        for(Friend friend: userData.getFriends()){
            cache(friend.getID());
        }
    }

    public static void cache(UUID playerID){
        if(!userToData.containsKey(playerID)){
            UserData userData = UserFlatFile.fetch(playerID);
            userToData.put(playerID, userData);
        }
    }

    @EventHandler
    public static void onQuit(PlayerQuitEvent e){
        UUID playerID = e.getPlayer().getUniqueId();
        UserData userData = userToData.get(playerID);
        if(!stillNeededByOthers(userData, playerID)){
            forget(playerID);
        }
        if(userData != null){
            for(Friend friend: userData.getFriends()){
                if(!friend.isOnline()){
                    UserData friendData = userToData.get(friend.getID());
                    if(!stillNeededByOthers(friendData, playerID)){
                        forget(friend.getID());
                    }
                }
            }
        }
    }

    private static boolean stillNeededByOthers(UserData userData, UUID leavingPlayerID){
        if(userData.hasHome()){
            for(Friend friend: userData.getHome().getFriends()){
                if(friend.isOnline() && !friend.getID().equals(leavingPlayerID)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void forget(UUID playerID){
        if(userToData.containsKey(playerID)){
            UserData userData = userToData.get(playerID);
            UserFlatFile.store(playerID, userData);
            userToData.remove(playerID);
        }
    }

    public static UserData getData(Player player){
        return userToData.get(player.getUniqueId());
    }

    public static UserData getData(UUID playerID){
        return userToData.get(playerID);
    }


}
