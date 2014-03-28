package com.radmas.example.radgram.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by raddev01 on 28/03/2014.
 */
public class Chat extends ActionBarActivity {
    public TextView outputText;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat);
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

            new DownloadTask().execute("http://google.com");
            //outputText = (TextView) findViewById(R.id.textView1);
            //String response = HttpRequest.get("http://google.com").body();
            //outputText.setText(response);

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            super.onCreateOptionsMenu(menu);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main4, menu);
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
