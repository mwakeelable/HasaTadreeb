package com.linked_sys.tadreeb_ihssa.network;

import com.android.volley.error.VolleyError;

public interface ApiCallback {

    public void onSuccess(Object response);
    public void onFailure(VolleyError error);

}
