package com.fajicskills.chatkit;

import android.text.SpannableString;

/**
 * Message abstraction with that class
 * @author Ange Bagui
 */

public interface MessageRecord<R,S,T> {

    /**
     * Id of the Message
     * @return
     */
    R getId();

    /**
     * This Message belong to a conversation. We get the conversation ID
     * @return
     */
    S getConversationId();

    /**
     * The client ID represent an unique identifier generated in the phone that send the message
     * @return
     */
    String getClientId();

    /**
     * The Sender identifier
     * @return
     */
    T getSenderIdentifier();

    /**
     * The Sender Name
     * @return
     */
    String getSenderName();

    /**
     * The sender's photo
     * @return
     */
    String getSenderPhoto();

    /**
     * The body of the message
     * @return
     */
    Body getBody();

    /**
     * The body to display
     * @return
     */
    SpannableString getDisplayBody();

    /**
     * the message has been sent at this date with return
     * @return
     */
    long getDateSent();

    /**
     * @return the date the message has been received
     */
    long getDateReceived();

    /**
     *
     * @return if this message is a media (image, sound, movies) or not
     */
    boolean isMedia();

    /**
     *
     * @return  if the sending failed or not
     */
    boolean isFailed();

    /**
     *
     * @return if the sending is pending or not
     */
    boolean isPending();

    /**
     * @return if this outgoing message or not
     */
    boolean isOutgoing();

    /**
     * @return if this message is delivered or not
     */
    boolean isDelivered();

    /**
     * @return if this message is received
     */
    boolean isReceived();


    /**
     * Message Body Class
     */
    public static class Body {
        private final String body;
        private final boolean plaintext;

        public Body(String body, boolean plaintext) {
            this.body      = body;
            this.plaintext = plaintext;
        }

        public boolean isPlaintext() {
            return plaintext;
        }

        public String getBody() {
            return body == null ? "" : body;
        }
    }



}
