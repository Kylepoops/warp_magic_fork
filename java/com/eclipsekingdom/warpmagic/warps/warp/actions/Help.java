package com.eclipsekingdom.warpmagic.warps.warp.actions;

import com.eclipsekingdom.warpmagic.warps.warp.WarpAction;
import com.eclipsekingdom.warpmagic.warps.warp.WarpHelp;
import org.bukkit.entity.Player;

public class Help extends WarpAction {
    @Override
    public void run(Player player, String[] args) {
        WarpHelp.showTo(player);
    }
}
