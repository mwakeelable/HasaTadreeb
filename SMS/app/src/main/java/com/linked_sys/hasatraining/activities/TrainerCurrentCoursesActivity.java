package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.AppController;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

public class TrainerCurrentCoursesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentCourses();
    }

    private void getCurrentCourses(){
        String coursesURL = ApiEndPoints.INSTRUCTIR_PROGRAMS_URL
                + "?ACODE=" + session.getUserToken()
                + "&ID_Number=" + session.getUserDetails().get(session.KEY_REF);
        ApiHelper coursesAPI = new ApiHelper(this, coursesURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG,response.toString());
            }

            @Override
            public void onFailure(VolleyError error) {
                Log.d(AppController.TAG,"Failed");
            }
        });
        coursesAPI.executeRequest(true,true);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.trainer_current_courses_activity;
    }
}
