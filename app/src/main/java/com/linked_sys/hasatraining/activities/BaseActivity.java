package com.linked_sys.hasatraining.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.SessionManager;
import com.linked_sys.hasatraining.core.SharedManager;

public abstract class BaseActivity extends AppCompatActivity {
    public SessionManager session;
    SharedManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        setContentView(getLayoutResourceId());
        session = new SessionManager(this);
        manager = new SharedManager();
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
}
