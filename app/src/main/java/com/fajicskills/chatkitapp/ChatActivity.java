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


    }

    @Override
    public Message sendMessage(String message) {

        return new Message(me,message,System.currentTimeMillis(),false);
    }


    @Override
    public Observable<Message> getSendingObservable() {
        return Observable.create(new Observable.OnSubscribe<Message>(){
            @Override
            public void call(Subscriber<? super Message> subscriber) {

            }
        });
    }

    @Override
    public void onEventMainThread(MessageJobAddedEvent event) {
         super.onEventMainThread(event);
        Log.w(TAG, "Test  MessageJobAddedEvent");
    }

    @Override
    public void onEventMainThread(UpdatedMessageEvent event) {
        super.onEventMainThread(event);
        Log.w(TAG, "Test  UpdatedMessageEvent");
    }

    @Override
    public void onEventMainThread(DeleteMessageEvent event) {
        super.onEventMainThread(event);
        Log.w(TAG, "Test  DeleteMessageEvent");
    }

    @Override
    public void updateUI(DataSetChange dataSetChange) {
        dataSetChange.onSuccess(Database.getConversations().get(0).getMessages());
        dataSetChange.onFailure(null);
    }

    @Override
    public void refreshUI(DataSetChange dataSetChange) {

    }

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

    @Override
    public void onAdapterSet(RecyclerView recyclerView, ConversationAdapter conversationAdapter) {
        this.recyclerView = recyclerView;
        this.mConversationAdapter = conversationAdapter;
    }
}
