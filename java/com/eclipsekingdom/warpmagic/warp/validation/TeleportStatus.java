package com.eclipsekingdom.warpmagic.warp.validation;

public enum TeleportStatus {

    VALID("success"),
    LOCATION_NOT_FOUND("The requested location could not be located")

    ;

    public final String message;

    TeleportStatus(String message) {
        this.message = message;
    }


}