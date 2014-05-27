package com.radmas.example.radgram.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

import de.greenrobot.event.EventBus;

/**
 * Created by raddev01 on 04/04/2014.
 */
public class MyTelephoneNumber extends Activity {

    public  String userPhone;
    private EditText telephone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telephone_number);
        telephone_num = (EditText) findViewById(R.id.telephone);


    }

    public void onClickTelephoneNumber(View view){
        userPhone =  telephone_num.getText().toString();
        MyPhone myPhone = new MyPhone(userPhone);
        EventBus.getDefault().post(myPhone);
        Intent i = new Intent ();
        i.putExtra("userPhone", userPhone);
        setResult(RESULT_OK, i);
        finish();

    }

}
