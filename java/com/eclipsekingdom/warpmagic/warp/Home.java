package com.eclipsekingdom.warpmagic.warp;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Home {

    private Location location;
    private List<Friend> friends;

    public Home(Location location){
        this.location = location;
        this.friends = new ArrayList<>();
    }

    public Home(Location location, List<Friend> friends){
        this.location = location;
        this.friends = friends;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public Friend getFriend(String name){
        for(Friend friend: friends){
            if(friend.getName().equalsIgnoreCase(name)){
                return friend;
            }
        }
        return null;
    }

    public boolean isFriend(UUID playerID){
        for(Friend friend: friends){
            if(playerID.equals(friend.getID())){
                return true;
            }
        }
        return false;
    }

    public boolean isFriend(String friendName){
        for(Friend friend: friends){
            if(friendName.equalsIgnoreCase(friend.getName())){
                return true;
            }
        }
        return false;
    }

    public boolean isFriend(Friend friend){
        return isFriend(friend.getID());
    }

    public void addFriend(Friend friend){
        if(!friends.contains(friend)){
            friends.add(friend);
        }
    }

    public void remFriend(String friendName){
        for(int i= friends.size() -1; i>=0;i--){
            if(friends.get(i).getName().equalsIgnoreCase(friendName)){
                friends.remove(friends.get(i));
            }
        }
    }


}
