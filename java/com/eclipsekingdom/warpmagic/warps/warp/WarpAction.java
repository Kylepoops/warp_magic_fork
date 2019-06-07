package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.warps.warp.data.WarpManager;
import com.eclipsekingdom.warpmagic.warps.warp.data.WarpNumManager;
import org.bukkit.entity.Player;

public abstract class WarpAction {

    public abstract void run(Player player, String[] args);

    protected final WarpNumManager warpNumManager = WarpNumManager.getInstance();
    protected final WarpManager warpManager = WarpManager.getInstance();

}
