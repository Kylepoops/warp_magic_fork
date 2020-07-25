package com.eclipsekingdom.warpmagic.warp.requests;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class LiveRequests {

    public static void registerRequest(Player target, Request request){
        targetToRequester.put(target.getUniqueId(), request);
    }

    public static void resolveRequest(Player target){
        targetToRequester.remove(target.getUniqueId());
    }

    public static UUID getCurrentTransactionID(Player target){
        if(targetToRequester.containsKey(target.getUniqueId())){
            return targetToRequester.get(target.getUniqueId()).getTransactionID();
        }else {
            return null;
        }
    }

    public static Request getRequest(Player target){
        return targetToRequester.get(target.getUniqueId());
    }

    private static final HashMap<UUID, Request> targetToRequester = new HashMap<>();

}
