package com.linked_sys.hasatraining.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.models.Program;

import java.util.ArrayList;

public class AllProgramsAdapter extends RecyclerView.Adapter<AllProgramsAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<Program> programs;
    public ArrayList<Program> filteredList;
    private MyCourseFilter courseFilter;
    private AllProgramsAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView programName;
        RelativeLayout courseRow;

        MyViewHolder(View view) {
            super(view);
//            programREF = (TextView) view.findViewById(R.id.txt_program_ref);
            programName = (TextView) view.findViewById(R.id.txt_programName);
//            programPeriod = (TextView) view.findViewById(R.id.txt_programPeriod);
//            programTime = (TextView) view.findViewById(R.id.txt_programTime);
//            programDate = (TextView) view.findViewById(R.id.txt_program_date);
            courseRow = (RelativeLayout) view.findViewById(R.id.program_container);
        }
    }

    public AllProgramsAdapter(Context mContext, ArrayList<Program> programs, AllProgramsAdapterListener listener) {
        this.mContext = mContext;
        this.programs = programs;
        this.filteredList = programs;
        this.listener = listener;
        getFilter();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Program program = filteredList.get(position);
        holder.programName.setText(program.getProgramName());
//        holder.programPeriod.setText(program.getProgramDays());
//        holder.programREF.setText(program.getProgramREF());
//        holder.programTime.setText(program.getProgramTimes());
//        holder.programDate.setText(program.getProgramDateStrat());
        applyClickEvents(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(filteredList.get(position).getProgramID());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
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

    public interface AllProgramsAdapterListener {
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
            filteredList = (ArrayList<Program>) results.values;
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
