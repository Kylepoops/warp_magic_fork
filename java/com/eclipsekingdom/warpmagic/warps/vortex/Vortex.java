package com.eclipsekingdom.warpmagic.warps.vortex;

import org.bukkit.Location;

public class Vortex {

    private final String name;
    private Location location;
    private final String creatorName;
    private boolean hideName;

    public Vortex(String name, Location location, String creatorName, boolean hideName){
        this.name = name;
        this.location = location;
        this.creatorName = creatorName;
        this.hideName = hideName;
    }


    public Vortex(String name, Location location, String creatorName){
        this.name = name;
        this.location = location;
        this.creatorName = creatorName;
        this.hideName = false;
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

    public boolean isHideName(){
        return hideName;
    }

    public void setHideName(boolean hideName){
        this.hideName = hideName;
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
