package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView delete,note_save,note_back;
    TextView noteName,note_time;
    EditText content;

    EditText author;
    EditText title;
    SQLiteHelper mSQLiteHelper;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        //通过findViewById获取界面控件
        note_back=(ImageView) findViewById(R.id.note_back);
        note_save=(ImageView) findViewById(R.id.note_save);
        delete=(ImageView) findViewById(R.id.delete);
        noteName=(TextView) findViewById(R.id.note_name);
        note_time=(TextView) findViewById(R.id.tv_time);
        content=(EditText) findViewById(R.id.note_content);
        author=(EditText) findViewById(R.id.note_author);
        title=(EditText) findViewById(R.id.note_title);
        //设置启动器
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        //初始化
        initData();
    }
    protected void initData(){
        //创建数据库
        mSQLiteHelper=new SQLiteHelper(this);
        noteName.setText("添加记录");
        //接收记事本传来的消息
        //getIntent()方法获取Intent对象
        Intent intent=getIntent();
        if(intent!=null){
            //获取传递的记录id
            id=intent.getStringExtra("id");
            if(id!=null){
                noteName.setText("修改记录");
                content.setText(intent.getStringExtra("content"));
                author.setText(intent.getStringExtra("author"));
                title.setText(intent.getStringExtra("title"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        final int getId=v.getId();
        if (getId == R.id.note_back) {
            finish();
        } else if (getId == R.id.delete) {
            content.setText("");
            author.setText("");
            title.setText("");
        } else if (getId == R.id.note_save) {
            String noteContent = content.getText().toString().trim();
            String notetitle = title.getText().toString().trim();
            String noteAuthor = author.getText().toString().trim();
            if (id != null) {
                if (noteContent.length() > 0) {
                    if (mSQLiteHelper.updateData(id, noteContent, DBUtils.getTime(),noteAuthor,notetitle)) {
                        showToast("修改成功");
                        setResult(2);
                        finish();
                    } else {
                        showToast("保存失败");
                    }
                } else {
                    showToast("保存内容不能为空");
                }
            } else {
                if (noteContent.length() > 0) {
                    if (mSQLiteHelper.insertData(noteContent, DBUtils.getTime(),noteAuthor,notetitle)) {
                        showToast("保存成功");
                        setResult(2);
                        finish();
                    } else {
                        showToast("保存失败");
                    }
                } else {
                    showToast("修改内容不能为空");
                }
            }
        }

    }

    public void showToast(String Message){
        Toast.makeText(RecordActivity.this,Message,Toast.LENGTH_SHORT).show();
    }
}
