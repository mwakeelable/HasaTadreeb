package com.linked_sys.hasatraining.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.AppController;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.models.ProgramStudents;
import com.linked_sys.hasatraining.models.StudentAbsence;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AbsenceActivity extends BaseActivity {
    ArrayList<ProgramStudents> studentsList = new ArrayList<>();
    ArrayList<StudentAbsence> absenceList = new ArrayList<>();
    HashMap<String, Boolean> isAttend = new HashMap<>();
    LayoutInflater inflater;
    LinearLayout studentsLayout;
    String ref, periodID;
    TextView txtAbsenceDay, txtAbsencePeriod,txtProgramName;
    CardView btnSubmitAbsence;
    StudentAbsence studentAbsence;
    RadioButton btnExist, btnNotExist;
    String finished;
    LinearLayout placeholder;
    RelativeLayout dataContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        studentsLayout = (LinearLayout) findViewById(R.id.students_container);
        txtAbsenceDay = (TextView) findViewById(R.id.txtAbsenceDay);
        txtAbsencePeriod = (TextView) findViewById(R.id.txtAbsencePeriod);
        btnSubmitAbsence = (CardView) findViewById(R.id.btnSubmitAbsence);
        placeholder = (LinearLayout) findViewById(R.id.no_data_placeholder);
        dataContainer = (RelativeLayout) findViewById(R.id.data_container);
        txtProgramName = (TextView) findViewById(R.id.txtProgramName);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ref = bundle.getString("ref");
            getStudents();
            getProgramStatus();
        }
        isAttend.clear();
        checkIsFinished();
    }

    private void fillAbsenceObj(int pos) {
        studentAbsence = new StudentAbsence();
        studentAbsence.setRegRef(studentsList.get(pos).getRegREF());
        studentAbsence.setProgRef(ref);
        studentAbsence.setAttend(isAttend.get(studentsList.get(pos).getRegREF()));
        studentAbsence.setAttendDay(txtAbsenceDay.getText().toString());
        studentAbsence.setAttendPeriod(periodID);
        absenceList.add(studentAbsence);
//        Log.d(AppController.TAG, String.valueOf(absenceList.get(pos).getRegRef()));
//        Log.d(AppController.TAG, absenceList.get(pos).getProgRef());
//        Log.d(AppController.TAG, String.valueOf(absenceList.get(pos).isAttend()));
//        Log.d(AppController.TAG, String.valueOf(absenceList.get(pos).getAttendDay()));
//        Log.d(AppController.TAG, String.valueOf(absenceList.get(pos).getAttendPeriod()));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.absence_activity;
    }

    private View getProgramStudentView(LayoutInflater inflater, final ProgramStudents programStudents, final int currentIndex) {
        View view = inflater.inflate(R.layout.program_student_item, null);
        TextView studentName = (TextView) view.findViewById(R.id.txt_studentName);
        TextView studentID = (TextView) view.findViewById(R.id.txt_studentID);
        TextView studentSchool = (TextView) view.findViewById(R.id.txt_studentSchool);
        btnExist = (RadioButton) view.findViewById(R.id.btnExist);
        btnNotExist = (RadioButton) view.findViewById(R.id.btnNotExist);
        studentName.setText(programStudents.getStudentName());
        studentID.setText(programStudents.getStudentID());
        studentSchool.setText(programStudents.getStudentSchool());

        btnExist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAttend.put(studentsList.get(currentIndex).getRegREF(), true);
            }
        });

        btnNotExist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAttend.put(studentsList.get(currentIndex).getRegREF(), false);
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
                studentsList.clear();
                JSONObject res = (JSONObject) response;
                JSONArray studentsArr = res.optJSONArray("con");
                if (studentsArr.length() > 0) {
                    placeholder.setVisibility(View.GONE);
                    dataContainer.setVisibility(View.VISIBLE);
                    for (int i = 0; i < studentsArr.length(); i++) {
                        try {
                            JSONObject jsonObject = studentsArr.getJSONObject(i);
                            ProgramStudents students = new ProgramStudents();
                            students.setRegREF(jsonObject.optString("RegREF"));
                            students.setStudentID(jsonObject.optString("MotadarebID"));
                            students.setStudentName(jsonObject.optString("MotadarebFullName"));
                            students.setStudentSchool(jsonObject.optString("MotadarebSchool"));
                            studentsList.add(students);
                            View v = getProgramStudentView(inflater, students, i);
                            studentsLayout.addView(v);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    placeholder.setVisibility(View.VISIBLE);
                    dataContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                Toast.makeText(AbsenceActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
        api.executeRequest(true, false);
    }

    private void getProgramStatus() {
        String url = ApiEndPoints.GET_PROGRAM_STATUS
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&ProgREF=" + ref;
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                JSONObject statusObj = res.optJSONObject("con");
                txtAbsenceDay.setText(statusObj.optString("RemainDays"));
                txtAbsencePeriod.setText(statusObj.optString("AttendPeriodName"));
                periodID = statusObj.optString("AttendPeriodID");
                txtProgramName.setText(statusObj.optString("ProgramName"));
            }

            @Override
            public void onFailure(VolleyError error) {
                Toast.makeText(AbsenceActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
        api.executeRequest(false, false);
    }

    private void submitAbsenceByOne(int pos) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("RegREF", absenceList.get(pos).getRegRef());
        params.put("ProgramREF", absenceList.get(pos).getProgRef());
        params.put("IsAttend", String.valueOf(absenceList.get(pos).isAttend()));
        params.put("AttendDay", absenceList.get(pos).getAttendDay());
        params.put("AttendPeriod", absenceList.get(pos).getAttendPeriod());
        ApiHelper api = new ApiHelper(this, ApiEndPoints.SUBMIT_ABSENCE_BY_ONE, Request.Method.POST, params, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
            }

            @Override
            public void onFailure(VolleyError error) {
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.d("Error", body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }
        });
        api.executePostRequest(true);
    }

    private void submitAbsence() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("RegREF", absenceList.get(0).getRegRef());
        params.put("ProgramREF", absenceList.get(0).getProgRef());
        params.put("IsAttend", String.valueOf(absenceList.get(0).isAttend()));
        params.put("AttendDay", absenceList.get(0).getAttendDay());
        params.put("AttendPeriod", absenceList.get(0).getAttendPeriod());
        ApiHelper api = new ApiHelper(this, ApiEndPoints.SUBMIT_ABSENCE, Request.Method.POST, params, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
                new MaterialDialog.Builder(AbsenceActivity.this)
                        .title("التحضيـر")
                        .content("تم التحضير بنجاح")
                        .positiveText("تم")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //Reload
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(VolleyError error) {
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.d("Error", body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }
        });
        api.executePostRequest(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void checkIsFinished() {
        String url = ApiEndPoints.CHECK_ATTENDANCE
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserID=" + session.getUserDetails().get(session.KEY_ID)
                + "&ProgRef=" + ref;

        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                if (res.optString("message").equals("false")) {
                    finished = "false";
                    btnSubmitAbsence.setVisibility(View.VISIBLE);
                    btnSubmitAbsence.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isAttend.size() == studentsList.size()) {
                                for (int i = 0; i < studentsList.size(); i++) {
                                    fillAbsenceObj(i);
                                    submitAbsenceByOne(i);
                                }
                                submitAbsence();
                            } else {
                                new MaterialDialog.Builder(AbsenceActivity.this)
                                        .title("خطــأ")
                                        .content("الرجاء تحديد الكل")
                                        .positiveText("تم")
                                        .show();
                            }
                        }
                    });
                } else if (res.optString("message").equals("true")) {
                    finished = "true";
                    finish();
//                    btnSubmitAbsence.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(false, false);
    }

}
