package com.eclipsekingdom.warpmagic.requests;

import java.util.UUID;

public class Request {

    public Request(UUID requesterID, RequestType type){
        this.requesterID = requesterID;
        this.type = type;
    }

    public UUID getTransactionID(){
        return transactionID;
    }

    public UUID getRequesterID() {
        return requesterID;
    }

    public RequestType getType() {
        return type;
    }

    private final UUID transactionID = UUID.randomUUID();
    private final UUID requesterID;
    private final RequestType type;

}
