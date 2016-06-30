package com.winds.dxtapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public class MyAdapter extends BaseAdapter{
    private List<HashMap<String,Object>> list;
    private Context context;
    private CacheManager manager;

    public MyAdapter(List<HashMap<String, Object>> list, Context context,CacheManager manager) {
        this.list = list;
        this.context = context;
        this.manager=manager;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=View.inflate(context,R.layout.item,null);
            holder=new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.imageView);
            holder.tv= (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        HashMap<String,Object> map=list.get(i);

        holder.tv.setText(map.get("title").toString());

        String path=map.get("litpic").toString();
        Log.i("aaa","图片地址："+path);
        if(!path.equals("none")){

            manager.getCache(path,holder.iv);
//          byte[] b= SdCardUtils.readFile(path);
//          Bitmap bitmap= BitmapFactory.decodeByteArray(b,0,b.length);
//          holder.iv.setImageBitmap(bitmap);

        }else{
            holder.iv.setImageResource(R.mipmap.ic_launcher);
        }

        return convertView;
    }

    class ViewHolder{
        ImageView iv=null;
        TextView tv=null;
    }
}
