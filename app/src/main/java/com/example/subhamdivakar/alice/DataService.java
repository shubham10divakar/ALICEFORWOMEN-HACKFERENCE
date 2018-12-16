package com.example.subhamdivakar.alice;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.example.subhamdivakar.alice.Bean.ContactSaving;
import com.example.subhamdivakar.alice.UTILS.SqDB;

import static com.example.subhamdivakar.alice.UTILS.SqDB.contact_INFO_TABLE_COLUMN_p1;
import static com.example.subhamdivakar.alice.UTILS.SqDB.contact_INFO_TABLE_COLUMN_p2;
import static com.example.subhamdivakar.alice.UTILS.SqDB.contact_INFO_TABLE_COLUMN_p3;
import static com.example.subhamdivakar.alice.UTILS.SqDB.contact_INFO_TABLE_COLUMN_p4;
import static com.example.subhamdivakar.alice.UTILS.SqDB.contact_INFO_TABLE_COLUMN_p5;

/**
 * Created by Subham Divakar on 3/11/2018.
 */

public class DataService extends Service
{

    SqDB obj=new SqDB(this);
    GPSTracker gps;
    double latitude,longitude;
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendLocation();
        stopSelf();
        return START_STICKY;
    }

    private void sendLocation()
    {
        ContactSaving obj1=new ContactSaving();
        Cursor res = obj.getAllData();
        if (res.moveToNext()) {
            String ph1 = String.valueOf( res.getString(res.getColumnIndex(contact_INFO_TABLE_COLUMN_p1)));
            String ph2 = String.valueOf(res.getString(res.getColumnIndex(contact_INFO_TABLE_COLUMN_p2)));
            String ph3 = String.valueOf(res.getString(res.getColumnIndex(contact_INFO_TABLE_COLUMN_p3)));
            String ph4 = String.valueOf(res.getString(res.getColumnIndex(contact_INFO_TABLE_COLUMN_p4)));
            String ph5 = String.valueOf(res.getString(res.getColumnIndex(contact_INFO_TABLE_COLUMN_p5)));

            gps = new GPSTracker(DataService.this);

            // check if GPS enabled
            if(gps.canGetLocation())
            {

                 latitude = gps.getLatitude();
                 longitude = gps.getLongitude();

            }

            String sms = "HELP ME,I AM IN DANGER.   My location is  http://maps.google.com/?q="+latitude+","+longitude;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(ph1, null, sms, null, null);
            smsManager.sendTextMessage(ph2, null, sms, null, null);
            smsManager.sendTextMessage(ph3, null, sms, null, null);
            smsManager.sendTextMessage(ph4, null, sms, null, null);
            smsManager.sendTextMessage(ph5, null, sms, null, null);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
    }
}
