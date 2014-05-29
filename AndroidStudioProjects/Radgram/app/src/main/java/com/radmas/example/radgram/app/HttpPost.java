package com.radmas.example.radgram.app;
import java.lang.String;
import android.os.AsyncTask;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

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
    private Database database;
    private boolean postToCreateDatabase;

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setResultResponse(String resultResponse){
        this.resultResponse = resultResponse;
    }

    public void setPostToCreateDatabase(boolean postToCreateDatabase) {
        this.postToCreateDatabase = postToCreateDatabase;
    }

    public boolean isPostToCreateDatabase() {
        return postToCreateDatabase;
    }

    public void setDatabase(Database database) {
        this.database = database;
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

        String last_rev = "1-db63d5463bf4d3064280690e18585786";
        if(resultResponse!=null){
            last_rev = resultResponse;
        }

        if(!isPostToCreateDatabase()) {
            return HttpRequest.put("http://192.168.1.12:5984/albums/" + database.getDatabaseId()+"?rev="+last_rev)
                    .header("referer", "http://192.168.1.12:5984/albums/" + database.getDatabaseId()+"?rev="+last_rev)
                    .contentType("application/json")
                    .followRedirects(true)
                    .send(json).body();
        }
        else{ //we need to create the database
            return HttpRequest.put("http://192.168.1.12:5984/albums/"+database.getDatabaseId()+"/1.0")
                    .header("referer", "http://192.168.1.12:5984/albums/" + database.getDatabaseId()+"/1.0")
                    .body();
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
