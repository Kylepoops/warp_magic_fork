package com.eclipsekingdom.warpmagic.warp.data;

import com.eclipsekingdom.warpmagic.data.DataType;
import com.eclipsekingdom.warpmagic.data.Database;
import org.bukkit.Location;

import java.util.UUID;

public class WarpDatabase extends Database<UUID, Location> {

    private WarpDatabase(DataType dataType, String header, String fileName, String dirName) {
        super(dataType, header, fileName, dirName);
    }

    public static final WarpDatabase getInstance(){
        return WARP_DATABASE;
    }

    @Override
    protected UUID convertPathToKey(String path) {
        return UUID.fromString(path);
    }

    private static final WarpDatabase WARP_DATABASE = new WarpDatabase(WarpDataType.getInstance(), "Warps", "warps", "data/warp");
}
