package com.example.liisandalex.fontyscompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Liis on 16/03/2017.
 */

public class CustomListAdapter extends BaseAdapter{

    private List<Grade> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public  CustomListAdapter(Context acontext, List<Grade> listdata){
        this.context = acontext;
        this.listData = listdata;
        layoutInflater = LayoutInflater.from(acontext);
    }

    @Override
    public  int getCount(){
        return  listData.size();
    }

    @Override
    public Object getItem(int position){
        return listData.get(position);
    }

    @Override
    public  long getItemId(int position){
        return  position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.grade_list_item, null);
            holder = new ViewHolder();
            holder.date=(TextView)convertView.findViewById(R.id.text_date);
            holder.grade=(TextView)convertView.findViewById(R.id.text_grade);
            holder.subject=(TextView)convertView.findViewById(R.id.text_subject);
            holder.subjectcode=(TextView)convertView.findViewById(R.id.text_subjectCode);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }

        Grade gr = this.listData.get(position);
        holder.subjectcode.setText(gr.getSubjectCode());
        holder.subject.setText(gr.getSubject());
        holder.grade.setText(gr.getGrade().toString());
        holder.date.setText(gr.getDate());

        return convertView;

    }

    static class ViewHolder {
        TextView subject;
        TextView subjectcode;
        TextView grade;
        TextView date;
    }

}
