package com.linked_sys.hasatraining.activities;

import com.linked_sys.hasatraining.R;

public class MyCertificatesActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.my_certificates_activity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }
}
