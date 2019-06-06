package com.eclipsekingdom.warpmagic.data;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class DataType<VALUE> {

    protected DataType(VALUE defaultValue){
        this.defaultValue = defaultValue;
    }

    public VALUE getDefaultValue(){
        return defaultValue;
    }

    public abstract boolean isTrivial(VALUE value);

    public abstract void writeTo(String path, VALUE data, FileConfiguration config);
    public abstract VALUE readFrom(String path, FileConfiguration config);

    private final VALUE defaultValue;

}
