package com.linked_sys.hasatraining.network;

public class ApiUtils {
    public static DownloadTeacherCertificate downloadTeacherCertificate() {
        return RetrofitClient.getTeacherCertificate().create(DownloadTeacherCertificate.class);
    }
}
