package com.linked_sys.tadreeb_ihssa.network;

import com.linked_sys.tadreeb_ihssa.models.FileStream;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DownloadTeacherCertificate {
    @GET(ApiEndPoints.PRINT_TEACHER_CERTIFICATE)
    Call<FileStream> getTeacherCertificate(@Query("ProgREF") String progRef);
}
