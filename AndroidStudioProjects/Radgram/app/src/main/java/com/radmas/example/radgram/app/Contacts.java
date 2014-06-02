package com.radmas.example.radgram.app;


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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by raddev01 on 19/03/14.
 */
public class Contacts extends ListActivity {
    public MyAdapter adapter;
    private String[][] nombres;
    private String[] telefonos;
    private int imagenes;
    private int sizeCount;
    private String myPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fetchContacts();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        imagenes= R.drawable.ic_launcher;
        //imagenes[1]=R.drawable.ic_launcher;
        adapter = new MyAdapter(this,nombres,telefonos,imagenes);
        Bundle extras = this.getIntent().getExtras();
        myPhone= extras.getString("myPhone");
        setListAdapter(adapter);

        ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                String selected = ((TextView) view.findViewById(R.id.nombre)).getText().toString();
                String telephone_selected = ((TextView) view.findViewById(R.id.telefono)).getText().toString();
                Toast toast=Toast.makeText(getApplicationContext(), selected+telephone_selected, Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.putExtra("user",selected);
                intent.putExtra("telephone",telephone_selected);
                intent.putExtra("myPhone",myPhone);
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
        Intent i = new Intent (this, MisPreferencias.class);
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
        nombres= new String[sizeCount][sizeCount];
        telefonos =new String[sizeCount];
        //imagenes = new int[sizeCount];
        int j = 0;
        //StringBuffer output= new StringBuffer();
        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
                if (hasPhoneNumber > 0) {
                    ///output.append("\n First Name:" + name);
                    nombres[j][0]=name;
                    j++;
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            nombres[j-1][1] = phoneNumber;
                    }
                    phoneCursor.close();
                }
            }
        }
    }
}