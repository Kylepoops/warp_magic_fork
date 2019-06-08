package com.eclipsekingdom.warpmagic.warps.home;

import com.eclipsekingdom.warpmagic.util.data.DataType;
import com.eclipsekingdom.warpmagic.util.data.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.List;

public class HomeManager extends Manager<String,Home> {


    /* --- constructors --- */

    private HomeManager() {
        super(new DataType<Home>(null) {
            @Override
            public void writeTo(String path, Home home, FileConfiguration config) {
                Location location = home.getLocation();
                config.set(path+".world", location.getWorld().getName());
                config.set(path+".x", location.getX());
                config.set(path+".y", location.getY());
                config.set(path+".z", location.getY());
                config.set(path+".yaw", location.getYaw());
                config.set(path+".pitch", location.getPitch());
                List<String> friends = home.getFriends();
                if(friends.size() > 0){
                    config.set(path+".friends", friends);
                }
            }

            @Override
            public Home readFrom(String path, FileConfiguration config) {
                World world = Bukkit.getWorld(config.getString(path+".world"));
                double x = config.getDouble(path+".x");
                double y = config.getDouble(path+".y");
                double z = config.getDouble(path+".z");
                float yaw = (float)config.getDouble(path+".yaw");
                float pitch = (float)config.getDouble(path+".pitch");
                Location location = new Location(world, x, y, z, yaw, pitch);
                List<String> friends = config.getStringList(path+".friends");
                if(location != null){
                    return new Home(location, friends);
                }else{
                    return null;
                }
            }
        },"homes", "/data/home");
    }

    private static final HomeManager HOME_MANAGER = new HomeManager();

    public static final HomeManager getInstance(){
        return HOME_MANAGER;
    }


    /* --- interface --- */

    @Override
    public void load() {
        for(Player player: Bukkit.getOnlinePlayers()){
            cache(player.getDisplayName());
        }
    }

    public void registerHome(Player player, Home home){
        String playerName = player.getDisplayName();
        keyToData.put(playerName, home);
        trackUnsavedData(playerName);
    }

    public void removeHome(Player player){
        String playerName = player.getDisplayName();
        keyToData.remove(playerName);
        trackUnsavedData(playerName);
    }

    public Home getHome(Player player){
        String playerName = player.getDisplayName();
        return keyToData.get(playerName);
    }

    public Home getHome(String playerName){
        return keyToData.get(playerName);
    }


    /* --- implementation --- */

    @Override
    protected boolean stillNeeded(String playerName, String leavingPlayerName) {

        if(notLeavingPlayer(playerName, leavingPlayerName) && isOnline(playerName)){
            return true;
        }else{
            List<String> needyPlayers = relationsManager.getDependants(playerName);
            for(String needyPlayerName: needyPlayers){
                if(isOnline(needyPlayerName) && notLeavingPlayer(needyPlayerName, leavingPlayerName)){
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    protected List<String> getRequirements(String playerName) {
        return relationsManager.getRequirements(playerName);
    }

    private RelationsManager relationsManager = RelationsManager.getInstance();

    private boolean isOnline(String playerName){
        return (Bukkit.getPlayer(playerName) != null);
    }

    private boolean notLeavingPlayer(String playerName, String leavingPlayerName){
        return (!playerName.equalsIgnoreCase(leavingPlayerName));
    }

}
