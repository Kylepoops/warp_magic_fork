package com.eclipsekingdom.warpmagic.warps.vortex;

import com.eclipsekingdom.warpmagic.util.data.DataType;
import com.eclipsekingdom.warpmagic.util.data.Manager;
import com.eclipsekingdom.warpmagic.util.data.StorageString;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class VortexManager extends Manager<String, Vortex> {

    /* --- constructors --- */

    private VortexManager() {
        super(new DataType<Vortex>(null) {
            @Override
            public void writeTo(String path, Vortex vortex, FileConfiguration config) {
                config.set(path+".creatorName",vortex.getCreatorName());
                config.set(path+".location", StorageString.from(vortex.getLocation()));
            }

            @Override
            public Vortex readFrom(String path, FileConfiguration config) {
                String creatorName = config.getString(path+".creatorName");
                Location location = StorageString.convertToLocation(config.getString(path+".location"));
                if(location != null){
                    return new Vortex(path, location, creatorName);
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
            if(vortex.getCreatorName().equals(player.getDisplayName())){
                vortexes.add(vortex);
            }
        }
        return vortexes;
    }

    public Vortex getVortexSetBy(Player player, String vortexName) {
        Vortex vortex = keyToData.get(vortexName);
        if(vortex != null){
            if(vortex.getCreatorName().equals(player.getDisplayName())){
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
    protected boolean stillNeeded(String s, String leavingS) {
        return false;
    }

    @Override
    protected List<String> getRequirements(String s) {
        return Collections.emptyList();
    }

}
