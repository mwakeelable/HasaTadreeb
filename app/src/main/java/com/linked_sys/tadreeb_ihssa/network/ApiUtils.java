package com.linked_sys.tadreeb_ihssa.network;

public class ApiUtils {
    public static DownloadTeacherCertificate downloadTeacherCertificate() {
        return RetrofitClient.getTeacherCertificate().create(DownloadTeacherCertificate.class);
    }
}
