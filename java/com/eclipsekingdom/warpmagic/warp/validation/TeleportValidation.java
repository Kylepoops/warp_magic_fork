package com.eclipsekingdom.warpmagic.warp.validation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportValidation {

    public static TeleportStatus getStatus(Player player, Location location) {
        if (location != null) {
            return TeleportStatus.VALID;
        } else {
            return TeleportStatus.LOCATION_NOT_FOUND;
        }
    }


}
