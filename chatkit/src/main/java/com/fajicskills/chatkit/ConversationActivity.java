package com.fajicskills.chatkit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.birbit.android.jobqueue.JobManager;
import com.fajicskills.chatkit.events.DeleteMessageEvent;
import com.fajicskills.chatkit.events.MessageJobAddedEvent;
import com.fajicskills.chatkit.events.UpdatedMessageEvent;
import com.fajicskills.chatkit.jobs.MessageJob;
import com.fajicskills.chatkit.util.JobUtils;
import com.fajicskills.chatkit.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.Observable;

/**
 * Created by angebagui on 15/03/2017.
 */

public abstract class ConversationActivity extends AppCompatActivity implements OnConversationFragmentListener {


    public static final String TAG = ConversationActivity.class.getSimpleName();

    private EditText embeddedTextEditor;

    private ImageButton sendButton;

    private JobManager jobManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(savedInstanceState == null){
            Fragment fragment = ConversationFragment.getInstance(this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_content, fragment)
                    .commit();
        }

        jobManager = JobUtils.getJobManager(getApplication());

        embeddedTextEditor = ViewUtil.findById(this, R.id.embedded_text_editor);
        sendButton = ViewUtil.findById(this,R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(embeddedTextEditor.getText())){
                    hideKeyBoard();
                    performMessageSending(embeddedTextEditor.getText().toString());
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //We register this class to the EventBus Manager
        EventBus.getDefault().register(this);
    }

    /**
     * Perform message Sending
     * We create a Message Job and add it to the message JobManager
     * @param message
     */
    private void performMessageSending(String message) {
        final MessageRecord messageRecord = sendMessage(message);

        jobManager.addJobInBackground(new MessageJob(messageRecord,getSendingObservable()));
    }


    // TODO Hide Keyboard when we click on Send button
    private void hideKeyBoard() {

    }

    private ConversationFragment getConversationFragment(){
       return (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_content);
    }

    /**
     * This method allow you to create Message Record by message wrote by connected User
     * @param message
     * @return
     */
    public abstract MessageRecord sendMessage(String message);

    /**
     * The method allow you the return the Observable to send message to the Backend Server
     * @return
     */
    public abstract Observable<? extends MessageRecord> getSendingObservable();



    public EditText getEmbeddedTextEditor() {
        return embeddedTextEditor;
    }

    public ImageButton getSendButton() {
        return sendButton;
    }

    /**
     * When message is added to the Message Job
     * @param event
     */
    @Subscribe
    public void onEventMainThread(final MessageJobAddedEvent event){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "MessageJob added ! Client ID:"+ event.getMessageRecord().getClientId() );

                getConversationFragment().addMessage(event.getMessageRecord());

            }
        });
    }


    /**
     * When message is sent to the server with success
     * @param event
     */
    @Subscribe
    public  void onEventMainThread(final UpdatedMessageEvent event){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "Message sent to Backend ! "+event );

                getConversationFragment().refreshMessage(event.getMessageRecord());

            }
        });
    }

    /**
     * When we got error on the Job running and message have been deleted
     * @param event
     */
    @Subscribe
    public void onEventMainThread(final DeleteMessageEvent event){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "Message deleted ! "+event);
                getConversationFragment().removeMessage(event.getMessageRecord());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //We unregister this class to the EventBus Manager
        EventBus.getDefault().unregister(this);
    }


}
