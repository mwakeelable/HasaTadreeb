package com.linked_sys.tadreeb_ihssa.network;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.linked_sys.tadreeb_ihssa.components.SpinnerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask {
    private static final String TAG = "DownloadTask";
    private Context context;
    private String downloadUrl = "", downloadFileName = "";
    SpinnerDialog spinnerDialog;
    File apkStorage = null;

    public DownloadTask(Context context, String downloadUrl) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        downloadFileName = downloadUrl.replace(ApiEndPoints.mainUrl, "");//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);
        spinnerDialog = new SpinnerDialog(context);
        //Start Downloading Task
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinnerDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    spinnerDialog.hide();
                    Uri path = Uri.fromFile(outputFile);
                    Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                    pdfIntent.setDataAndType(path, "application/pdf");
                    pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    try{
                        context.startActivity(pdfIntent);
                    }catch(ActivityNotFoundException e){
                        Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    spinnerDialog.hide();
                    Log.e(TAG, "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection
                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode() + " " + c.getResponseMessage());
                }
                apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + ApiEndPoints.downloadDirectory);
                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }
                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File
                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }
                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location
                InputStream is = c.getInputStream();//Get InputStream for connection
                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }
                //Close all connection after doing task
                fos.close();
                is.close();
            } catch (Exception e) {
                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }
            return null;
        }
    }
}
