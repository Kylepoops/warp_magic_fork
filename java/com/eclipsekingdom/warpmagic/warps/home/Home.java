package com.eclipsekingdom.warpmagic.warps.home;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Home {
    public Home(Location location){
        this.location = location;
    }

    public Home(Location location, List<String> friends){
        this.location = location;
        this.friends = friends;
    }

    public Location getLocation() {
        return location;
    }

    public void updateLocation(Location location){
        this.location = location;
    }

    public List<String> getFriends() {
        return friends;
    }

    public boolean trusts(String friendName){
        return friends.contains(friendName);
    }

    public void addFriend(String friendName){
        if(!friends.contains(friendName)){
            friends.add(friendName);
        }
    }

    public void remFriend(String friendName){
        while (friends.contains(friendName)){
            friends.remove(friendName);
        }
    }



    private Location location;
    private List<String> friends = new ArrayList<>();
}
