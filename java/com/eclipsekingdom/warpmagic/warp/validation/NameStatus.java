package com.eclipsekingdom.warpmagic.warp.validation;

import com.eclipsekingdom.warpmagic.util.language.Message;

public enum NameStatus {

    VALID("success"),
    SPECIAL_CHARACTERS(Message.INVALID_CHARACTERS.get()),
    TOO_LONG(Message.INVALID_TOO_LONG.get()),
    RESERVED_WORD(Message.INVALID_RESERVED_WORD.get());

    public final String message;

    NameStatus(String message){
        this.message = message;
    }

}