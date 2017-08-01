package com.linked_sys.hasatraining.network;

import com.android.volley.error.VolleyError;

public interface ApiCallback {

    public void onSuccess(Object response);
    public void onFailure(VolleyError error);

}
