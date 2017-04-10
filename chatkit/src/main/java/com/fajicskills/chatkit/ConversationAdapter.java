package com.fajicskills.chatkit;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fajicskills.chatkit.util.ViewUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import ci.adjemin.android.app.util.MaterialDesignDateFormats;

/**
 * Created by angebagui on 15/03/2017.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder>{

    private static final String TAG = ConversationAdapter.class.getSimpleName();

    private static final int MESSAGE_TYPE_OUTGOING           = 0;
    private static final int MESSAGE_TYPE_INCOMING           = 1;

    private final @NonNull Context context;
    private final @NonNull LayoutInflater inflater;
    private final @Nullable ItemClickListener clickListener;

    private  @NonNull  List<MessageRecord> messages;

    public ConversationAdapter(@NonNull Context context, @Nullable ItemClickListener clickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.messages = new ArrayList<>();
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        long start = System.currentTimeMillis();
        final View itemView = ViewUtil.inflate(inflater, parent, getLayoutForViewType(viewType));
        Log.w(TAG, "Inflate time: " + (System.currentTimeMillis() - start));
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MessageRecord messageRecord = messages.get(position);
       // Picasso.with(holder.itemView.getContext()).load(Uri.parse(messageRecord.getSenderPhoto())).error(R.drawable.ic_person_black_24dp).into(holder.contactPhoto);
        holder.conversationItemBody.setText(messageRecord.getBody().isPlaintext()?messageRecord.getBody().getBody():messageRecord.getDisplayBody());
        if (messageRecord.isOutgoing()){
            holder.conversationItemDate.setText(MaterialDesignDateFormats.display(messageRecord.getDateSent(), Locale.getDefault()));

        }else {
            holder.conversationItemDate.setText(MaterialDesignDateFormats.display(messageRecord.getDateReceived(), Locale.getDefault()));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemClick(messageRecord);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemLongClick(messageRecord);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(getMessages().get(position));
    }

    private @LayoutRes int getLayoutForViewType(int viewType) {
        switch (viewType) {
            case MESSAGE_TYPE_OUTGOING:        return R.layout.conversation_item_sent;
            case MESSAGE_TYPE_INCOMING:        return R.layout.conversation_item_received;
            default: throw new IllegalArgumentException("unsupported item view type given to ConversationAdapter");
        }
    }


    public int getItemViewType(@NonNull MessageRecord messageRecord) {

        if (messageRecord.isOutgoing()) {
            return MESSAGE_TYPE_OUTGOING;
        } else {
            return MESSAGE_TYPE_INCOMING;
        }
    }

    @NonNull
    public List<MessageRecord> getMessages() {
        return messages;
    }


    public void addMessage(@NonNull MessageRecord messageRecord){
        this.messages.add(messageRecord);
        // TODO item inserted position
        int itemInsertedPosition = this.getMessages().size()-1;
        notifyItemInserted(itemInsertedPosition);
    }
    public void setMessages(@NonNull List<MessageRecord> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void refreshMessage(MessageRecord messageRecord) {
         int index =  -1;
            for (int i = 0; i<messages.size(); i++){
                if (messages.get(i).getClientId().equals(messageRecord.getClientId())){
                    index = i;
                }
            }

            if (index <0)
                return;
        this.messages.add(index,messageRecord);
        notifyItemChanged(index);
    }

    public void removeMessage(MessageRecord messageRecord){

        int index =  -1;
        for (int i = 0; i<messages.size(); i++){
            if (messages.get(i).getClientId().equals(messageRecord.getClientId())){
                index = i;
            }
        }

        if (index <0)
            return;

        messages.remove(index);
        notifyItemRemoved(index);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView conversationItemBody;
        private TextView conversationItemDate;
        private ImageView contactPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            contactPhoto = ViewUtil.findById(itemView,R.id.contact_photo);
            conversationItemBody = ViewUtil.findById(itemView, R.id.conversation_item_body);
            conversationItemDate = ViewUtil.findById(itemView, R.id.conversation_item_date);
        }
    }

    public interface ItemClickListener {
        void onItemClick(MessageRecord item);
        void onItemLongClick(MessageRecord item);
    }
}
