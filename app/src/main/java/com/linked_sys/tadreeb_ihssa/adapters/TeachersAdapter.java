package com.linked_sys.tadreeb_ihssa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.models.Teachers;

import java.util.ArrayList;

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<Teachers> teachersList;
    public ArrayList<Teachers> filteredList;
    private MyCourseFilter courseFilter;
    private TeachersAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView teacherName, teacherSpecalize;
        LinearLayout teacherRow;

        MyViewHolder(View view) {
            super(view);
            teacherName = (TextView) view.findViewById(R.id.txtTeacherName);
            teacherSpecalize = (TextView) view.findViewById(R.id.txtTeacherSpecalize);
            teacherRow = (LinearLayout) view.findViewById(R.id.teacher_container);
        }
    }

    public TeachersAdapter(Context mContext, ArrayList<Teachers> teachersList, TeachersAdapterListener listener) {
        this.mContext = mContext;
        this.teachersList = teachersList;
        this.filteredList = teachersList;
        this.listener = listener;
        getFilter();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Teachers teacher = filteredList.get(position);
        holder.teacherName.setText(teacher.getName());
        holder.teacherSpecalize.setText(teacher.getSpecialize());
        applyClickEvents(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(filteredList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.teacherRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTeacherRowClicked(position);
            }
        });
    }

    public interface TeachersAdapterListener {
        void onTeacherRowClicked(int position);
    }

    private class MyCourseFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Teachers> tempList = new ArrayList<>();
                // search content in tasks list
                for (Teachers teachers : teachersList) {
                    if (teachers.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(teachers);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = teachersList.size();
                filterResults.values = teachersList;
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
            filteredList = (ArrayList<Teachers>) results.values;
            notifyDataSetChanged();
        }
    }

    @Override
    public Filter getFilter() {
        if (courseFilter == null) {
            courseFilter = new MyCourseFilter();
        }
        return courseFilter;
    }

}
