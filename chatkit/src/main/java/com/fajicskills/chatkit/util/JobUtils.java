package com.fajicskills.chatkit.util;

import android.app.Application;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;


public class JobUtils {

    final static String TAG = "ChatKit";

    private static JobManager mJobManager;

    public  static JobManager getJobManager(Application application){

        if (mJobManager == null){
             Configuration.Builder builder = new Configuration.Builder(application).customLogger(new CustomLogger() {
                @Override
                public boolean isDebugEnabled() {
                    return true;
                }

                @Override
                public void d(String text, Object... args) {
                    Log.d(TAG, String.format(text, args));
                }

                @Override
                public void e(Throwable t, String text, Object... args) {
                    Log.e(TAG, String.format(text, args), t);
                }

                @Override
                public void e(String text, Object... args) {
                    Log.e(TAG, String.format(text, args));
                }

                @Override
                public void v(String text, Object... args) {

                }
            })
                     .minConsumerCount(1)//always keep at least one consumer alive
                    .maxConsumerCount(3)//up to 3 consumers at a time
                    .loadFactor(3)//3 jobs per consumer
                    .consumerKeepAlive(120);//wait 2 minute

            mJobManager = new JobManager(builder.build());
        }

        return mJobManager;

    }
}
