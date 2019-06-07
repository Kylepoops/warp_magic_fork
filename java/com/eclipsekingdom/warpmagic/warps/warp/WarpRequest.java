package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.warps.warp.actions.Error;
import com.eclipsekingdom.warpmagic.warps.warp.actions.Set;
import com.eclipsekingdom.warpmagic.warps.warp.actions.WList;
import org.bukkit.entity.Player;

public enum WarpRequest {
    UNKNOWN(new Error()),
    SET(new Set()),
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
        return UNKNOWN;
    }

    public void process(Player player, String[] args) {
        warpAction.run(player, args);
    }

    private final WarpAction warpAction;
}
