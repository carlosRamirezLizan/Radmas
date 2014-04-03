package com.radmas.example.radgram.app;
import java.lang.String;
import android.os.AsyncTask;
import com.github.kevinsawicki.http.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raddev01 on 01/04/2014.
 */
public class HttpPost extends AsyncTask<String, Void, String> {
    public interface NetworkListener {
        void networkRequestCompleted(String result);
    }
    String userPhone;
    private NetworkListener _listener;
    String message;
    String contactPhone;
    String resultResponse;

    public void setResultResponse(String resultResponse){
        this.resultResponse=resultResponse;
    }

    public void setUserPhone(String userPhone){
        this.userPhone=userPhone;
    }

    public void setContactPhone(String contactPhone){
        this.contactPhone=contactPhone;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setListener(NetworkListener listener) {
        _listener = listener;
    }

    @Override
    protected String doInBackground(String... uri) {
        JSONObject json = new JSONObject();

        try {
            json.put("user_Telephone", this.userPhone);
            json.put("contact_Telephone", this.contactPhone);
            json.put("message",this.message);
        }catch (JSONException e){}


        String last_rev = "28-6f868561c9776c65238b18b885139dfc";
        if(resultResponse!=null){
            last_rev = resultResponse;
        }

        return  HttpRequest.put("http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369?rev="+last_rev)
                .header("referer", "http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369?rev="+last_rev)
                .contentType("application/json")
                .followRedirects(true)
                .send(json.toString()).body();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (_listener != null) {
            _listener.networkRequestCompleted(result);
        }
    }

}
