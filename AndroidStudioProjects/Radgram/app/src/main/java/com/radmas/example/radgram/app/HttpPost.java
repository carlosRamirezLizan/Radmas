package com.radmas.example.radgram.app;
import java.lang.String;
import android.os.AsyncTask;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raddev01 on 01/04/2014.
 */
public class HttpPost extends AsyncTask<String, Void, String> {
    public interface NetworkListener {
        void networkRequestCompleted(String result);
    }

    private NetworkListener _listener;
    private String resultResponse;
    Message message;
    Conversation conversation;
    private String contactPhone;
    private String userPhone;

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setResultResponse(String resultResponse){
        this.resultResponse = resultResponse;

    }



    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setListener(NetworkListener listener) {
        _listener = listener;
    }

    @Override
    protected String doInBackground(String... uri) {

        Gson gson = new Gson();
        String json = gson.toJson(conversation);

        /*
        JSONObject json = new JSONObject();

        try {
            json.put("user_Telephone", this.userPhone);
            json.put("contact_Telephone", this.contactPhone);
            json.put("conversation",this.conversation);
        }catch (JSONException e){}
*/

        String last_rev = "32-11d13c12cf3a9d00de9a2bfa96ee3ad0";
        if(resultResponse!=null){
            last_rev = resultResponse;
        }

        return  HttpRequest.put("http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369?rev="+last_rev)
                .header("referer", "http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369?rev="+last_rev)
                .contentType("application/json")
                .followRedirects(true)
                .send(json).body();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (_listener != null) {
            _listener.networkRequestCompleted(result);
        }
    }

}
