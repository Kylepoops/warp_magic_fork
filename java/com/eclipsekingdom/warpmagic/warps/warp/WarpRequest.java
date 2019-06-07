package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.warps.warp.actions.*;
import org.bukkit.entity.Player;

public enum WarpRequest {
    DEFAULT(new Default()),
    HELP(new Help()),
    SET(new Set()),
    DEL(new Del()),
    LIST(new WList());

    WarpRequest(WarpAction warpAction){
        this.warpAction = warpAction;
    }

    public static WarpRequest fromString(String string){
        for (WarpRequest request : WarpRequest.values()) {
            if (request.name().equalsIgnoreCase(string)) {
                return request;
            }
        }
        return DEFAULT;
    }

    public void process(Player player, String[] args) {
        warpAction.run(player, args);
    }

    private final WarpAction warpAction;
}
