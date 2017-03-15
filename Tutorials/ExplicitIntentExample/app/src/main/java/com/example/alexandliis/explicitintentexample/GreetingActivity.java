package com.example.alexandliis.explicitintentexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GreetingActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        //Intent is received
        Intent intent =this.getIntent();

        this.firstName=intent.getStringExtra("firstName");
        this.lastName=intent.getStringExtra("lastName");

        String greeting="Hello "+firstName+" "+lastName;

        TextView textGreeting= (TextView) findViewById(R.id.text_greeting);
        textGreeting.setText(greeting);
    }

    @Override
    public void finish() {
        Intent data=new Intent();
        data.putExtra("feedback","I'm "+this.firstName +", Hi!");

        //Activity finished ok, return the data
        this.setResult(Activity.RESULT_OK,data);
        super.finish();
    }

    public void backClicked(View view) {
        //Calling onBackPressed()
        this.onBackPressed();
    }
}
