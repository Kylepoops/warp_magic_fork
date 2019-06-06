package com.eclipsekingdom.warpmagic.warp.data;

import com.eclipsekingdom.warpmagic.data.DataType;
import com.eclipsekingdom.warpmagic.data.Database;

import java.util.UUID;

public class WarpNumDatabase extends Database {

    private WarpNumDatabase(DataType dataType, String header, String fileName, String dirName) {
        super(dataType, header, fileName, dirName);
    }

    public static final WarpNumDatabase getInstance(){
        return WARP_NUM_DATABASE;
    }

    @Override
    protected Object convertPathToKey(String path) {
        return UUID.fromString(path);
    }

    private static final WarpNumDatabase WARP_NUM_DATABASE = new WarpNumDatabase(WarpNumDataType.getInstance(), "Warp Number", "warpNum", "/data/warp");


}
