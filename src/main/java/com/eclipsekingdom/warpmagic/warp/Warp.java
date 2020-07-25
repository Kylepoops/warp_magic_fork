package com.eclipsekingdom.warpmagic.warp;

import org.bukkit.Location;

public class Warp {

    private String name;
    private Location location;

    public Warp(String name, Location location){
        this.name = name;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void updateLocation(Location location){
        this.location = location;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof String){
            return this.name.equals((o));
        }else{
            return super.equals(o);
        }
    }


}
