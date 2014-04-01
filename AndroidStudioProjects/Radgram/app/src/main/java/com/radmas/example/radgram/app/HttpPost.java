package com.radmas.example.radgram.app;
import java.lang.String;
import android.os.AsyncTask;

/**
 * Created by raddev01 on 01/04/2014.
 */
public class HttpPost extends AsyncTask<String, Void, String> {
    public interface NetworkListener {
        void networkRequestCompleted(String result);
    }

    private NetworkListener _listener;

    public void setListener(NetworkListener listener) {
        _listener = listener;
    }

    @Override
    protected String doInBackground(String... uri) {
        Integer i = com.github.kevinsawicki.http.HttpRequest.post(uri[0]).send("name=kevin").code();
        return i.toString();
        /*if(i == 200 || i == 201 || i == 203){
            Gson gson = new Gson();
            gson.toJson()
        }else{

        }
                .body();

                */
//
//
//
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpResponse response;
//        String responseString = null;
//        try {
//            response = httpclient.execute(new HttpGet(uri[0]));
//            StatusLine statusLine = response.getStatusLine();
//            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                response.getEntity().writeTo(out);
//                out.close();
//                responseString = out.toString();
//            } else {
//                //Closes the connection.
//                response.getEntity().getContent().close();
//                throw new IOException(statusLine.getReasonPhrase());
//            }
//        } catch (ClientProtocolException e) {
//            Log.d("debug:", "failed: " + e.getMessage());
//        } catch (IOException e) {
//            Log.d("debug:", "failed: " + e.getMessage());
//        }
//        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (_listener != null) {
            _listener.networkRequestCompleted(result);
        }
    }

   /* public String post(String posturl) {

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(posturl);
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("email", "user@gmail.com"));
            nameValuePair.add(new BasicNameValuePair("message", "Hi, trying Android HTTP post!"));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta
            String text = EntityUtils.toString(ent);
            return text;

        } catch (Exception e) {
            return "error";
        }
    }*/
}
