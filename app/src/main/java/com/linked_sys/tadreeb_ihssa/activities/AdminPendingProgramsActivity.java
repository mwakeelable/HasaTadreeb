package com.linked_sys.tadreeb_ihssa.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Program;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminPendingProgramsActivity extends BaseActivity {
    ArrayList<Program> studentsList = new ArrayList<>();
    LayoutInflater inflater;
    LinearLayout studentsLayout;
    LinearLayout placeholder;
    RelativeLayout dataContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView backBtn = (ImageView) findViewById(R.id.backImgView);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView titleTxt = (TextView) findViewById(R.id.titleTxt);
        titleTxt.setText("البرامج المعلقة");
        placeholder = (LinearLayout) findViewById(R.id.no_data_placeholder);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        studentsLayout = (LinearLayout) findViewById(R.id.students_container);
        dataContainer = (RelativeLayout) findViewById(R.id.data_container);
        getPendingPrograms();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.admin_pending_programs_activity;
    }

    private View getProgramStudentView(LayoutInflater inflater, final Program program, final int currentIndex) {
        View view = inflater.inflate(R.layout.admin_pending_item, null);
        TextView studentName = (TextView) view.findViewById(R.id.txt_studentName);
        TextView programName = (TextView) view.findViewById(R.id.txt_programName);
        TextView programDate = (TextView) view.findViewById(R.id.txt_program_date);
        TextView programTime = (TextView) view.findViewById(R.id.txt_program_time);
        TextView programID = (TextView) view.findViewById(R.id.txt_programID);
        LinearLayout btnAccept = (LinearLayout) view.findViewById(R.id.btnAcceptProgram);
        LinearLayout btnDecline = (LinearLayout) view.findViewById(R.id.btnDeclineProgram);
        programName.setText(program.getProgramName());
        studentName.setText(program.getMotadarebFullName());
        programDate.setText(program.getProgramDate());
        programTime.setText(program.getProgramTime());
        programID.setText(program.getProgramID());
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(AdminPendingProgramsActivity.this)
                        .title("تأكـيد")
                        .content("هل تريد قبول هذا البرنامج؟")
                        .positiveText("نعم")
                        .negativeText("إلغاء")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                acceptProgram(program.getRegREF());
                            }
                        })
                        .show();
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(AdminPendingProgramsActivity.this)
                        .title("تأكـيد")
                        .content("هل تريد رفض هذا البرنامج؟")
                        .positiveText("نعم")
                        .negativeText("إلغاء")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                declineProgram(program.getRegREF());
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    private void getPendingPrograms() {
        String url = ApiEndPoints.ADMIN_PENDING_PROGRAMS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + session.getUserDetails().get(session.KEY_NATIONAL_ID)
                + "&PeriodId=" + CacheHelper.getInstance().adminPeriodSelectedID;
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                studentsList.clear();
                studentsLayout.removeAllViews();
                JSONObject res = (JSONObject) response;
                JSONArray programsArray = res.optJSONArray("con");
                if (programsArray.length() > 0) {
                    placeholder.setVisibility(View.GONE);
                    dataContainer.setVisibility(View.VISIBLE);
                    for (int i = 0; i < programsArray.length(); i++) {
                        JSONObject programObj = programsArray.optJSONObject(i);
                        Program program = new Program();
                        program.setRegREF(programObj.optString("RegREF"));
                        program.setProgramREF(programObj.optString("ProgramREF"));
                        program.setProgramID(programObj.optString("ProgramID"));
                        program.setProgramName(programObj.optString("ProgramName"));
                        program.setProgramDate(programObj.optString("ProgramDate"));
                        program.setProgramTime(programObj.optString("ProgramTime"));
                        program.setProgramLocation(programObj.optString("ProgramLocation"));
                        program.setMotadarebFullName(programObj.optString("MotadarebFullName"));
                        studentsList.add(program);
                        View v = getProgramStudentView(inflater, program, i);
                        studentsLayout.addView(v);
                    }
                } else {
                    placeholder.setVisibility(View.VISIBLE);
                    dataContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    private void acceptProgram(final String regRef) {
        String url = ApiEndPoints.ADMIN_UPDATE_PROGRAM_STATUS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&RegREF=" + regRef
                + "&IsAccept=true";
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                if (res.optString("con").equals("Success"))
                    new MaterialDialog.Builder(AdminPendingProgramsActivity.this)
                            .title("تم")
                            .content("تم قبول البرنامج بنجاح")
                            .positiveText("نعم")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    getPendingPrograms();
                                }
                            })
                            .show();
                else
                    Toast.makeText(AdminPendingProgramsActivity.this, "خطـــأ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    private void declineProgram(String regRef) {
        String url = ApiEndPoints.ADMIN_UPDATE_PROGRAM_STATUS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&RegREF=" + regRef
                + "&IsAccept=false";
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                if (res.optString("con").equals("Success"))
                    new MaterialDialog.Builder(AdminPendingProgramsActivity.this)
                            .title("تم")
                            .content("تم رفض البرنامج بنجاح")
                            .positiveText("نعم")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    getPendingPrograms();
                                }
                            })
                            .show();
                else
                    Toast.makeText(AdminPendingProgramsActivity.this, "خطـــأ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

}
