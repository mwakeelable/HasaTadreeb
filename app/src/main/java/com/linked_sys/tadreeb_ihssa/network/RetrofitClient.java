package com.linked_sys.tadreeb_ihssa.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 8/2/17.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getTeacherCertificate() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiEndPoints.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
