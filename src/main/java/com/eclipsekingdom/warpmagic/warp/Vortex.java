package com.eclipsekingdom.warpmagic.warp;

import org.bukkit.Location;

public class Vortex extends Warp{

    private String creatorName;

    public Vortex(String name, Location location, String creatorName){
        super(name, location);
        this.creatorName = creatorName;
    }

    public String getCreatorName(){
        return creatorName;
    }

}
