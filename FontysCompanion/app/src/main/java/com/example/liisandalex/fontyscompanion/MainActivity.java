package com.example.liisandalex.fontyscompanion;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TokenFragment.OnFragmentInteractionListener {

    private BottomNavigationView navigation;
    private FragmentTransaction transaction;
    private URL url;
    private URLConnection connection;
    JSONTask thread;
    InputStream is;
    InputStreamReader isr;
    List<Grade> grades;
    Fragment selectedFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeActivity();
                    break;
                case R.id.navigation_schedule:
                    selectedFragment = new ScheduleActivity();
                    break;
                case R.id.navigation_grades:
                    selectedFragment = new GradesActivity();
                    break;
            }

            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        HomeActivity firstpage=new HomeActivity();
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,firstpage);
        transaction.commit();

        try{
            url = new URL("https://api.fhict.nl/grades/me");
            connection = (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFragmentInteraction(String token) {
        thread = new JSONTask();
        thread.execute(token);
    }

    public  class JSONTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params){

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + params[0]);
            grades = new ArrayList<Grade>();

            try {
                connection.connect();
                is = connection.getInputStream();
                isr = new InputStreamReader(is);
                JsonReader jsonReader = new JsonReader(isr);

                if(jsonReader.peek() == JsonToken.BEGIN_ARRAY){
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {

                            jsonReader.beginObject();

                            String subCode = new String();
                            String subject = new String();
                            Double grade = new Double(4);
                            String date = new String();
                            Grade gr;


                            while (jsonReader.hasNext()){
                                String name = new String();
                                name = jsonReader.nextName();

                                if(name.equals("date")){
                                    date=jsonReader.nextString();
                                }
                                else if(name.equals("item")){
                                    subject = jsonReader.nextString();
                                }
                                else if(name.equals("itemCode")){
                                    subCode = jsonReader.nextString();
                                }
                                else if(name.equals("grade")){
                                    grade=Double.parseDouble(jsonReader.nextString()) ;
                                }
                                else jsonReader.skipValue();

                            }

                            gr= new Grade(date,subject,subCode,grade,true);
                            grades.add(gr);

                            jsonReader.endObject();
                        }
                    }
                    jsonReader.endArray();

                    // for(Grade g:grades){
                    //    System.out.println(g.getDate() + g.getSubject() + g.getSubjectCode() + g.getGrade());
                    //}
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return params;}
    }

    public  void OnPostExecute(String[] results) {

        ((GradesActivity)selectedFragment).SetGrades(grades);

    }
}
