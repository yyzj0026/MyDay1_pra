package com.winds.dxtapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.winds.dxtapp.cache.MemoryCache;
import com.winds.dxtapp.cache.SdCardCache;
import com.winds.dxtapp.cache.WebCache;
import com.winds.dxtapp.dao.NewsDao;
import com.winds.dxtapp.models.NewsInfo;
import com.winds.dxtapp.utils.JsonUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DownloadService extends Service {
    private Handler myHandler;

    @Override
    public void onCreate() {
        super.onCreate();


        myHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext());
                builder.setTicker("通知");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("页面加载");
                builder.setContentText("页面已加载完成");

                Log.i("aaa","lalala");
                if(msg.what==1){
                    manager.notify(1,builder.build());
                }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    class MyBinder extends Binder{
        public void downInfo(final String urlPath){
            new Thread(){
                public void run(){
                    List<NewsInfo> list=new ArrayList<>();
                    byte[] info= WebCache.getWebCache(urlPath);
                    if(info!=null){
                        try {
                            String strJson=new String(info,"utf-8");
                            list= JsonUtils.getJson(strJson);

                            if(list!=null){
                                NewsDao dao=new NewsDao(getApplicationContext());
                                for(NewsInfo info1:list){
                                    dao.insert(info1);
                                    savePicOk(info1,dao);
                                }
                                Message msg=myHandler.obtainMessage();
                                myHandler.sendEmptyMessage(1);
                            }else{
                                Log.i("aaa","Json解析有问题");
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Log.i("aaa","网络读数据有问题");
                    }
                }

            }.start();
        }
        public boolean savePicOk(NewsInfo newsInfo,NewsDao dao){
            boolean isOk=false;

            SdCardCache sdCardCache=new SdCardCache();
            MemoryCache memoryCache=new MemoryCache();
//            String dir="picCache";
            String picPath=newsInfo.getLitpic();


            if(!picPath.equals("none")){
                String[] str=picPath.split("/");
                String fileName=str[str.length-1];

                byte[] b= WebCache.getWebCache(picPath);
                if(b!=null){
                    Bitmap bitmap1=ImgChange.getImg(b,60,60);
                    sdCardCache.saveBitmapToSdCard(bitmap1,picPath);
//                    memoryCache.addPicToLruCache(picPath,bitmap1);  //这一句没用，打开第二个Activity时页面跳转，缓存里清掉了
                }

                String newPath= sdCardCache.getSdCardPath().getAbsolutePath()+File.separator+fileName;
                newsInfo.setLitpic(newPath);
                dao.update(newsInfo);

                isOk=true;
            }


            return isOk;
        }

    }
}
