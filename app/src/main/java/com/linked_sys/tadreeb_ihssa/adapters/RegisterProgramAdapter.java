package com.linked_sys.tadreeb_ihssa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.models.ProgramByPeriod;

import java.util.ArrayList;


public class RegisterProgramAdapter extends RecyclerView.Adapter<RegisterProgramAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<ProgramByPeriod> programs;
    private RegisterProgramAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView programName;
        LinearLayout courseRow;
        RelativeLayout row;

        MyViewHolder(View view) {
            super(view);
            programName = (TextView) view.findViewById(R.id.txt_programName);
            courseRow = (LinearLayout) view.findViewById(R.id.program_container);
            row = (RelativeLayout) view.findViewById(R.id.program_row);
        }
    }

    public RegisterProgramAdapter(Context mContext, ArrayList<ProgramByPeriod> programs, RegisterProgramAdapterListener listener) {
        this.mContext = mContext;
        this.programs = programs;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.register_program_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ProgramByPeriod program = programs.get(position);
        holder.programName.setText(program.getProgramName());
        applyClickEvents(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(programs.get(position).getProgramID());
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.courseRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProgramRowClicked(position);
            }
        });
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProgramRowClicked(position);
            }
        });
    }

    public interface RegisterProgramAdapterListener {
        void onProgramRowClicked(int position);
    }
}
