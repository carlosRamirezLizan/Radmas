package com.radmas.example.radgram.app;

import android.os.AsyncTask;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.File;
import java.io.IOException;

/**
 * Created by raddev01 on 28/03/2014.
 */
public class DownloadTask extends AsyncTask<String, Long, File> {
    protected File doInBackground(String... urls) {
        try {
            HttpRequest request =  HttpRequest.get(urls[0]);
            System.out.println("Response was: " + request);
            File file = null;
            if (request.ok()) {
                try{
                    file = File.createTempFile("download", ".tmp");}
                catch (IOException io){
                }
                request.receive(file);
                publishProgress(file.length());
            }
            return file;
        } catch (HttpRequest.HttpRequestException exception) {
            return null;
        }
    }

    protected void onProgressUpdate(Long... progress) {
        Log.d("MyApp", "Downloaded bytes: " + progress[0]);
    }

    protected void onPostExecute(File file) {
        if (file != null)
            Log.d("MyApp", "Downloaded file to: " + file.getAbsolutePath());
        else
            Log.d("MyApp", "Download failed");
    }
}
