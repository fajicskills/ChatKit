package com.fajicskills.chatkit;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fajicskills.chatkit.util.Utils;

import java.util.List;

/**
 * Created by angebagui on 15/03/2017.
 */

public class ConversationFragment extends Fragment {

    private static ConversationFragment instance;

    private OnConversationFragmentListener listener;

    public static final String EXTRA_INCOMING_MESSAGE = "ConversationFragment.EXTRA_INCOMING_MESSAGE";

    public static final String INCOMING_MESSAGE_INTENT = "com.fajicskills.chatkit.INCOMING_MESSAGE_INTENT";

    private static final String TAG = ConversationFragment.class.getSimpleName();

    private boolean isReceiverRegistered = false;

    private BroadcastReceiver mMessageBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // if we receive this, we're visible, so cancel
            // the notification
            Log.w(TAG, "canceling notification");
            setResultCode(Activity.RESULT_CANCELED);

            Long messageId = intent.getLongExtra(EXTRA_INCOMING_MESSAGE, -1);
            if (messageId == -1){

            }else {

                listener.loadMessageRecordsFromInternetUI(new DataSetChange<MessageRecord>() {
                    @Override
                    public void onSuccess(@NonNull List<MessageRecord> messageRecords) {
                        conversationAdapter.setMessages(messageRecords);
                    }

                    @Override
                    public void onFailure(@Nullable String message) {

                    }
                });
            }
        }
    };


    public static ConversationFragment getInstance(OnConversationFragmentListener listener){
        if (instance == null){
            instance = new ConversationFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("CONVERSATION_LISTENER", listener);
            instance.setArguments(bundle);
        }
        return instance;
    }

    private ConversationAdapter conversationAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.conversation_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       listener = (OnConversationFragmentListener) getArguments().getSerializable("CONVERSATION_LISTENER");
        conversationAdapter = new ConversationAdapter(getActivity(),listener.getItemClickListener());
        recyclerView = (RecyclerView)view.findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(conversationAdapter);

        listener.onAdapterSet(recyclerView,conversationAdapter);

        listener.loadMessageRecordsFromDBUI(new DataSetChange<MessageRecord>() {
            @Override
            public void onSuccess(@NonNull List<MessageRecord> messageRecords) {
                conversationAdapter.setMessages(messageRecords);
            }

            @Override
            public void onFailure(@Nullable String message) {

            }
        });



    }

    public ConversationAdapter getConversationAdapter() {
        return conversationAdapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void addMessage(MessageRecord messageRecord) {
        getConversationAdapter().addMessage(messageRecord);
        scrollToBottom();
    }

    private void scrollToBottom() {
       recyclerView.smoothScrollToPosition(getConversationAdapter().getItemCount()-1);
    }

    public void refreshMessage(MessageRecord messageRecord) {
        getConversationAdapter().refreshMessage(messageRecord);
    }

    public void removeMessage(MessageRecord messageRecord){
        getConversationAdapter().removeMessage(messageRecord);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    private void registerReceiver() {

        if(!isReceiverRegistered) {
            getActivity().registerReceiver(mMessageBroadcastReceiver,
                    new IntentFilter(INCOMING_MESSAGE_INTENT), Utils.PERM_PRIVATE,null);
            isReceiverRegistered = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mMessageBroadcastReceiver);
        isReceiverRegistered = false;
    }
}
