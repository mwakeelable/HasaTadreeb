package com.linked_sys.tadreeb_ihssa.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.models.Mail;

import java.util.ArrayList;

/**
 * Created by wakeel on 26/09/17.
 */

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<Mail> mailList;
    public ArrayList<Mail> filteredList;
    private MailAdapterListener listener;
    private MessageFilter messageFilter;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView from, subject, message, iconText, txt_date;
        ImageView imgProfile;
        RelativeLayout iconContainer, iconBack, iconFront, messageContainer, messageRow;

        MyViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            subject = (TextView) view.findViewById(R.id.txt_primary);
            message = (TextView) view.findViewById(R.id.txt_secondary);
            iconText = (TextView) view.findViewById(R.id.icon_text);
            txt_date = (TextView) view.findViewById(R.id.timestamp);
            iconBack = (RelativeLayout) view.findViewById(R.id.icon_back);
            iconFront = (RelativeLayout) view.findViewById(R.id.icon_front);
            imgProfile = (ImageView) view.findViewById(R.id.icon_profile);
            messageContainer = (RelativeLayout) view.findViewById(R.id.message_container);
            iconContainer = (RelativeLayout) view.findViewById(R.id.icon_container);
            messageRow = (RelativeLayout) view.findViewById(R.id.message_row);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public MailAdapter(Context mContext, ArrayList<Mail> mailList, MailAdapterListener listener) {
        this.mContext = mContext;
        this.mailList = mailList;
        this.listener = listener;
        this.filteredList = mailList;
        getFilter();
    }

    public interface MailAdapterListener {
        void onIconClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mail_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MailAdapter.MyViewHolder holder, int position) {
        Mail mail = filteredList.get(position);
//        holder.from.setText(message.getSenderName1() + " " + message.getSenderName4());
        holder.subject.setText(mail.getTitle());
//        holder.message.setMovementMethod(LinkMovementMethod.getInstance());
        holder.message.setText(mail.getBody());
//        String date = getDateFormat(message.getTimestamp());
//        long timeStamp = convertToTimestamp(date);
//        holder.timestamp.setTimeStamp(timeStamp);
//        holder.timestamp.setVisibility(View.GONE);
//        holder.txt_date.setText(date);
//        String senderName = message.getSenderName1().substring(0, 1).toUpperCase() + message.getSenderName4().substring(0, 1).toUpperCase();
//        holder.iconText.setText(senderName);
        applyReadStatus(holder, mail);
//        applyIconAnimation(holder, position);
//        applyProfilePicture(holder, mail);
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.iconFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.messageRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

//        holder.timestamp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onMessageRowClicked(position);
//            }
//        });
    }

    private void applyReadStatus(MyViewHolder holder, Mail mail) {
        if (mail.getIsRead().equals("Read")) {
            holder.from.setTypeface(null, Typeface.NORMAL);
            holder.subject.setTypeface(null, Typeface.NORMAL);
            holder.from.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
            holder.subject.setTextColor(ContextCompat.getColor(mContext, R.color.message));
        } else {
            holder.from.setTypeface(null, Typeface.BOLD);
            holder.subject.setTypeface(null, Typeface.BOLD);
            holder.from.setTextColor(ContextCompat.getColor(mContext, R.color.from));
            holder.subject.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
        }
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        if (messageFilter == null) {
            messageFilter = new MessageFilter();
        }
        return messageFilter;
    }

    private class MessageFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Mail> tempList = new ArrayList<>();
                // search content in messages list
                for (Mail message : mailList) {
                    if (message.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(message);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mailList.size();
                filterResults.values = mailList;
            }
            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Mail>) results.values;
            notifyDataSetChanged();
        }
    }
}
