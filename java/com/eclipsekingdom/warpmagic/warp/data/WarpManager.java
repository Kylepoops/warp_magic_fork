package com.eclipsekingdom.warpmagic.warp.data;

import com.eclipsekingdom.warpmagic.data.DataType;
import com.eclipsekingdom.warpmagic.data.Database;
import com.eclipsekingdom.warpmagic.data.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.xml.stream.Location;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class WarpManager extends Manager<UUID, Location> {

    private WarpManager(DataType dataType, Database database) {
        super(dataType, database);
    }

    public static final WarpManager getInstance(){
        return WARP_MANAGER;
    }

    @Override
    public void load() {
        for(Player player: Bukkit.getOnlinePlayers()){
            cash(player.getUniqueId());
        }
    }

    @Override
    protected boolean stillNeeded(UUID uuid) {
        return false;
    }

    @Override
    protected List<UUID> getRequirements(UUID uuid) {
        return Collections.emptyList();
    }

    private static final WarpManager WARP_MANAGER = new WarpManager(WarpDataType.getInstance(), WarpDatabase.getInstance());
}
