package com.eclipsekingdom.warpmagic.warp.validation;

import com.eclipsekingdom.warpmagic.util.language.Message;

public enum LocationStatus {

    VALID("success"),
    INVALID_WORLD(Message.INVALID_WORLD.get());

    public final String message;

    LocationStatus(String message){
        this.message = message;
    }

}