package com.winds.dxtapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.winds.dxtapp.models.NewsInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public class NewsDao {
    private MySqliteOpenHelper helper;

    public NewsDao(Context context){
        helper=new MySqliteOpenHelper(context);
    }
    public boolean insert(NewsInfo newsInfo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        try {
            if(newsInfo.getLitpic().equals("http://www.3dmgame.com")){
                newsInfo.setLitpic("none");
            }

            ContentValues values = new ContentValues();
            values.put("id",newsInfo.getId());
            values.put("typeid",newsInfo.getTypeid());
            values.put("typeid2",newsInfo.getTypeid2());
            values.put("sortrank",newsInfo.getSortrank());
            values.put("flag",newsInfo.getFlag());
            values.put("ismake",newsInfo.getIsmake());
            values.put("channel",newsInfo.getChannel());
            values.put("arcrank",newsInfo.getArcrank());
            values.put("click",newsInfo.getClick());
            values.put("money",newsInfo.getMoney());
            values.put("title",newsInfo.getTitle());
            values.put("shorttitle",newsInfo.getShorttitle());
            values.put("color",newsInfo.getColor());
            values.put("writer",newsInfo.getWriter());
            values.put("source",newsInfo.getScores());
            values.put("litpic",newsInfo.getLitpic());
            values.put("pubdate",newsInfo.getPubdate());
            values.put("senddate",newsInfo.getSenddate());
            values.put("mid",newsInfo.getMid());
            values.put("keywords",newsInfo.getKeywords());
            values.put("lastpost",newsInfo.getLastpost());
            values.put("scores",newsInfo.getScores());
            values.put("goodpost",newsInfo.getGoodpost());
            values.put("badpost",newsInfo.getBadpost());
            values.put("voteid",newsInfo.getVoteid());
            values.put("notpost",newsInfo.getNotpost());
            values.put("description",newsInfo.getDescription());
            values.put("filename",newsInfo.getFilename());
            values.put("dutyadmin",newsInfo.getDutyadmin());
            values.put("tackid",newsInfo.getTackid());
            values.put("mtype",newsInfo.getMtype());
            values.put("weight",newsInfo.getWeight());
            values.put("fby_id",newsInfo.getFby_id());
            values.put("game_id",newsInfo.getGame_id());
            values.put("feedback",newsInfo.getFeedback());
            values.put("typedir",newsInfo.getTypedir());
            values.put("typename",newsInfo.getTypename());
            values.put("corank",newsInfo.getCorank());
            values.put("isdefault",newsInfo.getIsdefault());
            values.put("defaultname",newsInfo.getDefaultname());
            values.put("namerule",newsInfo.getNamerule());
            values.put("namerule2",newsInfo.getNamerule2());
            values.put("ispart",newsInfo.getIspart());
            values.put("moresite",newsInfo.getMoresite());
            values.put("ispart",newsInfo.getIspart());
            values.put("moresite",newsInfo.getMoresite());
            values.put("siteurl",newsInfo.getSiteurl());
            values.put("sitepath",newsInfo.getSitepath());
            values.put("arcurl",newsInfo.getArcurl());
            values.put("typeurl",newsInfo.getTypeurl());
            values.put("body",newsInfo.getBody());



            long id=db.insert("info", null, values);
            Log.i("aaa","插入id："+id);
            Log.i("aaa","插入标题："+newsInfo.getShorttitle());
            Log.i("aaa","插入时图片地址:"+newsInfo.getLitpic());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return false;
    }
    public List<HashMap<String,Object>> getAllNewsList() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
        try {
            db = helper.getReadableDatabase();
            cursor=db.query("info",new String[]{"litpic","title"},null,null,null,null,null);
            while (cursor.moveToNext()) {
                HashMap<String,Object> map=new HashMap<>();
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String litpic=cursor.getString(cursor.getColumnIndex("litpic"));

                map.put("litpic",litpic);
                map.put("title",title);
                data.add(map);
            }
            Log.i("aaa",data.toString());
            return data;

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (cursor != null && !cursor.isClosed()) { // &&短路与
                cursor.close();
            }
            if (db != null && db.isOpen()) { // &&短路与
                db.close();
            }
        }

        return null;
    }
    public boolean update(NewsInfo newsInfo) {
        SQLiteDatabase db = null;
        try {
            db = helper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("litpic",newsInfo.getLitpic());

            int rowcount = db.update("info", values, "id=?",
                    new String[] { newsInfo.getId() + "" });
            return rowcount > 0;
        } catch (Exception e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return false;
    }

}
