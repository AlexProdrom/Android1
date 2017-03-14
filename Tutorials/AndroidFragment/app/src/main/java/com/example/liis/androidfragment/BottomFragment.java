package com.example.liis.androidfragment;

import android.app.Fragment;
import android.os.Bundle;
import  android.support.annotation.Nullable;
import android.view.LayoutInflater;
import  android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Liis on 14/03/2017.
 */

public class BottomFragment extends Fragment {

    private TextView topText;
    private TextView bottomText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //read xml file and return view object

        View view = inflater.inflate(R.layout.activity_bottom, container, false);

        topText = (TextView)view.findViewById(R.id.input_bottom1_text);
        bottomText =(TextView) view.findViewById(R.id.input_bottom2_text);

        return  view;
    }

    public  void showText(String topImageText, String bottomImageText){
        topText.setText(topImageText);
        bottomText.setText(bottomImageText);
    }
}
