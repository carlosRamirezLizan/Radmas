package com.radmas.example.radgram.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity implements com.radmas.example.radgram.app.HttpRequestAllDbs.NetworkListener,com.radmas.example.radgram.app.HttpRequest.NetworkListener{
    public String myPhone="0"; //not initialized
    private final static int PHONE = 1;
    public final static String DEFAULT_PHONE_VALUE ="valorpordefecto";
    public TextView noChats;
    public ListView list;
    public RecentChats recentChats = new RecentChats();
    URI uri;
    public TextView outputText;
    private String rev;
    private ArrayList<InfoDb> chats= new ArrayList <InfoDb> ();
    private ArrayList<String> ids = new ArrayList<String>();
    private ArrayList<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        getDocuments();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        myPhone = pref.getString("userPhone", DEFAULT_PHONE_VALUE);
        /*
        String json = pref.getString("Recent Chats", "");
        Gson gson = new Gson();
        recentChats = gson.fromJson(json, RecentChats.class);
        try {
            if (!recentChats.getChats().isEmpty()) {
                noChats = (TextView) findViewById(R.id.textView2);
                //noChats.setVisibility(View.INVISIBLE);
                //MyAdapter2 adapter2 = new MyAdapter2(this, recentChats.getChats());
                list = (ListView) findViewById(R.id.list);
                //list.setAdapter(adapter2);
                //list.setSelection(adapter2.getCount() - 1);
            }
        }catch (Exception e){}
        */
        if(pref.getBoolean("first_run",true)||pref.getString("userPhone",DEFAULT_PHONE_VALUE).equals(DEFAULT_PHONE_VALUE)) {
            Intent i = new Intent(this, MyTelephoneNumber.class);
            startActivityForResult(i, PHONE);
        }
    }

    public void onEventMainThread(ChatsInitialized ev) {
       if (!ev.get_total_rows().equals(0)) {
          noChats = (TextView) findViewById(R.id.textView2);
          noChats.setVisibility(View.INVISIBLE);
          for(InfoDb infoDb: ev.getRows()){
              ids.add(infoDb.getId());
          }
          getDocumentInformation(ids);

       }
    }

    public void onEventMainThread(Conversation ev){
        if(!names.contains(ev.getContact_name())) {
            names.add(ev.getContact_name());
        }
        //falta ultimo mensaje y tiempo
        MyAdapter2 adapter2 = new MyAdapter2(this, names);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter2);
    }

    public void getDocumentInformation(ArrayList<String> ids){
        for(String id: ids){
            try{uri = new URI("http://192.168.1.12:5984/albums/"+id);
            }catch(URISyntaxException e){

            }
            //http request
            String requested = uri.toASCIIString();
            com.radmas.example.radgram.app.HttpRequest request = new com.radmas.example.radgram.app.HttpRequest();
            request.setListener(this);
            request.execute(requested);
        }
    }

    public void getDocuments(){
        try{uri = new URI("http://192.168.1.12:5984/albums/_all_docs");
        }catch(URISyntaxException e){}
        //http request
        String requested = uri.toASCIIString();
        com.radmas.example.radgram.app.HttpRequestAllDbs request = new com.radmas.example.radgram.app.HttpRequestAllDbs();
        request.setListener(this);
        request.execute(requested);
    }

    public void networkRequestCompleted(String result) {
        if(result == null) {
            return;
        }
        rev=result;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                myPhone = i.getExtras().getString("userPhone");
            }
            if (resultCode == RESULT_CANCELED) {
                myPhone=null;
            }
        }
    }//onActivityResult


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true; /** true -> el menú ya está visible */
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
        i.putExtra("myPhone",myPhone);
        Toast.makeText(this, myPhone, Toast.LENGTH_SHORT)
                .show();
        startActivity(i);
    }

    public void launchActivitySettings (){
        Intent i = new Intent (this, MisPreferencias.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}