package com.example.liisandalex.fontyscompanion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class GradeListAdapter extends BaseAdapter {
    private List<Grade> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public GradeListAdapter(Context aContext, List<Grade> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_grade_layout, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.textView_date);
            holder.subject = (TextView) convertView.findViewById(R.id.textView_subject);
            holder.grade = (TextView) convertView.findViewById(R.id.textView_grade);
            holder.state = (TextView) convertView.findViewById(R.id.textView_passed);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Grade grade = this.listData.get(position);
        holder.date.setText("Date: " + grade.getDate().substring(0, 10));
        holder.subject.setText("Subject: " + grade.getSubject());
        holder.grade.setText("Grade: " + grade.getGrade());
        holder.state.setText("Passed: " + grade.isPassed());

        return convertView;
    }

    static class ViewHolder {
        TextView date;
        TextView subject;
        TextView grade;
        TextView state;
    }
}
