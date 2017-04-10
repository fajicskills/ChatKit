package com.fajicskills.chatkitapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fajicskills.chatkit.ConversationActivity;
import com.fajicskills.chatkit.ConversationAdapter;
import com.fajicskills.chatkit.DataSetChange;
import com.fajicskills.chatkit.MessageRecord;
import com.fajicskills.chatkit.events.DeleteMessageEvent;
import com.fajicskills.chatkit.events.MessageJobAddedEvent;
import com.fajicskills.chatkit.events.UpdatedMessageEvent;
import com.fajicskills.chatkitapp.model.Message;
import com.fajicskills.chatkitapp.model.User;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by angebagui on 15/03/2017.
 */

public class ChatActivity extends ConversationActivity {

    private RecyclerView recyclerView;

    private ConversationAdapter mConversationAdapter;

    private Long chatId;

    private User me;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //chatId = Add chatId
         // me =  Add connected User
    }

    /**
     * This method allow you to create Message Record by message wrote by connected User
     * @param message
     * @return
     */
    @Override
    public Message sendMessage(String message) {

        return new Message(me,chatId,message,System.currentTimeMillis(),false);
    }

    /**
     * The method allow you the return the Observable to send message to the Backend Server
     * @return
     */

    @Override
    public Observable<Message> getSendingObservable() {
        return Observable.create(new Observable.OnSubscribe<Message>(){
            @Override
            public void call(Subscriber<? super Message> subscriber) {

            }
        });
    }

    /**
     * When message is added to the Message Job
     * @param event
     */
    @Override
    public void onEventMainThread(MessageJobAddedEvent event) {
         super.onEventMainThread(event);
        Log.w(TAG, "Test  MessageJobAddedEvent");
    }

    /**
     * When message is sent to the server with success
     * @param event
     */
    @Override
    public void onEventMainThread(UpdatedMessageEvent event) {
        super.onEventMainThread(event);
        Log.w(TAG, "Test  UpdatedMessageEvent");
    }


    /**
     * When we got error on the Job running and message have been deleted
     * @param event
     */
    @Override
    public void onEventMainThread(DeleteMessageEvent event) {
        super.onEventMainThread(event);
        Log.w(TAG, "Test  DeleteMessageEvent");
    }


    /**
     * Load messages from your database
     * @param dataSetChange
     */
    @Override
    public void loadMessageRecordsFromDBUI(DataSetChange dataSetChange) {
        // TODO implement to Load messages from your database
        dataSetChange.onSuccess(Database.getConversations().get(0).getMessages());
        dataSetChange.onFailure(null);
    }

    /**
     * Load messages from your backend server
     * @param dataSetChange
     */
    @Override
    public void loadMessageRecordsFromInternetUI(DataSetChange dataSetChange) {
        // TODO implement to Load messages from your backend server
    }

    /**
     *
     * @return an Item Click Listener
     */
    @Override
    public ConversationAdapter.ItemClickListener getItemClickListener() {
        return new ConversationAdapter.ItemClickListener() {
            @Override
            public void onItemClick(MessageRecord item) {

            }

            @Override
            public void onItemLongClick(MessageRecord item) {

            }
        };
    }

    /**
     * This method allow to get access of the RecyclerView and Adapter that fill your messages
     * @param recyclerView
     * @param conversationAdapter
     */
    @Override
    public void onAdapterSet(RecyclerView recyclerView, ConversationAdapter conversationAdapter) {
        this.recyclerView = recyclerView;
        this.mConversationAdapter = conversationAdapter;
    }
}
