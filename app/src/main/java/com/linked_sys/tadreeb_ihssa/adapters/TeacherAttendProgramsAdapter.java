package com.linked_sys.tadreeb_ihssa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.TeacherProgram;

import java.util.ArrayList;

public class TeacherAttendProgramsAdapter extends RecyclerView.Adapter<TeacherAttendProgramsAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<TeacherProgram> programs;
    private TeacherProgramsFilter courseFilter;
    private TeacherProgramsAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView programName;
        RelativeLayout courseRow;
        LinearLayout container;

        MyViewHolder(View view) {
            super(view);
            programName = (TextView) view.findViewById(R.id.txt_teacherProgramName);
            courseRow = (RelativeLayout) view.findViewById(R.id.teacher_program_row);
            container = (LinearLayout) view.findViewById(R.id.teacher_program_container);
        }
    }

    public TeacherAttendProgramsAdapter(Context mContext, ArrayList<TeacherProgram> programs, TeacherProgramsAdapterListener listener) {
        this.mContext = mContext;
        this.programs = programs;
        CacheHelper.getInstance().attendFilteredList = programs;
        this.listener = listener;
        getFilter();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_program_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TeacherProgram program = CacheHelper.getInstance().attendFilteredList.get(position);
        holder.programName.setText(program.getProgramName());
//        if (program.isCanPrintCertificate() && !program.isMustRate())
//            holder.programStatus.setText(R.string.btnPrint);
//        else if (program.isMustRate() && !program.isCanPrintCertificate())
//            holder.programStatus.setText(R.string.btnRate);
//        else
//            holder.programStatus.setText("");
        applyClickEvents(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(CacheHelper.getInstance().attendFilteredList.get(position).getProgramID());
    }

    @Override
    public int getItemCount() {
        return CacheHelper.getInstance().attendFilteredList.size();
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
//        holder.courseRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onProgramRowClicked(position);
//            }
//        });
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProgramRowClicked(position);
            }
        });
    }

    public interface TeacherProgramsAdapterListener {
        void onProgramRowClicked(int position);
    }

    private class TeacherProgramsFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<TeacherProgram> tempList = new ArrayList<>();
                // search content in tasks list
                for (TeacherProgram program : programs) {
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
            CacheHelper.getInstance().attendFilteredList = (ArrayList<TeacherProgram>) results.values;
            notifyDataSetChanged();
        }
    }

    @Override
    public Filter getFilter() {
        if (courseFilter == null) {
            courseFilter = new TeacherProgramsFilter();
        }
        return courseFilter;
    }
}
