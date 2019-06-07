package com.eclipsekingdom.warpmagic.warps.vortex;

import org.bukkit.Location;

import java.util.UUID;

public class Vortex {

    public Vortex(String name, Location location, UUID creatorID){
        this.name = name;
        this.location = location;
        this.creatorID = creatorID;
    }

    public Location getLocation() {
        return location;
    }

    public void updateLocation(Location location){
        this.location = location;
    }

    public UUID getCreatorID(){
        return creatorID;
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

    private final String name;
    private Location location;
    private final UUID creatorID;

}
