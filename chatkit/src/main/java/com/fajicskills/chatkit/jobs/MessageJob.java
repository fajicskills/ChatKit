package com.fajicskills.chatkit.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.fajicskills.chatkit.MessageRecord;
import com.fajicskills.chatkit.events.DeleteMessageEvent;
import com.fajicskills.chatkit.events.MessageErrorEvent;
import com.fajicskills.chatkit.events.MessageJobAddedEvent;
import com.fajicskills.chatkit.events.UpdatedMessageEvent;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.functions.Func1;
import rx.observables.BlockingObservable;

/**
 * Created by angebagui on 15/03/2017.
 */

public class MessageJob extends Job {


    private MessageRecord mMessageRecord;

    private Observable<? extends MessageRecord> messageRecordObservable;

    public MessageJob(MessageRecord messageRecord, Observable<? extends  MessageRecord> messageRecordObservable ) {
        super(new Params(Priority.HIGH).requireNetwork().persist().groupBy("message"));
        this.mMessageRecord = messageRecord;
        this.messageRecordObservable = messageRecordObservable;
    }

    @Override
    public void onAdded() {
        /**
         *  say to the system that the job has been saved
         */
        EventBus.getDefault().post(new MessageJobAddedEvent(mMessageRecord));

    }

    @Override
    public void onRun() throws Throwable {

        BlockingObservable.from(messageRecordObservable)
                .single(new Func1<MessageRecord, Boolean>() {
                    @Override
                    public Boolean call(MessageRecord messageRecord) {
                        if(messageRecord != null){
                            mMessageRecord = messageRecord;
                            EventBus.getDefault().post(new UpdatedMessageEvent(messageRecord));
                            return true;
                        }else {
                            return false;
                        }

                    }
                });

    }


    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        EventBus.getDefault().post(new MessageErrorEvent(throwable, mMessageRecord.getClientId()));
        return RetryConstraint.RETRY;
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        EventBus.getDefault().post(new DeleteMessageEvent(mMessageRecord));
    }

   /* interface HttpCallback{
        void onSuccess(MessageRecord messageRecord);
        void onError(Throwable throwable);
    }*/
}
