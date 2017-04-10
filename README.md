ChatKit
=======

[ ![Download](https://api.bintray.com/packages/angebagui/maven/ChatKit/images/download.svg) ]
(https://bintray.com/angebagui/maven/ChatKit/_latestVersion)


ChatKit is an android library to add chat feature quickly in your Android app

#Screenshot
![Screenshot](https://github.com/fajicskills/ChatKit/blob/master/screenshot/Screenshot_2016-08-14-19-59-48.png)


Usage
-----

**1.** Add the following to your **build.gradle**.
```groovy
dependencies {
  compile 'com.fajicskills.chatkit:chatkit:1.0.0-beta-1'
}
```

**2.** Create An activity that extends com.fajicskills.chatkit.ConversationActivity
```java

public class ChatActivity extends ConversationActivity {

    private RecyclerView recyclerView;

    private ConversationAdapter mConversationAdapter;

    private Long chatId;

    private User me;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            
            chatId = //Add chatId
            me = // Add connected User
            

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
    public void loadMessageRecordsFromDBUI(DataSetChange dataSetChange) {
        dataSetChange.onSuccess(Database.getConversations().get(0).getMessages());
        dataSetChange.onFailure(null);
    }

    @Override
    public void loadMessageRecordsFromInternetUI(DataSetChange dataSetChange) {

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
```



Licences
--------
    Copyright 2017 FAJIC SKILLS.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.