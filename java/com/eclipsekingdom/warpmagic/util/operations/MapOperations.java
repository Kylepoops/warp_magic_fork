package com.eclipsekingdom.warpmagic.util.operations;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapOperations {


    public static void addItemToList(Map map, Object key, Object item){
        try{
            if(map.containsKey(key)){
                List list = (List) map.get(key);
                while (list.contains(item)){
                    list.remove(item);
                }
                list.add(item);
            }else{
                List list = new ArrayList<>();
                list.add(item);
                map.put(key, list);
            }
        }catch (Exception e){
            Bukkit.getConsoleSender().sendMessage("Map must be in form of Map<Object,PList<Object>>");
        }

    }

    public static void removeItemFromList(Map map, Object key, Object item){
        try{
            if(map.containsKey(key)){
                List list = (List) map.get(key);
                while(list.contains(item)){
                    list.remove(item);
                }
                if(list.size() < 1){
                    map.remove(key);
                }
            }
        }catch (Exception e){
            Bukkit.getConsoleSender().sendMessage("Map must be in form of Map<Object,PList<Object>>");
        }
    }



}