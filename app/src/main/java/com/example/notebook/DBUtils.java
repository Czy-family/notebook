package com.example.notebook;


import java.sql.Date;
import java.text.SimpleDateFormat;

public class DBUtils {
    public static final String DATABASE_NAME="Notepad";
    public static final String DATABASE_TABLE="Note1";
    public static final int DATABASE_VERSION=2;
    //数据库表中的列名
    public static final String NOTEPAD_ID="id";
    public static  final String NOTEPAD_CONTENT="content";
    public static final String NOTEPAD_TIME="notetime";
    public static final String AUTHOR="author";
    public static final String TITLE="title";

    //获取当前日期
    public static final String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm;ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);

    }
}
