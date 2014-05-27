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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;


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

    URI uri;
    URI uri2;
    TextView tvIsConnected;
    private String contactName="0";
    private String telephoneContact="0";
    private String myPhone;
    private String rev;
    private ArrayList<Message> arrayMessages= new ArrayList <Message> ();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat);
            tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
            EventBus.getDefault().register(this);
            Bundle extras = this.getIntent().getExtras();
            contactName = extras.getString("user");
            telephoneContact = extras.getString("telephone");

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


//Http request to know the rev value

            try{uri = new URI("http://192.168.1.12:5984/albums/19640b3ad1fda6c9863575c751063369");
            //try{uri = new URI("http://localhost:5984/_utils/document.html?albums/19640b3ad1fda6c9863575c751063369");
            }catch(URISyntaxException e){

            }

            //http request

            String requested = uri.toASCIIString();
            com.radmas.example.radgram.app.HttpRequest request = new com.radmas.example.radgram.app.HttpRequest();
            request.setListener(this);
            request.execute(requested);

    }

    public void onEvent(MyPhone ev) {
            myPhone = ev.phoneNumber;
    }


    public void onEvent(Conversation ev) {
        for(Message message: ev.conversation){
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
            String date = sdf.format(message.getTimeSent());
            messageHistoryText.append(message.getUserTelephone()+" said: " + message.getText() + " at time: " + date+ "\n");
        }
    }

    public ArrayList<Message> getArrayMessages(){
        return arrayMessages;
    }

    public void setArrayMessages(ArrayList<Message> arrayMessages) {
        this.arrayMessages = arrayMessages;
    }

    public void sePulsa(View view){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        String formattedDate2 = df2.format(c.getTime());

        //construir el mensaje con usuario y hora y display

        //messageHistoryText.append(formattedDate2 + ":\n " + "Yo" + ": " + messageText.getText() + "\n");
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
        long unixTime = System.currentTimeMillis();
        Message message = new Message(messageText.getText().toString(),unixTime,myPhone);
        arrayMessages.add(i,message);
        i++;
        Conversation conversation = new Conversation();
        conversation.setConversation(arrayMessages);
        conversation.setMy_phone(myPhone);
        conversation.setFirend_phone(telephoneContact);
        request2.setResultResponse(this.rev);

//      request2.setMessage(message);
        request2.setConversation(conversation);

        request2.setListener(this);
        request2.execute(toPost);

        //http request

        String requested = uri.toASCIIString();
        com.radmas.example.radgram.app.HttpRequest request = new com.radmas.example.radgram.app.HttpRequest();
        request.setListener(this);
        request.execute(requested);

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

       rev= result;
       outputText = (TextView) findViewById(R.id.result_network);
       outputText.setText(rev);
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
