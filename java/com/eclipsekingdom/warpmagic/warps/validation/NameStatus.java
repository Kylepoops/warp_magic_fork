package com.eclipsekingdom.warpmagic.warps.validation;

public enum NameStatus {

    VALID("success"),
    SPECIAL_CHARACTERS("Plot names must consist of only a-z, A-Z, 0-9, _, and -"),
    TOO_LONG("Plot names must be 20 characters or less"),
    NAME_TAKEN("You already have a plot with that name"),
    RESERVED_WORD("The name you selected is reserved by WarpMagic");

    NameStatus(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private final String message;

}