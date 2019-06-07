package com.eclipsekingdom.warpmagic.data;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class DataType<T> {

    protected DataType(T defaultValue){
        this.defaultValue = defaultValue;
    }

    public T getDefaultValue(){
        return defaultValue;
    }

    public boolean isTrivial(T t){
        return t.equals(defaultValue);
    }

    public abstract void writeTo(String path, T data, FileConfiguration config);
    public abstract T readFrom(String path, FileConfiguration config);

    private final T defaultValue;

}
