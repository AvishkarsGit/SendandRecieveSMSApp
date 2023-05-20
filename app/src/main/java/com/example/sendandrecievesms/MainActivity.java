package com.example.sendandrecievesms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.*;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import static android.Manifest.permission.*;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText mno,msg;
    Button send;

    TextView read;

    private static final int REQ_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mno= findViewById(R.id.edtMno);
        msg = findViewById(R.id.edtMsg);
        read = findViewById(R.id.receiveSMS);
        send = findViewById(R.id.btnSend);


        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (checkPer()){
                    sendSMS();
                }else {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{SEND_SMS,READ_SMS,RECEIVE_SMS},REQ_CODE);
                }

            }
        });
    }
    private void sendSMS(){


        String mobileNo = mno.getText().toString();
        String message = msg.getText().toString();

        if (TextUtils.isEmpty(mobileNo)){
            Toast.makeText(this, "Enter Mobile No ..", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Enter Message...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(mobileNo)||TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Enter Mobile No and Message...", Toast.LENGTH_SHORT).show();
        }else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mobileNo,null,message,null,null);

            Toast.makeText(this, "SMS send successfully", Toast.LENGTH_SHORT).show();
        }


    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if (requestCode == REQ_CODE){
            if (grantResults.length>0)
            {
                int s = grantResults[0];
                int r = grantResults[1];
                int R = grantResults[2];

                boolean checks = s == PackageManager.PERMISSION_GRANTED && r == PackageManager.PERMISSION_GRANTED && R == PackageManager.PERMISSION_GRANTED;
                if (checks){
                    sendSMS();
                }else {
                    Toast.makeText(this, "Permission Denied!!", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    private Boolean checkPer() {
        int sendSMS = ActivityCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int readSMS = ActivityCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        int receiveSMS = ActivityCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);

        return sendSMS == PackageManager.PERMISSION_GRANTED && readSMS == PackageManager.PERMISSION_GRANTED && receiveSMS == PackageManager.PERMISSION_GRANTED;
    }


}