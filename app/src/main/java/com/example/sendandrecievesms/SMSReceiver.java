package com.example.sendandrecievesms;
import android.os.Bundle;
import android.content.*;
import android.telephony.*;
import android.widget.*;
public class SMSReceiver extends BroadcastReceiver{

    public void onReceive(Context context,Intent intent){

        Bundle bundle = intent.getExtras();

        Object[] smsObj = (Object[]) bundle.get("pdus");

        for(Object obj:smsObj){
            SmsMessage  message = SmsMessage.createFromPdu((byte[])obj);

            String mobile = message.getDisplayOriginatingAddress();
            String msg = message.getDisplayMessageBody();

            Toast.makeText(context,"Mobile NO:"+mobile+"Message:"+msg,Toast.LENGTH_LONG).show();
        }

    }

}