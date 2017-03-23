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
    private JSONTaskAccount accountThread;

    private Fragment homeFragment, gradesFragment,scheduleFragment;

    private String token;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(homeFragment);
                    accountThread = new JSONTaskAccount();
                    accountThread.execute(token);
                    break;
                case R.id.navigation_schedule:
                    changeFragment(scheduleFragment);
                    break;
                case R.id.navigation_grades:
                    changeFragment(gradesFragment);
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

        homeFragment = new HomeActivity();
        scheduleFragment = new ScheduleActivity();
        gradesFragment = new GradesActivity();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void changeFragment(Fragment f)
    {
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
                            myAccount.id = jsonReader.nextString();
                        } else if (name.equals("givenName")) {
                            myAccount.giveName = jsonReader.nextString();
                        } else if (name.equals("surName")) {
                            myAccount.surName = jsonReader.nextString();
                        } else if (name.equals("mail")) {
                            myAccount.mail = jsonReader.nextString();
                        } else if (name.equals("personalTitle")) {
                            myAccount.theclass = jsonReader.nextString();
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
            tv.setText("PCN: "+myAccount.id);

            tv = (TextView) findViewById(R.id.tv_gn);
            tv.setText("Firstname: "+myAccount.giveName);

            tv = (TextView) findViewById(R.id.tv_sur);
            tv.setText("Lastname: "+myAccount.surName);

            tv = (TextView) findViewById(R.id.tv_mail);
            tv.setText("Email: "+myAccount.mail);

            tv = (TextView) findViewById(R.id.tv_class);
            tv.setText("Class :"+myAccount.theclass);
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
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(new CustomListAdapter(MainActivity.this, gradeslist));
        }
    }

}
