package com.eclipsekingdom.warpmagic.data;

import com.eclipsekingdom.warpmagic.sys.ConsoleSender;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.Friend;
import com.eclipsekingdom.warpmagic.util.StorageString;
import com.eclipsekingdom.warpmagic.warp.Home;
import com.eclipsekingdom.warpmagic.warp.Warp;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class UserFlatFile {

    private static String header = "UserData";

    public static UserData fetch(UUID playerID){
        File file = new File("plugins/WarpMagic/Users", playerID + ".yml");
        if(file.exists()){
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            int unlockedWarps = config.getInt(header+".unlockedWarps");
            int unlockedVortexes = config.getInt(header+".unlockedVortexes");

            Home home = null;
            if(config.contains(header+".home")){
                Location location = StorageString.convertToLocation(config.getString(header+".home.location"));
                if(location != null){
                    List<Friend> friends = new ArrayList<>();
                    if(config.contains(header+".home.friends")){
                        for(String friendIDString: config.getConfigurationSection(header+".home.friends").getKeys(false)){
                            UUID ID = UUID.fromString(friendIDString);
                            String name = config.getString(header+".home.friends." + friendIDString);
                            friends.add(new Friend(ID, name));
                        }
                    }
                    home = new Home(location, friends);
                }
            }

            List<Warp> warps = new ArrayList<>();
            if(config.contains(header+".warps")){
                for(String warpName: config.getConfigurationSection(header+".warps").getKeys(false)){
                    Location location = StorageString.convertToLocation(config.getString(header+".warps."+warpName));
                    if(location != null){
                        warps.add(new Warp(warpName, location));
                    }
                }
            }

            List<Friend> friends = new ArrayList<>();
            if(config.contains(header+".friends")){
                for(String friendIDString: config.getConfigurationSection(header+".friends").getKeys(false)){
                    UUID friendID = UUID.fromString(friendIDString);
                    String name = config.getString(header+".friends."+friendIDString);
                    friends.add(new Friend(friendID, name));
                }
            }

            UserData userData = new UserData(home, warps, unlockedWarps, unlockedVortexes, friends);

            return userData;
        }else{
            return new UserData();
        }
    }

    public static void store(UUID playerID, UserData userData){
        File file = new File("plugins/WarpMagic/Users", playerID + ".yml");
        if(!userData.isEmpty() || file.exists()){
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            config.set(header, null);
            config.set(header+".unlockedWarps", userData.getUnlockedWarps());
            config.set(header+".unlockedVortexes", userData.getUnlockedVortexes());

            if(userData.hasHome()){
                Home home = userData.getHome();
                config.set(header+".home.location", StorageString.from(home.getLocation()));
                for(Friend friend: home.getFriends()){
                    config.set(header+".home.friends." + friend.getID(), friend.getName());
                }
            }

            List<Warp> warps = userData.getWarps();
            if(warps.size() > 0){
                for(Warp warp: warps){
                    config.set(header+".warps." + warp.getName(), StorageString.from(warp.getLocation()));
                }
            }

            List<Friend> friends = userData.getFriends();
            if(friends.size() > 0){
                for(Friend friend: friends){
                    config.set(header+".friends." + friend.getID(), friend.getName());
                }
            }

            save(config, file);
        }
    }

    private static void save(FileConfiguration config, File file){
        try{
            config.save(file);
        } catch (Exception e){
            ConsoleSender.sendMessage(Message.CONSOLE_FILE_ERROR.fromFile(file.getName()));
        }
    }


}
