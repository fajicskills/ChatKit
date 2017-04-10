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
        messages.add(new Message(foo(),"Hello !",System.currentTimeMillis(),false));
        messages.add(new Message( poo(),"Hi !",System.currentTimeMillis(),true));
        messages.add(new Message( foo(),"How are you? ",System.currentTimeMillis(),false));
        messages.add(new Message( poo(),"Am Ok and You ?",System.currentTimeMillis(),true));
        messages.add(new Message( foo(),"Am there. ",System.currentTimeMillis(),false));
        messages.add(new Message( poo(),"Where are you?",System.currentTimeMillis(),true));
        messages.add(new Message( foo(),"Am in Abidjan. And you?",System.currentTimeMillis(),false));
        messages.add(new Message( poo(),"Am in Yakro",System.currentTimeMillis(),true));

        return messages;
    }

    private static User foo(){
        return new User("Malaika Lois", "");
    }

    private static User poo(){
        return new User("Ake NGBO", "");
    }
}
