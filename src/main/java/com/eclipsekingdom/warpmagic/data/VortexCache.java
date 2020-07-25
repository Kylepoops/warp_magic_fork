package com.eclipsekingdom.warpmagic.data;

import com.eclipsekingdom.warpmagic.warp.Vortex;
import org.bukkit.entity.Player;

import java.util.*;

public class VortexCache {

    private static VortexFlatFile vortexFlatFile = new VortexFlatFile();

    private static HashMap<String, Vortex> nameToVortex = new HashMap<>();

    public VortexCache(){
        load();
    }

    private void load(){
        List<Vortex> vortexes = vortexFlatFile.fetch();
        for(Vortex vortex: vortexes){
            nameToVortex.put(vortex.getName(), vortex);
        }
    }

    public static void save(){
        vortexFlatFile.store(nameToVortex.values());
    }

    public static void registerVortex(Vortex vortex){
        nameToVortex.put(vortex.getName(), vortex);
    }

    public static void removeVortex(Vortex vortex){
        nameToVortex.remove(vortex.getName());
    }

    public static List<Vortex> getVortexesSetBy(Player player){
        List<Vortex> vortexes = new ArrayList<>();
        for(Vortex vortex: nameToVortex.values()){
            if(vortex.getCreatorName() != null && vortex.getCreatorName().equals(player.getName())){
                vortexes.add(vortex);
            }
        }
        return vortexes;
    }

    public static Collection<Vortex> getVortexes(){
        return nameToVortex.values();
    }

    public static Vortex getVortex(String vortexName) {
        return nameToVortex.get(vortexName);
    }

}
