package com.eclipsekingdom.warpmagic.warps.vortex;

import com.eclipsekingdom.warpmagic.util.data.DataType;
import com.eclipsekingdom.warpmagic.util.data.Manager;
import com.eclipsekingdom.warpmagic.util.operations.MapOperations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class VortexManager extends Manager<String, Vortex> {

    /* --- constructors --- */

    private VortexManager() {
        super(new DataType<Vortex>(null) {
            @Override
            public void writeTo(String path, Vortex vortex, FileConfiguration config) {
                config.set(path+".creatorID",vortex.getCreatorID());
                Location location = vortex.getLocation();
                config.set(path+".world", location.getWorld().getName());
                config.set(path+".x", location.getX());
                config.set(path+".y", location.getY());
                config.set(path+".z", location.getY());
                config.set(path+".yaw", location.getYaw());
                config.set(path+".pitch", location.getPitch());
                config.set(path+".world", location.getWorld().getName());
            }

            @Override
            public Vortex readFrom(String path, FileConfiguration config) {
                World world = Bukkit.getWorld(config.getString(path+".world"));
                UUID creatorID = UUID.fromString(config.getString(path+".creatorID"));
                double x = config.getDouble(path+".x");
                double y = config.getDouble(path+".y");
                double z = config.getDouble(path+".z");
                float yaw = (float)config.getDouble(path+".yaw");
                float pitch = (float)config.getDouble(path+".pitch");
                Location location = new Location(world, x, y, z, yaw, pitch);
                if(location != null){
                    return new Vortex(path, location, creatorID);
                }else{
                    return null;
                }
            }
        },"vortexes", "/data/vortex");
    }

    private static final VortexManager VORTEX_MANAGER = new VortexManager();

    public static final VortexManager getInstance(){
        return VORTEX_MANAGER;
    }


    /* --- interface --- */

    @Override
    public void load() {
        List<String> paths = database.getAllKeyPaths();
        for(String path: paths){
            cache(path);
        }
    }

    public void registerVortex(Vortex vortex){
        String vortexName = vortex.getName();
        keyToData.put(vortexName, vortex);
        trackUnsavedData(vortexName);
    }

    public void removeVortex(Vortex vortex){
        String vortexName = vortex.getName();
        keyToData.remove(vortexName);
        trackUnsavedData(vortexName);
    }

    public List<Vortex> getVortexesSetBy(Player player){
        List<Vortex> vortexes = new ArrayList<>();
        for(Vortex vortex: keyToData.values()){
            if(vortex.getCreatorID().equals(player.getUniqueId())){
                vortexes.add(vortex);
            }
        }
        return vortexes;
    }

    public Vortex getVortexSetBy(Player player, String vortexName) {
        Vortex vortex = keyToData.get(vortexName);
        if(vortex != null){
            if(vortex.getCreatorID().equals(player.getUniqueId())){
                return vortex;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public Collection<Vortex> getVortexes(){
        Collection<Vortex> vortexes = keyToData.values();
        if(vortexes != null){
            return vortexes;
        }else{
            return Collections.emptySet();
        }
    }

    public Vortex getVortex(String vortexName) {
        return keyToData.get(vortexName);
    }

    public int getUsedVortexCount(Player player){
        return getVortexesSetBy(player).size();
    }


    /* --- implementation --- */

    @Override
    protected boolean stillNeeded(String s) {
        return false;
    }

    @Override
    protected List<String> getRequirements(String s) {
        return null;
    }

}
