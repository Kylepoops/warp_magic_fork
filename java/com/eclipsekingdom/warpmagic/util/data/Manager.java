package com.eclipsekingdom.warpmagic.util.data;

import java.util.*;

public abstract class Manager<KEY, DATA> {

    /* --- constructors --- */

    protected Manager(DataType<DATA> dataType, String fileName, String dirPath){
        this.dataType = dataType;
        this.defaultData = dataType.getDefaultValue();
        this.database = new Database(dataType,fileName,dirPath);
    }


    /* --- interface --- */

    public abstract void load();

    public void save(){

        for(KEY key: unsavedData){
            DATA data = keyToData.get(key);
            database.store(key, data);
        }

        database.save();
        unsavedData.clear();
    }

    public void cache(KEY key){
        cashOne(key);
        for(KEY requiredDataKey: getRequirements(key)){
            cashOne(requiredDataKey);
        }
    }

    public void forget(KEY leavingKey){
        forgetOne(leavingKey, leavingKey);
        for(KEY requiredDataKey: getRequirements(leavingKey)){
            forgetOne(requiredDataKey, leavingKey);
        }
    }

    public void trackUnsavedData(KEY key){
        if(!unsavedData.contains(key)){
            unsavedData.add(key);
        }
    }


    /* --- implementation --- */

    protected final DataType dataType;
    protected final Database database;

    protected final Map<KEY,DATA> keyToData = new HashMap<>();
    protected final DATA defaultData;

    protected abstract boolean stillNeeded(KEY key, KEY leavingKey);
    protected abstract List<KEY> getRequirements(KEY key);


    /* --- secret --- */

    private final List<KEY> unsavedData = new ArrayList<>();

    private void resolveUnsavedData(KEY key){
        while (unsavedData.contains(key)) {
            unsavedData.remove(key);
        }
    }

    private void cashOne(KEY key){
        if(!inCash(key)){
            DATA data = (DATA) database.fetch(key);
            if(data != null){
                if(!dataType.isTrivial(data)){
                    keyToData.put(key, data);
                }
            }
        }
    }

    private void forgetOne(KEY key, KEY leavingKey){
        if(!stillNeeded(key, leavingKey)){
            if(unsavedData.contains(key)){
                database.store(key, keyToData.get(key)); //store even if trivial (to clear out old values)
                database.save();
                resolveUnsavedData(key);
            }
            keyToData.remove(key);
        }
    }

    private boolean inCash(KEY key){
        return keyToData.containsKey(key);
    }

}





