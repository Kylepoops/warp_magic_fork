package com.eclipsekingdom.warpmagic.warp.effect;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Effect {

    private final String name;
    private final ChatColor color;
    private final Material material;

    public Effect(ChatColor color, String name, Material material){
        this.name = color+name;
        this.color = color;
        this.material = material;
    }

    public abstract void run(Player player);

    public String getName() {
        return name;
    }

    public ChatColor getColor(){
        return color;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Effect){
            return this.getName().equalsIgnoreCase(((Effect)o).getName());
        }else{
            return super.equals(o);
        }
    }
}
