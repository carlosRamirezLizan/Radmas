package com.radmas.example.radgram.app;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;

import java.lang.String;

import de.greenrobot.event.EventBus;

/**
 * Created by raddev01 on 31/03/2014.
 */
public class HttpRequest extends AsyncTask<String, Void, String> {
    public interface NetworkListener {
        void networkRequestCompleted(String result);
    }

    private NetworkListener _listener;

    public void setListener(NetworkListener listener) {
        _listener = listener;
    }

    @Override
    protected String doInBackground(String... uri) {
        String json = com.github.kevinsawicki.http.HttpRequest.get(uri[0]).body();
        int code = com.github.kevinsawicki.http.HttpRequest.get(uri[0]).code();
        String no_valid = "Error al recibir la información";
        if (code == 200 ) {
            Gson gson = new GsonBuilder().create();
            Conversation response = (Conversation) gson.fromJson(json, Conversation.class);
            EventBus.getDefault().post(response);
            return response._rev;
        } else {
            return no_valid;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (_listener != null) {
            _listener.networkRequestCompleted(result);
        }
    }


}


