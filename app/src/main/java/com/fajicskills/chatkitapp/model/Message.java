package com.fajicskills.chatkitapp.model;

import android.text.SpannableString;

import com.fajicskills.chatkit.MessageRecord;

import java.io.Serializable;

/**
 * Created by angebagui on 15/03/2017.
 */

public class Message implements MessageRecord , Serializable{


    private long id;

    private User sender;

    private String message;

    private long dateSent;

    private long dateReceived;

    private boolean isOutgoing;

    public Message(long id, User sender, String message, long dateSent, long dateReceived, boolean isOutgoing) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.dateSent = dateSent;
        this.dateReceived = dateReceived;
        this.isOutgoing = isOutgoing;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateSent(long dateSent) {
        this.dateSent = dateSent;
    }

    public void setDateReceived(long dateReceived) {
        this.dateReceived = dateReceived;
    }

    public void setOutgoing(boolean outgoing) {
        isOutgoing = outgoing;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getSenderName() {
        return sender.getName();
    }

    @Override
    public String getSenderPhoto() {
        return sender.getAvatar();
    }

    @Override
    public Body getBody() {
        return new Body(message, true);
    }

    @Override
    public SpannableString getDisplayBody() {
        return new SpannableString(message);
    }

    @Override
    public long getDateSent() {
        return dateSent;
    }

    @Override
    public long getDateReceived() {
        return dateReceived;
    }

    @Override
    public boolean isMms() {
        return false;
    }

    @Override
    public boolean isFailed() {
        return false;
    }

    @Override
    public boolean isPending() {
        return false;
    }

    @Override
    public boolean isOutgoing() {
        return isOutgoing;
    }
}
