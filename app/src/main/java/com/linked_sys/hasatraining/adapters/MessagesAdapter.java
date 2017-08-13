package com.linked_sys.hasatraining.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.models.Message;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Message> messages;
    private MessagesAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageDate,messageRef;
        LinearLayout messageRow,idContainer, dateContainer;

        MyViewHolder(View view) {
            super(view);
            messageDate = (TextView) view.findViewById(R.id.txtMessageDate);
            messageRef = (TextView) view.findViewById(R.id.messageRef);
            messageRow = (LinearLayout) view.findViewById(R.id.message_container);
            idContainer = (LinearLayout) view.findViewById(R.id.idContainer);
            dateContainer = (LinearLayout) view.findViewById(R.id.dateContainer);
        }
    }

    public MessagesAdapter(Context mContext, ArrayList<Message> messages, MessagesAdapterListener listener) {
        this.mContext = mContext;
        this.messages = messages;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Message message = messages.get(position);
        holder.messageDate.setText(message.getMessageDate());
        holder.messageRef.setText(message.getMessageREF());
        applyClickEvents(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(messages.get(position).getMessageREF());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.messageRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClick(position);
            }
        });
        holder.idContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMessageRowClick(position);
            }
        });
        holder.dateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMessageRowClick(position);
            }
        });
    }

    public interface MessagesAdapterListener {
        void onMessageRowClick(int position);
    }
}
