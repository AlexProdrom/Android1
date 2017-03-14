package com.example.liis.androidfragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import  android.support.annotation.Nullable;
import android.view.LayoutInflater;
import  android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Liis on 14/03/2017.
 */

public class TopFragment extends Fragment {

    private EditText inputTopImageText;
    private  EditText inputBottomImageText;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //read xml file and return view object

        View view = inflater.inflate(R.layout.activity_top, container, false);

        inputTopImageText = (EditText)view.findViewById(R.id.input_top1_text);
        inputBottomImageText = (EditText)view.findViewById(R.id.input_top2_text);

        Button applyButton = (Button)view.findViewById(R.id.btn_apply);


        applyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public  void onClick(View v){
                applyText();
            }
        });

        return  view;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        if(context instanceof MainActivity){
            this.mainActivity = (MainActivity) context;
        }
    }

    private  void applyText() {
        String topText = this.inputTopImageText.getText().toString();
        String bottomText = this.inputBottomImageText.getText().toString();
        this.mainActivity.showText(topText, bottomText);
    }
}
