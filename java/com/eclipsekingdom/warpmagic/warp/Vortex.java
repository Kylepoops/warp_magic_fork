package com.eclipsekingdom.warpmagic.warp;

import org.bukkit.Location;

public class Vortex {

    private final String name;
    private Location location;
    private final String creatorName;

    public Vortex(String name, Location location, String creatorName){
        this.name = name;
        this.location = location;
        this.creatorName = creatorName;
    }

    public Location getLocation() {
        return location;
    }

    public void updateLocation(Location location){
        this.location = location;
    }

    public String getCreatorName(){
        return creatorName;
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
