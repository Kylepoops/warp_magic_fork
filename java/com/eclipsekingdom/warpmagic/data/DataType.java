package com.eclipsekingdom.warpmagic.data;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class DataType<T> {

    protected DataType(T defaultT){
        this.defaultValue = defaultT;
    }

    public T getDefaultValue(){
        return defaultValue;
    }

    public abstract boolean isTrivial(T T);

    public abstract void writeTo(String path, T data, FileConfiguration config);
    public abstract T readFrom(String path, FileConfiguration config);

    private final T defaultValue;

}
