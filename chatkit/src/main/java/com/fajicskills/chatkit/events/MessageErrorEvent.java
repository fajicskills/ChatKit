package com.fajicskills.chatkit.events;

/**
 * Created by angebagui on 09/04/2017.
 */

public class MessageErrorEvent implements MessageEvent{

    private Throwable throwable;

    private String clientId;

    public MessageErrorEvent(Throwable throwable,String clientId) {
        this.throwable = throwable;
        this.clientId = clientId;
    }
}
