package com.example.notebook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DBUtils.DATABASE_NAME,null,DBUtils.DATABASE_VERSION);
        sqLiteDatabase=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DBUtils.DATABASE_TABLE+"("+DBUtils.NOTEPAD_ID+" integer primary key autoincrement,"+DBUtils.NOTEPAD_CONTENT+" text,"+DBUtils.NOTEPAD_TIME+ " text," +DBUtils.AUTHOR + " text,"+DBUtils.TITLE +" text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertData(String userContent,String userTime,String author,String title){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT,userContent);
        contentValues.put(DBUtils.NOTEPAD_TIME,userTime);
        contentValues.put(DBUtils.AUTHOR,author);
        contentValues.put(DBUtils.TITLE,title);
        return sqLiteDatabase.insert(DBUtils.DATABASE_TABLE,null,contentValues)>0;
    }
    //删除数据
    public boolean deleteData(String id){
        String sql=DBUtils.NOTEPAD_ID+"=?";
        String [] contentValuesArray=new String[]{String.valueOf(id)};
        return sqLiteDatabase.delete(DBUtils.DATABASE_TABLE,sql,contentValuesArray)>0;
    }
    //修改数据
    public boolean updateData(String id,String content,String userYear,String author,String title){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT,content);
        contentValues.put(DBUtils.NOTEPAD_TIME,userYear);
        contentValues.put(DBUtils.AUTHOR,author);
        contentValues.put(DBUtils.TITLE,title);
        String sql=DBUtils.NOTEPAD_ID+"=?";
        String [] strings=new String[] {id};
        return sqLiteDatabase.update(DBUtils.DATABASE_TABLE,contentValues,sql,strings)>0;
    }
    //查询数据
    public List<NotepadBean> query(){
        List<NotepadBean> list =new ArrayList<NotepadBean>();
        Cursor cursor=sqLiteDatabase.query(DBUtils.DATABASE_TABLE,null,null,null,null,null,DBUtils.NOTEPAD_ID+" desc");
        if(cursor!=null){
            while(cursor.moveToNext()){
                NotepadBean noteInfo=new NotepadBean();
                @SuppressLint("range")
                String id=String.valueOf(cursor.getInt(cursor.getColumnIndex(DBUtils.NOTEPAD_ID)));
                @SuppressLint("range")
                String content=cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_CONTENT));
                @SuppressLint("range")
                String time=cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_TIME));

                @SuppressLint("Range")
                String author=cursor.getString(cursor.getColumnIndex(DBUtils.AUTHOR));

                @SuppressLint("Range")
                String title=cursor.getString(cursor.getColumnIndex(DBUtils.TITLE));
                noteInfo.setId(id);
                noteInfo.setNotepadContent(content);
                noteInfo.setNotepadTime(time);
                noteInfo.setName(author);
                noteInfo.setTitle(title);
                list.add(noteInfo);
            }
            cursor.close();
        }
        return list;
    }

}
