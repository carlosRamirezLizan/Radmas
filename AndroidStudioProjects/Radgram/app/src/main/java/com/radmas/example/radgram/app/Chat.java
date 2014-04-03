package com.radmas.example.radgram.app;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by raddev01 on 28/03/2014.
 */
public class Chat extends ActionBarActivity implements com.radmas.example.radgram.app.HttpPost.NetworkListener, com.radmas.example.radgram.app.HttpRequest.NetworkListener{

    public TextView outputText;
    public String[] resultados=new String[2];
    public int i=0;
    private EditText messageText;
    private TextView messageHistoryText;
    private Button sendMessageButton;
    private FriendInfo friend = new FriendInfo();

    URI uri;
    URI uri2;
    TextView tvIsConnected;
    private String contactName="0";
    private String telephoneContact="0";
    private String myPhone="0";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat);
            tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

            Bundle extras = this.getIntent().getExtras();
            contactName = extras.getString("user");
            telephoneContact = extras.getString("telephone");
            myPhone = extras.getString("myPhone");

            messageHistoryText = (TextView) findViewById(R.id.messageHistory);
            messageText = (EditText) findViewById(R.id.message);
            messageText.requestFocus();
            sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
            setTitle("Messaging with " + contactName);

        // check if you are connected or not
            if(isConnected()){
                tvIsConnected.setText("Online");
            }
            else{
                tvIsConnected.setText("Offline");
            }
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);


//            try{uri = new URI("http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369");
//            //try{uri = new URI("http://localhost:5984/_utils/document.html?albums/19640b3ad1fda6c9863575c751063369");
//            }catch(URISyntaxException e){
//
//            }
//
//            try{uri2 = new URI("http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369");
//            //try{uri = new URI("http://localhost:5984/_utils/document.html?albums/19640b3ad1fda6c9863575c751063369");
//            }catch(URISyntaxException e){
//
//            }
//
//            //http request
//            //http post
//
//            String toPost = uri2.toASCIIString();
//            com.radmas.example.radgram.app.HttpPost request2 = new com.radmas.example.radgram.app.HttpPost();
//            request2.setListener(this);
//            request2.execute(toPost);

    }

    public void sePulsa(View view){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        String formattedDate2 = df2.format(c.getTime());

        //construir el mensaje con usuario y hora y display
        messageHistoryText.setText(formattedDate2 + ":\n " + "Yo" + ": " + messageText.getText() + "\n");
        try{uri = new URI("http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369");
            //try{uri = new URI("http://localhost:5984/_utils/document.html?albums/19640b3ad1fda6c9863575c751063369");
        }catch(URISyntaxException e){

        }

        try{uri2 = new URI("http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369");
            //try{uri = new URI("http://localhost:5984/_utils/document.html?albums/19640b3ad1fda6c9863575c751063369");
        }catch(URISyntaxException e){

        }

        //http request

/*
            String requested = uri.toASCIIString();
            com.radmas.example.radgram.app.HttpRequest request = new com.radmas.example.radgram.app.HttpRequest();
            request.setListener(this);
            request.execute(requested);

*/
        //http post

        String toPost = uri2.toASCIIString();
        com.radmas.example.radgram.app.HttpPost request2 = new com.radmas.example.radgram.app.HttpPost();
        request2.setContactPhone(telephoneContact);
        try {
            request2.setMessage(messageText.getText().toString());
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        request2.setUserPhone(myPhone);
        request2.setListener(this);
        request2.execute(toPost);


        String resultResponse = ((TextView)findViewById(R.id.result_network)).getText().toString();
        String revResponse = resultResponse.substring(58);
        if(revResponse!=null) {
            request2.setResultResponse(revResponse);
        }
        //new line y guardar los mensajes
        //enviar info en json a la base de datos
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
       outputText = (TextView) findViewById(R.id.result_network);
       outputText.setText(result);

      /* Gson gson = new Gson();
       Conversation conversation = gson.fromJson(result, Conversation.class);
       outputText.setText(conversation.toString());
       */

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
