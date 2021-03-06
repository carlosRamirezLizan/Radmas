package com.radmas.example.radgram.app;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;


/**
 * Created by raddev01 on 28/03/2014.
 */
public class Chat extends ActionBarActivity implements com.radmas.example.radgram.app.HttpPost.NetworkListener, com.radmas.example.radgram.app.HttpRequest.NetworkListener{

    public TextView outputText;
    private EditText messageText;
    private ListView messageHistoryList;
    private Button sendMessageButton;

    TimerTask doAsynchronousTask;
    final Handler handler = new Handler();
    Timer timer = new Timer();

    URI uri;
    URI uri2;
    TextView tvIsConnected;
    private String contactName;
    private String telephoneContact="0";
    private String myPhone;
    private String rev;
    private Database database;
    private ArrayList<Message> arrayMessagesAll= new ArrayList <Message> ();
    private ArrayList<Message> arrayMessagesThisChat =new ArrayList <Message> ();
    private RecentChats recentChats =new RecentChats();
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat);
            tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
            EventBus.getDefault().register(this);
            Bundle extras = this.getIntent().getExtras();
            contactName = extras.getString("user");
            telephoneContact = extras.getString("telephone");
            myPhone = extras.getString("myPhone");
            if(Integer.parseInt(myPhone)<Integer.parseInt(telephoneContact)) {
                database = new Database(myPhone, telephoneContact, myPhone + "_" + telephoneContact);
            }else{
                database = new Database(myPhone,telephoneContact,telephoneContact+"_"+myPhone);
            }
            messageHistoryList = (ListView) findViewById(R.id.messageHistoryList);
            messageText = (EditText) findViewById(R.id.message);
            messageText.requestFocus();
            sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
            setTitle("Messaging with " + contactName);
            recentChats.getChats().add(position,contactName);
            position++;
            Gson gson = new Gson();
            String json = gson.toJson(recentChats);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Recent Chats", json);
            editor.commit();

            // check if you are connected or not
            if(isConnected()){
                tvIsConnected.setText("Online");
            }
            else{
                tvIsConnected.setText("Offline");
            }
//            try {
//                ActionBar actionBar = getActionBar();
//                actionBar.setDisplayHomeAsUpEnabled(true);
//            }catch(Exception e){}

            //http post para crear el documento en base de datos en caso de que no exista
            try {
                uri2 = new URI("http://192.168.1.12:5984/albums/"+database.getDatabaseId());
            } catch (URISyntaxException e) {

            }
            String toPost = uri2.toASCIIString();
            com.radmas.example.radgram.app.HttpPost request2 = new com.radmas.example.radgram.app.HttpPost();
            request2.setDatabase(database);
            request2.setPostToCreateDatabase(true);
            request2.setListener(this);
            request2.execute(toPost);

            //Periodic http request to know the rev value
            doAsynchronousTask =  new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            refresh();
                            Log.w("Radgram", "Refreshing");
                            Log.w("List Messages",arrayMessagesAll.toString());
                        }
                    });
                }
            };timer.schedule(doAsynchronousTask, 0, 10000); //refresh every 10 seconds
    }

    public void refresh (){
        try{uri = new URI("http://192.168.1.12:5984/albums/"+database.getDatabaseId());
        }catch(URISyntaxException e){

        }
        //http request
        String requested = uri.toASCIIString();
        com.radmas.example.radgram.app.HttpRequest request = new com.radmas.example.radgram.app.HttpRequest();
        request.setListener(this);
        request.execute(requested);
    }

    public void onEventMainThread(Conversation ev) {
        try {
            if ((ev.getFirend_phone().equals(telephoneContact)||ev.getFirend_phone().equals(myPhone)) && (ev.getMy_phone().equals(myPhone))||ev.getMy_phone().equals(telephoneContact)) {
                setArrayMessagesThisChat(ev.conversation);
                for (Message message : ev.conversation) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
                    String date = sdf.format(message.getTimeSent());
                    //comparar tiempos
                    MyAdapter3 adapter3 = new MyAdapter3(this,ev,myPhone,contactName);
                    messageHistoryList.setAdapter(adapter3);
                    messageHistoryList.setSelection(adapter3.getCount() - 1);
                }
            }
        }catch (Exception e){

        }
    }

    public void sePulsa(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        String formattedDate2 = df2.format(c.getTime());

        try {
            uri = new URI("http://192.168.1.12:5984/albums/"+database.getDatabaseId());
        } catch (URISyntaxException e) {

        }

        try {
            uri2 = new URI("http://192.168.1.12:5984/albums/"+database.getDatabaseId());
        } catch (URISyntaxException e) {

        }
        //http post
        String toPost = uri2.toASCIIString();
        com.radmas.example.radgram.app.HttpPost request2 = new com.radmas.example.radgram.app.HttpPost();
        long unixTime = System.currentTimeMillis();
        if (messageText.getText().length() != 0) {
            Message message = new Message(messageText.getText().toString(), unixTime, myPhone);
            messageText.setText("");
            Conversation conversation = new Conversation();
            arrayMessagesAll.add(message);
            //for(Message mess: arrayMessagesAll){
              //  if(mess.getUserTelephone().equals(myPhone)){
            arrayMessagesThisChat.add(message);
                //}
            //}
            conversation.setConversation(arrayMessagesThisChat);
            conversation.setMy_phone(myPhone);
            conversation.setFirend_phone(telephoneContact);
            conversation.setContact_name(contactName);
            request2.setResultResponse(this.rev);
            request2.setConversation(conversation);
            request2.setDatabase(database);
            request2.setListener(this);
            request2.execute(toPost);

            //http request
            String requested = uri.toASCIIString();
            com.radmas.example.radgram.app.HttpRequest request = new com.radmas.example.radgram.app.HttpRequest();
            request.setListener(this);
            request.execute(requested);
        } else {
            Toast.makeText(this, "Introduce un texto", Toast.LENGTH_SHORT).show();
        }
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

    public ArrayList<Message> getArrayMessagesAll(){
            return arrayMessagesAll;
    }

    public void setArrayMessagesAll(ArrayList<Message> arrayMessages) {
            this.arrayMessagesAll = arrayMessages;
    }

    public void setArrayMessagesThisChat(ArrayList<Message> arrayMessagesThisChat) {
        this.arrayMessagesThisChat = arrayMessagesThisChat;
    }

    public void launchContacts (){

            Intent i = new Intent (this, Contacts.class);
            i.putExtra("myPhone",myPhone);
            startActivity(i);
    }

    public void launchActivitySettings (){
            Intent i = new Intent (this, MisPreferencias.class);
            startActivity(i);
    }

    @Override
    protected void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
    }
}
