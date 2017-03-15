package com.example.alexandliis.explicitintentexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText textFirstName;
    private EditText textLastName;
    private TextView textFeedback;

    public static final int MY_REQUEST_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textFirstName= (EditText) findViewById(R.id.text_firstName);
        this.textLastName= (EditText) findViewById(R.id.text_lastName);
        this.textFeedback= (TextView) findViewById(R.id.text_feedback);
    }
//When greeting activity is completed, it sends back a certain feecback
    //Only if you started it with startActivityForResult()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK && requestCode==MY_REQUEST_CODE)
        {
            String feedback=data.getStringExtra("feedback");
            this.textFeedback.setText(feedback);
        }
        else
            this.textFeedback.setText("!?");
    }
//The method is called when the user clicks the button
    public void showGreetings(View view) {
        String firstName=this.textFirstName.getText().toString();
        String lastName=this.textLastName.getText().toString();

        Intent intent=new Intent(this,GreetingActivity.class);
        intent.putExtra("firstName",firstName).putExtra("lastName",lastName);

        //Start Activity and noo feedback==>>> this.startActivity(intent);

        //Start activity with feedback
        this.startActivityForResult(intent,MY_REQUEST_CODE);
    }
}
