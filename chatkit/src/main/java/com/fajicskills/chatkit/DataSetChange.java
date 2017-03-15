package com.fajicskills.chatkit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public interface DataSetChange <T extends MessageRecord>  extends Serializable {
        void onSuccess(@NonNull List<T> messageRecords);
        void onFailure(@Nullable String message);
    }