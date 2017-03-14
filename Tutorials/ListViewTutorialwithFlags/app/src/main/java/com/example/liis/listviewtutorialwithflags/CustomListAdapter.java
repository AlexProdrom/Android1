package com.example.liis.listviewtutorialwithflags;

import android.content.Context;
import  android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import  java.util.List;
import java.util.Objects;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Liis on 14/03/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private List<Country> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public  CustomListAdapter(Context acontext, List<Country> listdata){
        this.context = acontext;
        this.listData = listdata;
        layoutInflater = LayoutInflater.from(acontext);
    }

    @Override
    public int getCount(){
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
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.flagView = (ImageView)convertView.findViewById(R.id.imageView_flag);
            holder.countryNameView=(TextView)convertView.findViewById(R.id.textView_countryName);
            holder.populationView=(TextView)convertView.findViewById(R.id.textView_population);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }

        Country country = this.listData.get(position);
        holder.countryNameView.setText(country.getCountryName());
        holder.populationView.setText("Population: " + country.getPopulation());

        int imageId = this.getMipmapResIdByName(country.getFlagName());
        holder.flagView.setImageResource(imageId);
        return  convertView;

    }

    public  int getMipmapResIdByName(String resname){
        String pkgName = context.getPackageName();
        int resID=context.getResources().getIdentifier(resname, "mipmap", pkgName);
        Log.i("Listview","Res name: " + resname + "--> ResID = " + resID);
        return resID;
    }

    static  class ViewHolder {
        ImageView flagView;
        TextView countryNameView;
        TextView populationView;
    }
}
