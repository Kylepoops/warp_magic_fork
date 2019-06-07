package com.eclipsekingdom.warpmagic.warps.warp.data;

import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.data.DataType;
import org.bukkit.configuration.file.FileConfiguration;

public class WarpNumDataType extends DataType<Integer> {

    private WarpNumDataType(Integer defaultValue) {
        super(defaultValue);
    }

    public static final WarpNumDataType getInstance(){
        return WARP_NUM_DATA_TYPE;
    }

    @Override
    public boolean isTrivial(Integer object) {
        return false;
    }

    @Override
    public void writeTo(String path, Integer data, FileConfiguration config) {
        config.set(path, data);
    }

    @Override
    public Integer readFrom(String path, FileConfiguration config) {
        return config.getInt(path);
    }

    private static final WarpNumDataType WARP_NUM_DATA_TYPE = new WarpNumDataType( PluginConfig.getInstance().getStartingWarpNum() );


}
