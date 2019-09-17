package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.GlobalCache;
import com.eclipsekingdom.warpmagic.data.VortexCache;
import com.eclipsekingdom.warpmagic.warp.global.GlobalPoint;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dynmap {

    private MarkerAPI markerAPI;
    private DynmapAPI dynmapAPI;
    private MarkerIcon globalIcon;
    private MarkerIcon cardinalIcon;
    private MarkerIcon vortexIcon;
    private MarkerSet warpMagicSet;

    private Map<GlobalPoint, Marker> pointToMarker = new HashMap<>();
    private Map<String, Marker> vortexToMarker = new HashMap<>();

    public Dynmap(Plugin plugin){
        assert plugin instanceof DynmapAPI : "Plugin must be an instance of DynmapAPI";
        this.dynmapAPI = (DynmapAPI) plugin;
        this.markerAPI = dynmapAPI.getMarkerAPI();
        loadImages();
        loadIcons();
    }

    private void loadImages(){
        InputStream globalStream = WarpMagic.plugin.getResource("icons/warp-global.png");
        globalIcon = (markerAPI.getMarkerIcon("global") != null)? markerAPI.getMarkerIcon("global"): markerAPI.createMarkerIcon("global", "global", globalStream);

        InputStream cardinalStream = WarpMagic.plugin.getResource("icons/warp-cardinal.png");
        cardinalIcon = (markerAPI.getMarkerIcon("cardinal") != null)? markerAPI.getMarkerIcon("cardinal"): markerAPI.createMarkerIcon("cardinal", "cardinal", cardinalStream);

        InputStream vortexStream = WarpMagic.plugin.getResource("icons/warp-vortex.png");
        vortexIcon = (markerAPI.getMarkerIcon("vortex") != null)? markerAPI.getMarkerIcon("vortex"): markerAPI.createMarkerIcon("vortex", "vortex", vortexStream);

        Set<MarkerIcon> icons = new ImmutableSet.Builder<MarkerIcon>().add(globalIcon).add(cardinalIcon).add(vortexIcon).build();
        this.warpMagicSet = markerAPI.createMarkerSet("warpmagic.markerset", "Warp Points", icons, false);
    }

    private void loadIcons(){
        for(GlobalPoint globalPoint: GlobalPoint.values()){
            Location location = GlobalCache.get(globalPoint);
            if(location != null){
               setGlobalIconAt(globalPoint, location);
            }
        }
        for(Vortex vortex: VortexCache.getVortexes()){
            setVortexIcon(vortex);
        }
    }

    public void disable(){
        for(GlobalPoint globalPoint: GlobalPoint.values()){
            removeGlobalIcon(globalPoint);
        }
        for(String vortexName : vortexToMarker.keySet()){
            removeVortexIcon(vortexName);
        }
    }

    public void setGlobalIconAt(GlobalPoint point, Location location){
        if(pointToMarker.containsKey(point)){
            Marker marker = pointToMarker.get(point);
            marker.setLocation(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }else{
            MarkerIcon icon = (point == GlobalPoint.SPAWN || point == GlobalPoint.HUB)? globalIcon : cardinalIcon;
            String markerID = point.toString();
            Marker marker = warpMagicSet.createMarker(markerID, getFormattedString(point), location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), icon, false);
            pointToMarker.put(point, marker);
        }
    }

    private String getFormattedString(GlobalPoint globalPoint){
        return globalPoint.toString().charAt(0) + globalPoint.toString().substring(1).toLowerCase();
    }

    public void removeGlobalIcon(GlobalPoint point){
        if(pointToMarker.containsKey(point)){
            Marker marker = pointToMarker.get(point);
            marker.deleteMarker();
        }
    }

    public void setVortexIcon(Vortex vortex){
        Location location = vortex.getLocation();
        if(vortexToMarker.containsKey(vortex.getName())){
            Marker marker = vortexToMarker.get(vortex.getName());
            marker.setLocation(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }else{
            String markerID = "Vortex: " + vortex.getName();
            String markerName = "Vortex: " + vortex.getName();
            Marker marker = warpMagicSet.createMarker(markerID, markerName, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), vortexIcon, false);
            vortexToMarker.put(vortex.getName(), marker);
        }
    }

    public void removeVortexIcon(String vortexName){
        if(vortexToMarker.containsKey(vortexName)){
            Marker marker = vortexToMarker.get(vortexName);
            marker.deleteMarker();
        }
    }

}
