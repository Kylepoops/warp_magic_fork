package com.eclipsekingdom.warpmagic.warp.validation;


import com.eclipsekingdom.warpmagic.sys.lang.Message;

public enum NameStatus {

    VALID(""),
    SPECIAL_CHARACTERS(Message.INVALID_CHARACTERS.toString()),
    TOO_LONG(Message.INVALID_TOO_LONG.toString()),
    RESERVED_WORD(Message.INVALID_RESERVED_WORD.toString());

    public final String message;

    NameStatus(String message) {
        this.message = message;
    }

}