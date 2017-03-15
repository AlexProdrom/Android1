package com.example.alexandliis.implicitintentexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goGoogle(View view) {
        String url="http://google.com";

        //An implicit intent, request a URL
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        this.startActivity(intent);
    }

    public void sendEmail(View view) {
        String[] recipients=new String[]{"friendemail@gmail.com"};

        String subject="Hi, how are you?";

        String content="This is my first email";

        Intent intentEmail=new Intent(Intent.ACTION_SEND,Uri.parse("mailto:"));
        intentEmail.putExtra(Intent.EXTRA_EMAIL,recipients);
        intentEmail.putExtra(Intent.EXTRA_SUBJECT,subject);
        intentEmail.putExtra(Intent.EXTRA_TEXT,content);

        intentEmail.setType("text/plain");

        this.startActivity(Intent.createChooser(intentEmail, "Choose an email client from..."));
    }
}
