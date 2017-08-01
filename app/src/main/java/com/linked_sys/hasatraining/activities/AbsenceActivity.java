package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.models.ProgramStudents;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AbsenceActivity extends BaseActivity {
    ArrayList<ProgramStudents> studentsList = new ArrayList<>();
    LayoutInflater inflater;
    LinearLayout studentsLayout;
    String ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        studentsLayout = (LinearLayout) findViewById(R.id.students_container);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ref = bundle.getString("ref");
            getStudents();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.absence_activity;
    }

    private View getProgramStudenView(LayoutInflater inflater, final ProgramStudents programStudents, final int currentIndex) {
        View view = inflater.inflate(R.layout.program_student_item, null);
        TextView studentName = (TextView) view.findViewById(R.id.txt_studentName);
        TextView studentID = (TextView) view.findViewById(R.id.txt_studentID);
        TextView studentSchool = (TextView) view.findViewById(R.id.txt_studentSchool);
        RadioButton btnExist = (RadioButton) view.findViewById(R.id.btnExist);
        RadioButton btnNotExist = (RadioButton) view.findViewById(R.id.btnNotExist);
        studentName.setText(programStudents.getStudentName());
        studentID.setText(programStudents.getStudentID());
        studentSchool.setText(programStudents.getStudentSchool());

        btnExist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        btnNotExist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        return view;
    }

    public void getStudents() {
        String url = ApiEndPoints.GET_STUDENT_OF_PROGRAM
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&ProgREF=" + ref;
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                JSONArray studentsArr = res.optJSONArray("con");
                for (int i = 0; i < studentsArr.length(); i++) {
                    try {
                        JSONObject jsonObject = studentsArr.getJSONObject(i);
                        ProgramStudents students = new ProgramStudents();
                        students.setRegREF(jsonObject.optString("RegREF"));
                        students.setStudentID(jsonObject.optString("MotadarebID"));
                        students.setStudentName(jsonObject.optString("MotadarebFullName"));
                        students.setStudentSchool(jsonObject.optString("MotadarebSchool"));
                        studentsList.add(students);
                        View v = getProgramStudenView(inflater, students, i);
                        studentsLayout.addView(v);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                Toast.makeText(AbsenceActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
        api.executeRequest(true, false);
    }

}
