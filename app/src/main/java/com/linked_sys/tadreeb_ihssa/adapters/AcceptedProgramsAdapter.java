package com.linked_sys.tadreeb_ihssa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Program;

import java.util.ArrayList;

public class AcceptedProgramsAdapter extends RecyclerView.Adapter<AcceptedProgramsAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<Program> programs;
    private MyCourseFilter courseFilter;
    private AcceptedProgramsAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView programName, studentName;
        RelativeLayout courseRow;

        MyViewHolder(View view) {
            super(view);
            programName = (TextView) view.findViewById(R.id.txtProgramName);
            studentName = (TextView) view.findViewById(R.id.txtStudentName);
            courseRow = (RelativeLayout) view.findViewById(R.id.program_container);
        }
    }

    public AcceptedProgramsAdapter(Context mContext, ArrayList<Program> programs, AcceptedProgramsAdapterListener listener) {
        this.mContext = mContext;
        this.programs = programs;
        CacheHelper.getInstance().filteredList = programs;
        this.listener = listener;
        getFilter();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.accepted_programs_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Program program = CacheHelper.getInstance().filteredList.get(position);
        holder.programName.setText(program.getProgramName());
        holder.studentName.setText(program.getMotadarebFullName());
        applyClickEvents(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(CacheHelper.getInstance().filteredList.get(position).getProgramID());
    }

    @Override
    public int getItemCount() {
        return CacheHelper.getInstance().filteredList.size();
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.courseRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProgramRowClicked(position);
            }
        });
//        holder.programREF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onProgramRowClicked(position);
//            }
//        });
    }

    public interface AcceptedProgramsAdapterListener {
        void onProgramRowClicked(int position);
    }

    private class MyCourseFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Program> tempList = new ArrayList<>();
                // search content in tasks list
                for (Program program : programs) {
                    if (program.getProgramName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(program);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = programs.size();
                filterResults.values = programs;
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
            CacheHelper.getInstance().filteredList = (ArrayList<Program>) results.values;
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