package com.fajicskills.chatkit.events;

import com.fajicskills.chatkit.MessageRecord;

/**
 * Created by angebagui on 09/04/2017.
 */

public class UpdatedMessageEvent implements MessageEvent {

    private MessageRecord messageRecord;

    public UpdatedMessageEvent(MessageRecord messageRecord) {
        this.messageRecord = messageRecord;
    }

    public MessageRecord getMessageRecord() {
        return messageRecord;
    }

    public void setMessageRecord(MessageRecord messageRecord) {
        this.messageRecord = messageRecord;
    }
}
