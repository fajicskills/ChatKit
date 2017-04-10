package com.fajicskills.chatkitapp.model;

import android.text.SpannableString;

import com.fajicskills.chatkit.MessageRecord;

import java.io.Serializable;

/**
 * Created by angebagui on 15/03/2017.
 */

public class Message implements MessageRecord<Long,Long,Long> , Serializable{

    private Long id;

    private String clientId;

    private Long chatId;

    private User sender;

    private String message;

    private long dateSent;

    private long dateReceived;

    private boolean isOutgoing;

    public Message( User sender, Long chatId, String message, long dateSent, boolean isOutgoing) {
        this.sender = sender;
        this.chatId = chatId;
        this.message = message;
        this.dateSent = dateSent;
        this.isOutgoing = isOutgoing;
    }



    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getConversationId() {
        return chatId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Long getSenderIdentifier() {
        return  this.sender.getId();
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
    public boolean isMedia() {
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

    @Override
    public boolean isDelivered() {
        return false;
    }

    @Override
    public boolean isReceived() {
        return false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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
}
