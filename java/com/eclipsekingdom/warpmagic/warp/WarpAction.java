package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.warp.data.WarpManager;
import com.eclipsekingdom.warpmagic.warp.data.WarpNumManager;
import org.bukkit.entity.Player;

public abstract class WarpAction {

    public WarpAction(){
        this.description = initDescription();
    }

    public String getDescription() {
        return description;
    }

    public abstract void run(Player player, String[] args);


    protected final WarpNumManager warpNumManager = WarpNumManager.getInstance();
    protected final WarpManager warpManager = WarpManager.getInstance();

    protected abstract String initDescription();
    private final String description;
}
