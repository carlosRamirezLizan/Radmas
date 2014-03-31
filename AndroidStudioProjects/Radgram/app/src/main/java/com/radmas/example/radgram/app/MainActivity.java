package com.radmas.example.radgram.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

//import com.github.kevinsawicki.http.HttpRequest;

import java.net.URI;
import java.net.URISyntaxException;


public class MainActivity extends ActionBarActivity implements com.radmas.example.radgram.app.HttpRequest.NetworkListener {
    public TextView outputText;
    URI uri;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{uri = new URI("http://docs.couchdb.org/en/latest/intro/tour.html");
        }catch(URISyntaxException e){

        }
        String requested = uri.toASCIIString();

        //Create and make HTTP request
        HttpRequest request = new HttpRequest();
        request.setListener(this);
        request.execute(requested);
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
        startActivity(i);
    }

    public void launchActivitySettings (){
        Intent i = new Intent (this, SettingsActivity.class);
        startActivity(i);
    }

}
