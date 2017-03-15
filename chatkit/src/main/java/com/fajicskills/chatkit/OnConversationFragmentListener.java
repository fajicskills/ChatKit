package com.fajicskills.chatkit;

import android.support.v7.widget.RecyclerView;

import java.io.Serializable;

/**
 * Created by angebagui on 15/03/2017.
 */

interface OnConversationFragmentListener extends Serializable {

    void updateUI(DataSetChange dataSetChange);

    ConversationAdapter.ItemClickListener getItemClickListener();

    void onAdapterSet(RecyclerView recyclerView, ConversationAdapter conversationAdapter);

}
