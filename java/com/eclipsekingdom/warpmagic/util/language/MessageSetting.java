package com.eclipsekingdom.warpmagic.util.language;

public class MessageSetting {

    private String messageSetting;
    private String messageDefault;
    private String message;

    public MessageSetting(String messageSetting, String messageDefault){
        this.messageSetting = messageSetting;
        this.messageDefault = messageDefault;
        this.message = messageDefault;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageSetting(){
        return messageSetting;
    }

    public String getMessageDefault(){
        return messageDefault;
    }

    public String getMessage(){
        return message;
    }
}
