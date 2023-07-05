package com.example.notebook;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotepadAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<NotepadBean> list;
    public NotepadAdapter(Context context, List<NotepadBean> list){
        this.layoutInflater=LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    //获取Item条目的总数
    public int getCount(){
        return list==null? 0: list.size();
    }
    @Override
    //根据position（位置）获取某个Item的对象
    public Object getItem(int position){
        return list.get(position);
    }
    @Override
    //根据position（位置）获取某个Item的id
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //通过inflate()方法加载Item布局，并将获取的数据显示到对应的控件上，并判断旧视图是否为空，若为空，则创建一个ViewHolder对象
        //通过set.Tag()方法将该对象添加到convertView中进行缓存，否则把获取的旧视图进行缓存
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.activity_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        NotepadBean noteInfo=(NotepadBean) getItem(position);
        viewHolder.tvNoteoadContent.setText(noteInfo.getNotepadContent());
        viewHolder.tvNotepadTime.setText(noteInfo.getNotepadTime());
        return convertView;
    }
    class ViewHolder{
        TextView tvNoteoadContent;
        TextView tvNotepadTime;
        public ViewHolder(View view){
            tvNoteoadContent=(TextView) view.findViewById(R.id.ietm_content);
            tvNotepadTime=(TextView) view.findViewById((R.id.item_time));
        }
    }

}
