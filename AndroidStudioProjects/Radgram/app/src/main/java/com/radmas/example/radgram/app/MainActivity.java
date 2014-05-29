package com.radmas.example.radgram.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
public class MainActivity extends ActionBarActivity {
    public String myPhone="0"; //not initialized
    private final static int PHONE = 1;
    private final static String DEFAULT_PHONE_VALUE ="valorpordefecto";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        myPhone = pref.getString("userPhone",DEFAULT_PHONE_VALUE);
        if(pref.getBoolean("first_run",true)||pref.getString("userPhone",DEFAULT_PHONE_VALUE).equals(DEFAULT_PHONE_VALUE)) {
            Intent i = new Intent(this, MyTelephoneNumber.class);
            startActivityForResult(i, PHONE);
        }
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
}
