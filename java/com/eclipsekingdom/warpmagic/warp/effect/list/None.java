package com.eclipsekingdom.warpmagic.warp.effect.list;

import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class None extends Effect {

    public None() {
        super(ChatColor.GRAY,"None", Material.BARRIER);
    }

    @Override
    public void run(Player player) {
        return;
    }
}
