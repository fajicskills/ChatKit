package com.fajicskills.chatkit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.fajicskills.chatkit.util.ViewUtil;

/**
 * Created by angebagui on 15/03/2017.
 */

public abstract class ConversationActivity extends AppCompatActivity implements OnConversationFragmentListener {


    private EditText embeddedTextEditor;

    private ImageButton sendButton;


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

        embeddedTextEditor = ViewUtil.findById(this, R.id.embedded_text_editor);
        sendButton = ViewUtil.findById(this,R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(embeddedTextEditor.getText())){
                    hideKeyBoard();
                }
            }
        });


    }

    protected abstract void hideKeyBoard();

    public EditText getEmbeddedTextEditor() {
        return embeddedTextEditor;
    }

    public ImageButton getSendButton() {
        return sendButton;
    }
}
