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
import android.widget.ListAdapter;
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

    JSONTaskGrades gradesThread;
    InputStream is;
    InputStreamReader isr;
    List<Grade> grades;
    Fragment selectedFragment = null;
    private String token;

    private HomeActivity homeactivity;
    private GradesActivity gradesactivity;
    private ScheduleActivity scheduleacivity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = homeactivity;

                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, selectedFragment);
                    transaction.commit();
                    break;
                case R.id.navigation_schedule:
                    selectedFragment = scheduleacivity;

                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, selectedFragment);
                    transaction.commit();
                    break;
                case R.id.navigation_grades:
                    selectedFragment = gradesactivity;

                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, selectedFragment);
                    transaction.commit();

                    gradesThread = new JSONTaskGrades();
                    gradesThread.execute(token);
                    break;
            }


            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeactivity = new HomeActivity();
        scheduleacivity = new ScheduleActivity();
        gradesactivity = new GradesActivity();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(String token) {
//        transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.content, gradesactivity);
//        transaction.commit();
        this.token = token;
//        gradesThread = new JSONTaskGrades();
//        gradesThread.execute(token);
    }

    public class JSONTaskGrades extends AsyncTask<String, Void, List<Grade>> {

        @Override
        protected List<Grade> doInBackground(String... params) {


            grades = new ArrayList<Grade>();

            try {
                URL url;
                URLConnection connection;
                url = new URL("https://api.fhict.nl/grades/me");
                connection = url.openConnection();

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + params[0]);
                connection.connect();
                is = connection.getInputStream();
                isr = new InputStreamReader(is);
                JsonReader jsonReader = new JsonReader(isr);

                if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {

                            jsonReader.beginObject();

                            String subCode = new String();
                            String subject = new String();
                            Double grade = new Double(4);
                            String date = new String();
                            boolean passed = true;
                            Grade gr;


                            while (jsonReader.hasNext()) {
                                String name = new String();
                                name = jsonReader.nextName();

                                if (name.equals("date")) {
                                    date = (jsonReader.nextString()).substring(0,10);
                                } else if (name.equals("item")) {
                                    subject = jsonReader.nextString();
                                } else if (name.equals("itemCode")) {
                                    subCode = jsonReader.nextString();
                                } else if (name.equals("grade")) {
                                    grade = Double.parseDouble(jsonReader.nextString());
                                } else if (name.equals("passed")){
                                    passed = jsonReader.nextBoolean();
                                }
                                else jsonReader.skipValue();

                            }

                            gr = new Grade(date, subject, subCode, grade, passed);
                            grades.add(gr);

                            jsonReader.endObject();
                        }
                    }
                    jsonReader.endArray();

                    //to test
                     for(Grade g:grades){
                        System.out.println(g.getDate() + g.getSubject() + g.getSubjectCode() + g.getGrade() + g.isPassed());

                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return grades;
        }

        protected void onPostExecute(List<Grade> gradeslist) {
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(new CustomListAdapter(MainActivity.this, gradeslist));
        }
    }

}
