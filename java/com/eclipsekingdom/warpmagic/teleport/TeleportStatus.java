package com.eclipsekingdom.warpmagic.teleport;

public enum TeleportStatus {
    VALID("success"),
    PLAYER_OFFLINE("player offline"),
    LOCATION_NOT_FOUND("The requested location could not be located");


    TeleportStatus(String message){
        this.message = message;
    }


    public String getMessage(){
        return message;
    }

    private final String message;

}
