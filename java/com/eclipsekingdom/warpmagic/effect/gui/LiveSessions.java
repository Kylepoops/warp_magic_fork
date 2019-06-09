package com.eclipsekingdom.warpmagic.effect.gui;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LiveSessions {

    private LiveSessions(){}

    private static final LiveSessions LIVE_SESSIONS = new LiveSessions();

    public static final LiveSessions getInstance(){
        return LIVE_SESSIONS;
    }


    public void launch(Player player){
        end(player);
        playersWithSession.add(player.getUniqueId());
        player.openInventory(menus.buildPlayerEffectMenu(player));
    }

    public void end(Player player){
        UUID ownerID = player.getUniqueId();
        while(playersWithSession.contains(ownerID)){
            playersWithSession.remove(ownerID);
        }
    }
    public boolean hasSession(Player player){
        UUID ownerID = player.getUniqueId();
        return playersWithSession.contains(ownerID);
    }

    private final Menus menus = Menus.getInstance();
    private final List<UUID> playersWithSession = new ArrayList<>();

}
