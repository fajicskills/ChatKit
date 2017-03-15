package com.fajicskills.chatkitapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by angebagui on 15/03/2017.
 */

public class Conversation implements Serializable {

    private User recipient;
    private List<Message> messages;

    public Conversation() {

    }

    public Conversation(User recipient, List<Message> messages) {
        this.recipient = recipient;
        this.messages = messages;
    }

    public User getRecipient() {
        return recipient;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
