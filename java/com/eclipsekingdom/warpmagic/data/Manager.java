package com.eclipsekingdom.warpmagic.data;

import java.util.*;

public abstract class Manager<KEY, DATA> {

    /* --- constructors --- */

    protected Manager(DataType dataType, String fileName, String dirPath){
        this.dataType =  dataType;
        this.defaultData = (DATA) dataType.getDefaultValue();
        this.database = new Database(dataType,fileName,dirPath);
    }


    /* --- interface --- */

    public abstract void load();

    public void save(){

        for(Map.Entry<KEY,String> entry: newDataToName.entrySet()){
            database.addName(entry.getKey(), entry.getValue());
        }

        for(KEY key: unsavedData){
            DATA data = keyToData.get(key);
            database.store(key, data);
        }

        database.save();

        newDataToName.clear();
        unsavedData.clear();
    }

    public void cash(KEY key){
        cashOne(key);
        List<KEY> requiredDataKeys = getRequirements(key);
        for(KEY requiredDataKey: requiredDataKeys){
            cashOne(key);
        }
    }

    public void forget(KEY key){
        if(inCash(key)){
            if(!stillNeeded(key)){
                if(unsavedData.contains(key)){
                    DATA data = keyToData.get(key);
                    database.store(key, keyToData.get(data)); //store even if trivial (to clear out old values)
                    database.save();
                    resolveUnsavedData(key);
                }
                keyToData.remove(key);
            }
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

    protected abstract boolean stillNeeded(KEY key);
    protected abstract List<KEY> getRequirements(KEY key);

    protected void registerNewDataName(KEY key, String name){
        if(!newDataToName.containsKey(key)){
            newDataToName.put(key, name);
        }
    }


    /* --- secret --- */

    private final HashMap<KEY, String> newDataToName = new HashMap<>();

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

    private boolean inCash(KEY key){
        return keyToData.containsKey(key);
    }

}





