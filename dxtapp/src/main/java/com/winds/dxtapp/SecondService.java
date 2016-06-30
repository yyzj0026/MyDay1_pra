package com.winds.dxtapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.winds.dxtapp.dao.NewsDao;

public class SecondService extends Service {
    private Handler myHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        myHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(msg.what==2){
                    Log.i("aaa","更新适配器");
                    SecondActivity.adapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends Binder{
        public void setData(){

            new Thread(){
                public void run(){
                    NewsDao dao=new NewsDao(getApplicationContext());
                    SecondActivity.list.clear();
                    SecondActivity.list.addAll(dao.getAllNewsList());

                    Log.i("aaa","second:"+SecondActivity.list.toString());
                    Message msg=myHandler.obtainMessage();
                    myHandler.sendEmptyMessage(2);

                }
            }.start();

        }
    }
}
