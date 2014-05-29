package com.radmas.example.radgram.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by raddev01 on 04/04/2014.
 */
public class MyTelephoneNumber extends Activity {

    private EditText telephone_num;
    private MyPhone myPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telephone_number);
        telephone_num = (EditText) findViewById(R.id.telephone);
    }

    public void onClickTelephoneNumber(View view){
        myPhone = new MyPhone();
        myPhone.setPhoneNumber(telephone_num.getText().toString());
        //EventBus.getDefault().post(myPhone);
        if(telephone_num.getText().length()!=0) {
            Intent i = getIntent();
            i.putExtra("userPhone", myPhone.phoneNumber);

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("first_run",false);
            editor.putString("userPhone",myPhone.phoneNumber);
            editor.commit();

            Toast.makeText(this, myPhone.phoneNumber, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, i);
            finish();
        } else{
            Toast.makeText(this,"No hay texto, introduce tu teléfono",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
