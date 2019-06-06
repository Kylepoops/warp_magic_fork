package com.eclipsekingdom.warpmagic.warp.data;

import com.eclipsekingdom.warpmagic.data.DataType;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class WarpDataType extends DataType<Location> {

    private WarpDataType(Location defaultValue) {
        super(defaultValue);
    }

    public static final WarpDataType getInstance(){
        return WARP_DATA_TYPE;
    }

    @Override
    public boolean isTrivial(Location location) {
        return false;
    }

    @Override
    public void writeTo(String path, Location data, FileConfiguration config) {

    }

    @Override
    public Location readFrom(String path, FileConfiguration config) {
        return null;
    }

    private static final WarpDataType WARP_DATA_TYPE = new WarpDataType(null);

}
