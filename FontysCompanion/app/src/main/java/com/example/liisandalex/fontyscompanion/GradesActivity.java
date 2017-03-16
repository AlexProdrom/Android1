package com.example.liisandalex.fontyscompanion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GradesActivity extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_grades,container,false);

        List<Grade> grades = GetGrades();

        ListView lv = (ListView)view.findViewById(R.id.listView);
        lv.setAdapter(new CustomListAdapter(getActivity().getApplicationContext(), grades));



       /* String first = "a";
        String second = "b";
        String third = "c";
        String firstm = "a";
        String secondm = "b";
        String thirdm = "c";
        String firstn = "a";
        String secondn = "b";
        String thirdn = "c";
        String firstmm = "a";
        String secondmm = "b";
        String thirdmm = "c";
        String firstnn = "a";
        String secondnn = "b";
        String thirdnn = "c";

        String[] letters = new String[]{first,second,third,firstm,secondm,thirdm,firstn,secondn,thirdn,firstmm,secondmm,thirdmm,firstnn,secondnn,thirdnn};

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                 android.R.layout.simple_list_item_checked, letters);
        lv.setAdapter(adapter);
        */

        return view;
    }


    public List<Grade> GetGrades(){
        List<Grade> list = new ArrayList<>();

        Grade sd2 = new Grade("2017/02/08", "System Development", "SD2", 8.0, true);
        Grade popd3 = new Grade("2017/02/18", "Personal dev", "POPD3", 8.0, true);
        Grade prop = new Grade("2016/02/08", "Project", "ProP", 7.0, true);

        list.add(sd2);
        list.add(popd3);
        list.add(prop);
        return list;
    }

}
