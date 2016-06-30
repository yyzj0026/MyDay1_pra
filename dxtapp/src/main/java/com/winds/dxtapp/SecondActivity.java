package com.winds.dxtapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private ListView listView;
    private SecondService.MyBinder myBinder;
    public static List<HashMap<String,Object>> list;
    public static MyAdapter adapter;
    private CacheManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        manager=new CacheManager();
        listView= (ListView) this.findViewById(R.id.listview);
        list=new ArrayList<>();
        adapter=new MyAdapter(list,this,manager);
        listView.setAdapter(adapter);

        Intent intent=new Intent();
        intent.setAction("secondservice");

        ServiceConnection connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder= (SecondService.MyBinder) iBinder;
                myBinder.setData();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        manager.clear();
    }
}
