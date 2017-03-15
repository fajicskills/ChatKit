package com.fajicskills.chatkitapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.fajicskills.chatkit.ConversationActivity;
import com.fajicskills.chatkit.ConversationAdapter;
import com.fajicskills.chatkit.DataSetChange;
import com.fajicskills.chatkit.MessageRecord;


/**
 * Created by angebagui on 15/03/2017.
 */

public class ChatActivity extends ConversationActivity {

    private RecyclerView recyclerView;
    private ConversationAdapter mConversationAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void updateUI(DataSetChange dataSetChange) {
        dataSetChange.onSuccess(Database.getConversations().get(0).getMessages());
        dataSetChange.onFailure(null);
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
