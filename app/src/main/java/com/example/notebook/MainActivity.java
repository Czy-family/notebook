package com.example.notebook;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<NotepadBean> list;
    SQLiteHelper mSQLiteHelper;
    NotepadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //用于显示记录的列表
        listView = findViewById(R.id.listview);
        ImageView add= findViewById(R.id.add);
        //设置添加按钮事件
        add.setOnClickListener(v -> {
            //使用显示Intent指定要跳转的目标Activity，并通过startActivityForResult()方法开启目标Activity
            Intent intent=new Intent(MainActivity.this,RecordActivity.class);
            startActivityForResult(intent,1);
        });
        initData();

    }

    protected void initData() {
        //创建数据库
        mSQLiteHelper = new SQLiteHelper(this);
        showQueryData();
        //处理查看记事本详细信息
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //通过get方法获取对应的Item数据
            NotepadBean notepadBean=list.get(position);
            //通过putExtra()方法封装到Intent对象中
            Intent intent=new Intent(MainActivity.this,RecordActivity.class);
            intent.putExtra("id",notepadBean.getId());
            intent.putExtra("time",notepadBean.getNotepadTime());
            intent.putExtra("content",notepadBean.getNotepadContent());
            intent.putExtra("author",notepadBean.getName());
            intent.putExtra("title",notepadBean.getTitle());
            MainActivity.this.startActivityForResult(intent,1);
        });
        //删除记事本记录
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog dialog = null;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("是否删除此纪录？");

// 创建并设置删除按钮
                Button deleteButton = new Button(MainActivity.this);
                deleteButton.setText("删除");
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                buttonParams.setMargins(0, 0, 0, 10); // 设置按钮与消息的间距
                deleteButton.setLayoutParams(buttonParams);
                AlertDialog finalDialog = dialog;
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 获取Item对象
                        NotepadBean notepadBean = list.get(position);
                        if (mSQLiteHelper.deleteData(notepadBean.getId())) {
                            // 删除对应的Item
                            list.remove(position);
                            // 更新记事本界面
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }
                        finalDialog.dismiss(); // 关闭对话框
                    }
                });
                builder.setView(deleteButton); // 将删除按钮设置到对话框中

// 创建并设置取消按钮
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }
    private void showQueryData(){
        if(list!=null){
            list.clear();
        }
        //从数据库中查询数据
        list=mSQLiteHelper.query();
        adapter=new NotepadAdapter(this,list);
        listView.setAdapter(adapter);
    }
    @Override
    //重写，当关闭添加记录界面时，程序回调该方法
    // 并在该方法中调用showQueryData()方法重新获取数据库中保存的记录数据并显示到记录列表中
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==2){
            showQueryData();
        }
    }
}