package com.fajicskills.chatkit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by angebagui on 15/03/2017.
 */

public class ConversationFragment extends Fragment {

    private static ConversationFragment instance;

    private OnConversationFragmentListener listener;



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

        listener.updateUI(new DataSetChange<MessageRecord>() {
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
