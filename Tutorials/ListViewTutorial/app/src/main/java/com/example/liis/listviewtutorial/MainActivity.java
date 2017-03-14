package com.example.liis.listviewtutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView)findViewById(R.id.listView);

        UserAccount tom = new UserAccount("Tom", "Admin");
        UserAccount jerry = new UserAccount("Jerry", "User");
        UserAccount donald = new UserAccount("Donald", "Guest", false);

        UserAccount[] users = new UserAccount[]{tom,jerry,donald};

        ArrayAdapter<UserAccount> arrayAdapter = new
                ArrayAdapter<UserAccount>(this, android.R.layout.simple_list_item_checked, users);

        listview.setAdapter(arrayAdapter);
    }

}
