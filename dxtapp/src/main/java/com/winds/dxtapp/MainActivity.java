package com.winds.dxtapp;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private DownloadService.MyBinder myBinder;
    private String urlPath="http://www.3dmgame.com/sitemap/api.php?row=10&typeid=1&paging=1&page=1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent1=new Intent();
        intent1.setAction("downloadservice");

        ServiceConnection connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder= (DownloadService.MyBinder) iBinder;
                myBinder.downInfo(urlPath);
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        bindService(intent1,connection, Context.BIND_AUTO_CREATE);
    }

    public void clickButton(View view){
        Intent intent2=new Intent(this,SecondActivity.class);
        startActivity(intent2);
    }

}
