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

    private JSONTaskGrades gradesThread;
    private JSONTaskSchedule scheduleThread;
    private JSONTaskAccount accountThread;
    private AsyncTask oldtask;

    private Fragment homeFragment, gradesFragment, scheduleFragment;

    private String token;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    oldtask.cancel(true);
                    changeFragment(homeFragment);
                    accountThread = new JSONTaskAccount();
                    accountThread.execute(token);
                    oldtask = accountThread;
                    break;
                case R.id.navigation_schedule:
                    oldtask.cancel(true);
                    changeFragment(scheduleFragment);
                    scheduleThread = new JSONTaskSchedule();
                    scheduleThread.execute(token);
                    oldtask = scheduleThread;
                    break;
                case R.id.navigation_grades:
                    oldtask.cancel(true);
                    changeFragment(gradesFragment);
                    gradesThread = new JSONTaskGrades();
                    gradesThread.execute(token);
                    oldtask = gradesThread;
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeActivity();
        scheduleFragment = new ScheduleActivity();
        gradesFragment = new GradesActivity();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void changeFragment(Fragment f) {
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, f);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(String token) {
        this.token = token;

        changeFragment(homeFragment);
        accountThread = new JSONTaskAccount();
        accountThread.execute(token);
        oldtask = accountThread;
    }

    public class JSONTaskAccount extends AsyncTask<String, Void, Account> {
        TextView tv;
        Account myAccount;

        @Override
        protected Account doInBackground(String... params) {
            try {

                URL url = new URL("https://api.fhict.nl/people/me");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + params[0]);

                myAccount = new Account();

                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                JsonReader jsonReader = new JsonReader(isr);

                if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String name = jsonReader.nextName();
                        if (name.equals("id")) {
                            myAccount.setId(jsonReader.nextString());
                        } else if (name.equals("givenName")) {
                            myAccount.setGiveName(jsonReader.nextString());
                        } else if (name.equals("surName")) {
                            myAccount.setSurName(jsonReader.nextString());
                        } else if (name.equals("mail")) {
                            myAccount.setMail(jsonReader.nextString());
                        } else if (name.equals("personalTitle")) {
                            myAccount.setTheclass(jsonReader.nextString());
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                }
                jsonReader.endObject();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return myAccount;
        }

        protected void onPostExecute(Account result) {
            tv = (TextView) findViewById(R.id.tv_pcn);
            tv.setText("PCN: " + myAccount.getId());

            tv = (TextView) findViewById(R.id.tv_gn);
            tv.setText("Firstname: " + myAccount.getGiveName());

            tv = (TextView) findViewById(R.id.tv_sur);
            tv.setText("Lastname: " + myAccount.getSurName());

            tv = (TextView) findViewById(R.id.tv_mail);
            tv.setText("Email: " + myAccount.getMail());

            tv = (TextView) findViewById(R.id.tv_class);
            tv.setText("Class :" + myAccount.getTheclass());
        }
    }

    public class JSONTaskGrades extends AsyncTask<String, Void, List<Grade>> {

        @Override
        protected List<Grade> doInBackground(String... params) {
            List<Grade> grades = new ArrayList<Grade>();

            try {
                URL url = new URL("https://api.fhict.nl/grades/me");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + params[0]);
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
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
                                    date = (jsonReader.nextString()).substring(0, 10);
                                } else if (name.equals("item")) {
                                    subject = jsonReader.nextString();
                                } else if (name.equals("itemCode")) {
                                    subCode = jsonReader.nextString();
                                } else if (name.equals("grade")) {
                                    grade = Double.parseDouble(jsonReader.nextString());
                                } else if (name.equals("passed")) {
                                    passed = jsonReader.nextBoolean();
                                } else jsonReader.skipValue();

                            }

                            gr = new Grade(date, subject, subCode, grade, passed);
                            grades.add(gr);

                            jsonReader.endObject();
                        }
                    }
                    jsonReader.endArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return grades;
        }

        protected void onPostExecute(List<Grade> gradeslist) {
            ListView lv = (ListView) findViewById(R.id.listViewGrades);
            lv.setAdapter(new CustomListAdapter(MainActivity.this, gradeslist));
        }
    }

    public class JSONTaskSchedule extends AsyncTask<String, Void, List<Schedule>> {

        @Override
        protected List<Schedule> doInBackground(String... params) {
            List<Schedule> schedule = new ArrayList<>();

            try {
                URL url = new URL("https://api.fhict.nl/schedule/me");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + params[0]);
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                JsonReader jsonReader = new JsonReader(isr);

                if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String name1 = jsonReader.nextName();
                        if (name1.equals("data") && jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()) {
                                if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                                    jsonReader.beginObject();

                                    String name2 = new String();
                                    String room = new String();
                                    String subject = new String();
                                    String teacher = new String();
                                    String start = new String();
                                    String end = new String();

                                    Schedule sc;
                                    while (jsonReader.hasNext()) {
                                        name2 = jsonReader.nextName();
                                        if (name2.equals("room")) {
                                            room = jsonReader.nextString();
                                        } else if (name2.equals("subject")) {
                                            subject = jsonReader.nextString();
                                        } else if (name2.equals("teacherAbbreviation")) {
                                            teacher = jsonReader.nextString();
                                        } else if (name2.equals("start")) {
                                            String value = jsonReader.nextString();
                                            start = value.substring(0, 10) + " " + value.substring(11, 19);
                                        } else if (name2.equals("end")) {
                                            String value = jsonReader.nextString();
                                            end = value.substring(0, 10) + " " + value.substring(11, 19);
                                        } else jsonReader.skipValue();

                                    }

                                    sc = new Schedule(room, subject, teacher, start, end);
                                    schedule.add(sc);

                                    jsonReader.endObject();
                                }
                            }
                            jsonReader.endArray();
                        } else jsonReader.skipValue();
                    }
                    jsonReader.endObject();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return schedule;
        }

        @Override
        protected void onPostExecute(List<Schedule> schedules) {
            ListView lv = (ListView) findViewById(R.id.listViewSchedule);
            lv.setAdapter(new ScheduleListAdapter(MainActivity.this, schedules));
        }
    }
}
