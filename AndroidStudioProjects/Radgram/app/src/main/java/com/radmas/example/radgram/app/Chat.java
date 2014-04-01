package com.radmas.example.radgram.app;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.github.kevinsawicki.http.HttpRequest;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by raddev01 on 28/03/2014.
 */
public class Chat extends ActionBarActivity implements com.radmas.example.radgram.app.HttpPost.NetworkListener, com.radmas.example.radgram.app.HttpRequest.NetworkListener{
    public TextView outputText;
    public TextView outputText2;
    public String[] resultados=new String[2];
    public int i=0;

    URI uri;
    URI uri2;
    TextView tvIsConnected;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat);
            tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

            // check if you are connected or not
            if(isConnected()){
                tvIsConnected.setBackgroundColor(0xFF00CC00);
                tvIsConnected.setText("You are connected");
            }
            else{
                tvIsConnected.setText("You are NOT connected");
            }
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);


            try{uri = new URI("http://127.0.0.1:5984/_utils/document.html?albums/19640b3ad1fda6c9863575c751063369");
            //try{uri = new URI("http://localhost:5984/_utils/document.html?albums/19640b3ad1fda6c9863575c751063369");
            }catch(URISyntaxException e){

            }

            try{uri2 = new URI("http://google.com");
            //try{uri = new URI("http://localhost:5984/_utils/document.html?albums/19640b3ad1fda6c9863575c751063369");
            }catch(URISyntaxException e){

            }

            //http request


            String requested = uri.toASCIIString();
            com.radmas.example.radgram.app.HttpRequest request = new com.radmas.example.radgram.app.HttpRequest();
            request.setListener(this);
            request.execute(requested);


            //http post
/*
            String toPost = uri2.toASCIIString();
            com.radmas.example.radgram.app.HttpPost request2 = new com.radmas.example.radgram.app.HttpPost();
            request2.setListener(this);
            request2.execute(toPost);

*/
        //new DownloadTask().execute("http://google.com");
        //outputText = (TextView) findViewById(R.id.textView1);
        //String response = HttpRequest.get("http://google.com").body();
        //outputText.setText(response);
        //Create and make HTTP request
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
   public void networkRequestCompleted(String result) {
        if(result == null) {
            return;
        }
       outputText = (TextView) findViewById(R.id.textView1);
       outputText.setText(result);

    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            super.onCreateOptionsMenu(menu);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main4, menu);
            return true; //true -> el menú ya está visible
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_search:
                    Log.i("ActionBar", "Search!");
                    return true;
                case R.id.action_settings:
                    launchActivitySettings();
                    return true;
                case R.id.action_new_message:
                    launchContacts();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        public void launchContacts (){

            Intent i = new Intent (this, Contacts.class);
            startActivity(i);
        }

        public void launchActivitySettings (){
            Intent i = new Intent (this, SettingsActivity.class);
            startActivity(i);
        }
}
