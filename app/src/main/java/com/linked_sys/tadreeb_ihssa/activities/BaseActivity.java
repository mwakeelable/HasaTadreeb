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
import com.google.firebase.messaging.FirebaseMessaging;
import com.linked_sys.tadreeb_ihssa.core.AppController;
import com.linked_sys.tadreeb_ihssa.core.SessionManager;
import com.linked_sys.tadreeb_ihssa.core.SharedManager;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.linked_sys.tadreeb_ihssa.core.CacheHelper.newMails;

public abstract class BaseActivity extends AppCompatActivity {
    public static SessionManager session;
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
        try {
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
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public void removeFBToken() {
        try {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("UserID", session.getUserDetails().get(session.KEY_NATIONAL_ID));
            map.put("Token", FirebaseInstanceId.getInstance().getToken());
            ApiHelper api = new ApiHelper(this, ApiEndPoints.REMOVE_FB_TOKEN, Request.Method.POST, map, new ApiCallback() {
                @Override
                public void onSuccess(Object response) {
                    session.logoutUser();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("All");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("Monaseq");
                    FirebaseMessaging.getInstance().subscribeToTopic("Motdareb");
                    FirebaseMessaging.getInstance().subscribeToTopic("Modareb");
                    newMails = 0;
                    setBadge(BaseActivity.this, newMails);
                }

                @Override
                public void onFailure(VolleyError error) {
//                    session.logoutUser();

                }
            });
            api.executePostRequest(true);
        } catch (Exception e) {
            session.logoutUser();
        }
    }

    public void getUnreadMessagesCount() {
        String url = ApiEndPoints.GET_MESSAGE_COUNT
                + "?APPCode=zunIhQwuD38JfFkSQBCk8gzvK5aJQaoahacqSJLhRcg="
                + "&UserID=" + session.getUserDetails().get(session.KEY_ID_REF);
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                String count = res.optString("ret");
                newMails = Integer.parseInt(count);
                setBadge(BaseActivity.this, Integer.parseInt(res.optString("ret")));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    public static void setBadge(Context context, int count) {
        ShortcutBadger.applyCount(context, count);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setBadge(this, newMails);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setBadge(this, newMails);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBadge(this, newMails);
    }
}
