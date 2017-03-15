package com.fajicskills.chatkit;

import android.text.SpannableString;

/**
 * Created by angebagui on 15/03/2017.
 */

public interface MessageRecord {

    long getId();
    String getSenderName();
    String getSenderPhoto();
    Body getBody();
    SpannableString getDisplayBody();
    long getDateSent();
    long getDateReceived();
    boolean isMms();
    boolean isFailed();
    boolean isPending();
    boolean isOutgoing();


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
