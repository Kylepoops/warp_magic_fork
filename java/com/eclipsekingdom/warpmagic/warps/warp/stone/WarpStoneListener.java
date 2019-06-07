package com.eclipsekingdom.warpmagic.warps.warp.stone;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.util.ItemOperations;
import com.eclipsekingdom.warpmagic.warps.warp.WarpNumManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WarpStoneListener implements Listener {

    public WarpStoneListener(WarpMagic plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
            if(WarpStone.isWarpStone(e.getItem())){
                e.setCancelled(true);
                Player player = e.getPlayer();

                if( (warpNumManager.getUnlockedWarpNum(player) < pluginConfig.getMaxWarpNum())
                        || Permissions.hasNoWarpLimit(player) ){
                    warpNumManager.incrementWarpCapacity(player);
                    Notifications.sendSuccess(player, "+1 warp");
                    ItemOperations.consumeItem(e.getItem());
                }else{
                    Notifications.sendWarning(player, "You already have the maximum number of warps");
                }

            }
        }
    }

    private final WarpNumManager warpNumManager = WarpNumManager.getInstance();
    private final PluginConfig pluginConfig = PluginConfig.getInstance();


}
