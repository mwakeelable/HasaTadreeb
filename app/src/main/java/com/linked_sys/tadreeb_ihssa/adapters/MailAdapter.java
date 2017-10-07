package com.linked_sys.tadreeb_ihssa.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        TextView subject, iconText, txt_date;
        ImageView imgProfile;
        LinearLayout iconContainer;
        RelativeLayout messageContainer, messageRow;

        MyViewHolder(View view) {
            super(view);
            subject = (TextView) view.findViewById(R.id.txt_primary);
            iconText = (TextView) view.findViewById(R.id.icon_text);
            txt_date = (TextView) view.findViewById(R.id.timestamp);
            imgProfile = (ImageView) view.findViewById(R.id.userImg);
            messageContainer = (RelativeLayout) view.findViewById(R.id.message_container);
            iconContainer = (LinearLayout) view.findViewById(R.id.iconContainer);
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
        holder.subject.setText(mail.getTitle());
        String date = "";
        try {
            date = getDateFormat(filteredList.get(position).getDate());
        } catch (Exception e) {
            date = filteredList.get(position).getDate();
        }
        holder.txt_date.setText(date);
        applyReadStatus(holder, mail);
        applyClickEvents(holder, position);
    }

    private String getDateFormat(String date) {
        String dateFormat = "";
        if (date.charAt(5) != '0')
            dateFormat += date.charAt(5);
        dateFormat += date.charAt(6);
        dateFormat += '/';
        if (date.charAt(8) != '0')
            dateFormat += date.charAt(8);
        dateFormat += date.charAt(9);
        dateFormat += '/';
        dateFormat += date.charAt(2);
        dateFormat += date.charAt(3);
        return dateFormat;
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
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
    }

    private void applyReadStatus(MyViewHolder holder, Mail mail) {
        if (mail.getIsRead().equals("Read")) {
            holder.subject.setTypeface(null, Typeface.NORMAL);
            holder.imgProfile.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.message_open));
            holder.subject.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        } else {
            holder.subject.setTypeface(null, Typeface.BOLD);
            holder.imgProfile.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.message_closed));
            holder.subject.setTextColor(ContextCompat.getColor(mContext, R.color.celestialblue));
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
