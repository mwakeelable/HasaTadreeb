package com.linked_sys.tadreeb_ihssa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.models.SearchResult;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<SearchResult> programs;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView programName, programTime, programDate, regDate, programStatus;

        MyViewHolder(View view) {
            super(view);
            programName = (TextView) view.findViewById(R.id.txtProgramName);
            programTime = (TextView) view.findViewById(R.id.txtProgramTime);
            programDate = (TextView) view.findViewById(R.id.txtProgramDate);
            regDate = (TextView) view.findViewById(R.id.txtRegDate);
            programStatus = (TextView) view.findViewById(R.id.txtProgramStatus);
        }
    }

    public SearchAdapter(Context mContext, ArrayList<SearchResult> programs) {
        this.mContext = mContext;
        this.programs = programs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        SearchResult program = programs.get(position);
        holder.programName.setText(program.getProgramName());
        holder.programTime.setText(program.getProgramTime());
        holder.programDate.setText(program.getProgramDate());
        holder.regDate.setText(program.getRegistrationDate());
        holder.programStatus.setText(program.getProgramStatus());

    }

    @Override
    public int getItemCount() {
        return programs.size();
    }


}
