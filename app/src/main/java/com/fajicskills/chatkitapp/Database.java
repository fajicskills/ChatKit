package com.fajicskills.chatkitapp;


import com.fajicskills.chatkitapp.model.Conversation;
import com.fajicskills.chatkitapp.model.Message;
import com.fajicskills.chatkitapp.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angebagui on 15/03/2017.
 */

public class Database {

    public static List<Conversation> getConversations(){

        List<Conversation> conversations = new ArrayList<>();
        Conversation conversation = new Conversation();
        conversation.setMessages(getGeneratedMessages());
        conversations.add(conversation);
        return conversations;
    }

    private static List<Message> getGeneratedMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1, foo(),"Hello !",System.currentTimeMillis(),System.currentTimeMillis(),false));
        messages.add(new Message(1, poo(),"Hi !",System.currentTimeMillis(),System.currentTimeMillis(),true));
        messages.add(new Message(1, foo(),"How are you? ",System.currentTimeMillis(),System.currentTimeMillis(),false));
        messages.add(new Message(1, poo(),"Am Ok and You ?",System.currentTimeMillis(),System.currentTimeMillis(),true));
        messages.add(new Message(1, foo(),"Am there. ",System.currentTimeMillis(),System.currentTimeMillis(),false));
        messages.add(new Message(1, poo(),"Where are you?",System.currentTimeMillis(),System.currentTimeMillis(),true));
        messages.add(new Message(1, foo(),"Am in Abidjan. And you?",System.currentTimeMillis(),System.currentTimeMillis(),false));
        messages.add(new Message(1, poo(),"Am in Yakro",System.currentTimeMillis(),System.currentTimeMillis(),true));

        return messages;
    }

    private static User foo(){
        return new User("Malaika Lois", "");
    }

    private static User poo(){
        return new User("Ake NGBO", "");
    }
}
