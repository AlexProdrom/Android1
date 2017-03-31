package com.example.liisandalex.fontyscompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by prodromalex on 3/30/2017.
 */

public class ScheduleListAdapter extends BaseAdapter{

        private List<Schedule> listData;
        private LayoutInflater layoutInflater;
        private Context context;

        public  ScheduleListAdapter(Context acontext, List<Schedule> listdata){
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
                convertView = layoutInflater.inflate(R.layout.schedule_list_item, null);
                holder = new ViewHolder();

                holder.subject=(TextView)convertView.findViewById(R.id.tv_subject);
                holder.room=(TextView)convertView.findViewById(R.id.tv_room);
                holder.teacher=(TextView)convertView.findViewById(R.id.tv_teacher);
                holder.start=(TextView)convertView.findViewById(R.id.tv_start);
                holder.end=(TextView)convertView.findViewById(R.id.tv_end);
                convertView.setTag(holder);
            }
            else {
                holder= (ViewHolder)convertView.getTag();
            }

            Schedule sc = this.listData.get(position);
            holder.subject.setText(sc.getSubject());
            holder.room.setText(sc.getRoom());
            holder.teacher.setText(sc.getTeacher());
            holder.start.setText(sc.getStart());
            holder.end.setText(sc.getEnd());

            return convertView;

        }

        class ViewHolder {
            TextView subject;
            TextView room;
            TextView teacher;
            TextView start;
            TextView end;
        }

}

