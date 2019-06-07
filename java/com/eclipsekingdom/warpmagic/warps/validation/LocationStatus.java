package com.eclipsekingdom.warpmagic.warps.validation;

public enum LocationStatus {

    VALID("success"),
    INVALID_WORLD("Warp points can not be set in this world");

    LocationStatus(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private final String message;

}
