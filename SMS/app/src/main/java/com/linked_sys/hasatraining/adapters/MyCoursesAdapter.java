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
import com.linked_sys.hasatraining.models.Course;

import java.util.ArrayList;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<Course> courses;
    private ArrayList<Course> filteredList;
    private MyCourseFilter courseFilter;
    private CourseAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, courseDesc, rated;
        RelativeLayout courseRow;

        MyViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.txt_programName);
            courseDesc = (TextView) view.findViewById(R.id.txt_programPeriod);
            rated = (TextView) view.findViewById(R.id.txt_program_ref);
            courseRow = (RelativeLayout) view.findViewById(R.id.program_container);
        }
    }

    public MyCoursesAdapter(Context mContext, ArrayList<Course> courses, CourseAdapterListener listener) {
        this.mContext = mContext;
        this.courses = courses;
        this.filteredList = courses;
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
        Course course = filteredList.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.courseDesc.setText(course.getCourseDescription());
        holder.rated.setText(String.valueOf(course.isRated()));
        applyClickEvents(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return filteredList.get(position).getCourseID();
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.courseRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCourseRowClicked(position);
            }
        });
        holder.rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCourseRowClicked(position);
            }
        });
    }

    public interface CourseAdapterListener {
        void onCourseRowClicked(int position);
    }

    private class MyCourseFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Course> tempList = new ArrayList<>();
                // search content in tasks list
                for (Course task : courses) {
                    if (task.getCourseName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(task);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = courses.size();
                filterResults.values = courses;
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
            filteredList = (ArrayList<Course>) results.values;
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
