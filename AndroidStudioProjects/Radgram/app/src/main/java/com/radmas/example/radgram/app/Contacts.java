package com.radmas.example.radgram.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by raddev01 on 19/03/14.
 */
public class Contacts extends ListActivity {
    //public TextView outputText;
    public MyAdapter adapter;
    private String[] nombres;
    private String[] telefonos;
    private int[] imagenes;
    private int sizeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ///nombres= new String[sizeCount];
        ///telefonos =new String[sizeCount];
        ///imagenes = new int[sizeCount];
        fetchContacts();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ///nombres[0]="Carlos";
        ///nombres[1]="Juan";
        ///telefonos[0]="639915743";
        ///telefonos[1]="623392236";
        imagenes[0]=R.drawable.ic_sent;
        imagenes[1]=R.drawable.ic_sent;
        adapter = new MyAdapter(this,nombres,telefonos,imagenes);
        //outputText = (TextView) findViewById(R.id.textView1);
        //fetchContacts();

        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
             //Toast.makeText(getApplicationContext(), "myPos " + i, Toast.LENGTH_LONG).show();
             Intent intent = new Intent(getApplicationContext(), Chat.class);
             startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main3, menu);
        return true; // true -> el menú ya está visible
    }

    public void launchSettings (){
        Intent i = new Intent (this, SettingsActivity.class);
        startActivity(i);
    }
    public void launchChats(){
        Intent i = new Intent (this, MainActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Log.i("ActionBar", "Search!");
                return true;
            case R.id.action_settings:
                launchSettings();
                return true;
            case R.id.action_chats:
                launchChats();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchContacts(){
        String phoneNumber;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        //Uri info
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        //cursor
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
        sizeCount=cursor.getCount();
        nombres= new String[sizeCount];
        telefonos =new String[sizeCount];
        imagenes = new int[sizeCount];
        int j = 0;
        int k=0;
        //StringBuffer output= new StringBuffer();
        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
                if (hasPhoneNumber > 0) {
                    ///output.append("\n First Name:" + name);
                    nombres[j]=name;
                    j++;
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        telefonos[k]=phoneNumber;
                        k++;
                       ///output.append("\n Phone number:" + phoneNumber);
                    }
                    phoneCursor.close();
                }
                ///output.append("\n");
            }
            ///outputText.setText(output);
        }
    }
}