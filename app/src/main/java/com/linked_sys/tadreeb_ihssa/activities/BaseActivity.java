package com.linked_sys.tadreeb_ihssa.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.linked_sys.tadreeb_ihssa.core.AppController;
import com.linked_sys.tadreeb_ihssa.core.SessionManager;
import com.linked_sys.tadreeb_ihssa.core.SharedManager;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {
    public SessionManager session;
    SharedManager manager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        session = new SessionManager(this);
        manager = new SharedManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    protected abstract int getLayoutResourceId();

    public void openActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void restartActivity(Intent intent) {
        finish();
        startActivity(intent);
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(BaseActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void sendFBToken(String userID) {
        try{
            final String token = FirebaseInstanceId.getInstance().getToken();
            Map<String, String> map = new LinkedHashMap<>();
            map.put("UserID", userID);
            map.put("Token", token);

            ApiHelper api = new ApiHelper(this, ApiEndPoints.SEND_FB_TOKEN, Request.Method.POST, map, new ApiCallback() {
                @Override
                public void onSuccess(Object response) {
                    Log.d(AppController.TAG, (String) response);
                }

                @Override
                public void onFailure(VolleyError error) {
//                    Log.d(AppController.TAG, error.getMessage());
                }
            });
            api.executePostRequest(false);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
}
