package com.linked_sys.hasatraining.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.AppController;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.models.FileStream;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.DownloadTeacherCertificate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherProgramDetailsActivity extends BaseActivity {
    TextView txtProgramRef, txtProgramDays, txtProgramID, txtProgramName, txtProgramDateFrom, txtProgramDateTo, txtProgramTime, txtProgramLocation;
    int pos;
    boolean canPrint, mustAttend;
    CardView btnAbsence, btnPrint;
    static final int REQUEST_ABSENCE_CODE = 0;
    String comeFrom;
    String backAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View shadow = findViewById(R.id.toolbar_shadow);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            shadow.setVisibility(View.VISIBLE);
        else
            shadow.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtProgramRef = (TextView) findViewById(R.id.txt_program_ref);
        txtProgramDays = (TextView) findViewById(R.id.txt_program_days);
        txtProgramID = (TextView) findViewById(R.id.txt_program_id);
        txtProgramName = (TextView) findViewById(R.id.txt_program_name);
        txtProgramDateFrom = (TextView) findViewById(R.id.txt_program_date_from);
        txtProgramDateTo = (TextView) findViewById(R.id.txt_program_date_to);
        txtProgramTime = (TextView) findViewById(R.id.txt_program_time);
        txtProgramLocation = (TextView) findViewById(R.id.txt_program_location);
        btnAbsence = (CardView) findViewById(R.id.btnAbsence);
        btnPrint = (CardView) findViewById(R.id.btnPrint);
        btnAbsence.setVisibility(View.GONE);
        btnPrint.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pos = bundle.getInt("pos");
            comeFrom = bundle.getString("comeFrom");
            getProgramDetails();
        }

        btnAbsence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent absenceIntent = new Intent(TeacherProgramDetailsActivity.this, AbsenceActivity.class);
                absenceIntent.putExtra("ref", txtProgramRef.getText().toString());
                startActivityForResult(absenceIntent, REQUEST_ABSENCE_CODE);
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ApiEndPoints.BASE_URL + ApiEndPoints.TEACHER_CERTIFICATE_URL + "?ProgREF=" + txtProgramRef.getText().toString();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);

//                String url = ApiEndPoints.PRINT_TEACHER_CERTIFICATE + "?ProgREF=" + txtProgramID.getText().toString() ;
//                ApiHelper api = new ApiHelper(TeacherProgramDetailsActivity.this, url, Request.Method.GET, new ApiCallback() {
//                    @Override
//                    public void onSuccess(Object response) {
//                        JSONObject res = (JSONObject) response;
//                        JSONObject obj = res.optJSONObject("con");
//                        JSONObject file = obj.optJSONObject("FileStream");
//                        try {
//                            InputStream in = IOUtils.toInputStream(file.optString("_buffer"), "UTF-8");
//                            boolean success = writeResponseBodyToDisk(in, obj.optString("FileDownloadName"));
//                            Toast.makeText(TeacherProgramDetailsActivity.this, "Download was Successful: " + success, Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(VolleyError error) {
//                        Toast.makeText(TeacherProgramDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                api.executeRequest(true, false);
            }
        });
    }

    private void getProgramDetails() {
        if (comeFrom.equals("done")) {
            txtProgramRef.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getREF());
            txtProgramDays.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramDays());
            txtProgramID.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramID());
            txtProgramName.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramName());
            txtProgramDateFrom.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramDateStrat());
            txtProgramDateTo.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramDateEnd());
            txtProgramTime.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramTimes());
            txtProgramLocation.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramLocation());
            canPrint = CacheHelper.getInstance().doneFilteredList.get(pos).isCanPrintCertificate();
            mustAttend = CacheHelper.getInstance().doneFilteredList.get(pos).isMustAttend();
            if (canPrint && !mustAttend) {
                btnPrint.setVisibility(View.VISIBLE);
                btnAbsence.setVisibility(View.GONE);
            } else if (mustAttend && !canPrint) {
                btnPrint.setVisibility(View.GONE);
                btnAbsence.setVisibility(View.VISIBLE);
            } else {
                btnPrint.setVisibility(View.GONE);
                btnAbsence.setVisibility(View.GONE);
            }
        } else if (comeFrom.equals("attend")) {
            txtProgramRef.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getREF());
            txtProgramDays.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramDays());
            txtProgramID.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramID());
            txtProgramName.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramName());
            txtProgramDateFrom.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramDateStrat());
            txtProgramDateTo.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramDateEnd());
            txtProgramTime.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramTimes());
            txtProgramLocation.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramLocation());
            canPrint = CacheHelper.getInstance().attendFilteredList.get(pos).isCanPrintCertificate();
            mustAttend = CacheHelper.getInstance().attendFilteredList.get(pos).isMustAttend();
            if (canPrint && !mustAttend) {
                btnPrint.setVisibility(View.VISIBLE);
                btnAbsence.setVisibility(View.GONE);
            } else if (mustAttend && !canPrint) {
                btnPrint.setVisibility(View.GONE);
                btnAbsence.setVisibility(View.VISIBLE);
            } else {
                btnPrint.setVisibility(View.GONE);
                btnAbsence.setVisibility(View.GONE);
            }
        }
    }

    private void downloadCertificate(final String name, final InputStream inputStream) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(ApiEndPoints.BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        DownloadTeacherCertificate downloadTeacherCertificate = retrofit.create(DownloadTeacherCertificate.class);
        Call<FileStream> call = downloadTeacherCertificate.getTeacherCertificate(txtProgramRef.getText().toString());
        call.enqueue(new Callback<FileStream>() {
            @Override
            public void onResponse(Call<FileStream> call, Response<FileStream> response) {
                InputStream stream = null;
                stream = inputStream;
                boolean success = writeResponseBodyToDisk(stream, name);
                Toast.makeText(TeacherProgramDetailsActivity.this, "Download was Successful:" + success, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FileStream> call, Throwable t) {
                Toast.makeText(TeacherProgramDetailsActivity.this, "No :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean writeResponseBodyToDisk(InputStream stream, String name) {
        try {
            // todo change the file location/name according to your needs
            File certificateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSizeDownloaded = 0;
                inputStream = stream;
                outputStream = new FileOutputStream(certificateFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                Log.d(AppController.TAG, "file download: " + fileSizeDownloaded);
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.teacher_program_details_activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ABSENCE_CODE && resultCode == RESULT_OK && data != null) {
            comeFrom = data.getStringExtra("comeFrom");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pos = bundle.getInt("pos");
            comeFrom = bundle.getString("comeFrom");
            getProgramDetails();
        }
    }
}
