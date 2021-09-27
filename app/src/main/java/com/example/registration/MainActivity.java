package com.example.registration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Announcement> announcementList;
    private AnnouncementListAdapter adapter;
    private ListView announcementListView;
    public static String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = getIntent().getStringExtra("userID");

        announcementListView = this.findViewById(R.id.AnnouncementList);
        announcementList = new ArrayList<Announcement>();
        adapter = new AnnouncementListAdapter(getApplicationContext(),announcementList);
        announcementListView.setAdapter(adapter);

        final Button courseButton = (Button) findViewById(R.id.CourseButton);
        final Button scheduleButton = (Button) findViewById(R.id.ScheduleButton);
        final Button statisticsButton = (Button) findViewById(R.id.StatisticsButton);
        final LinearLayout announcement = (LinearLayout) findViewById(R.id.Announcement);

        // fragment = portion of UI (mostly layout) that seperately interacts within activity
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcement.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorSecondaryDark));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorSecondary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTraction = fragmentManager.beginTransaction();
                fragmentTraction.replace( R.id.relativeLayout, new CourseFragment()).addToBackStack( "tag" );
                fragmentTraction.commit();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcement.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorSecondaryDark));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorSecondary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTraction = fragmentManager.beginTransaction();
                fragmentTraction.replace( R.id.relativeLayout, new ScheduleFragment()).addToBackStack( "tag" );
                fragmentTraction.commit();
            }
        });
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcement.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorSecondaryDark));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTraction = fragmentManager.beginTransaction();
                fragmentTraction.replace( R.id.relativeLayout, new StatisticsFragment()).addToBackStack( "tag" );
                fragmentTraction.commit();
            }
        });

        new AnnouncementTask().execute();

    }
    class AnnouncementTask extends AsyncTask {
        String address;
        @Override
        protected void onPreExecute() {
            address = "http://ec2-3-222-117-117.compute-1.amazonaws.com/AnnouncementList.php";
        }

        @Override
        protected String doInBackground(Object[] objects) {
            try {
                URL url = new URL(address);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while((temp = buffer.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                buffer.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                String result = (String) o;
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonResponse = jsonObject.getJSONArray("announcement");
                boolean success = jsonObject.getBoolean("success");
                int errorCode = jsonObject.getInt("errorCode");

                int index = 0;
                String announcementContent;
                String announcementTitle;
                String announcementDate;

                while(index < jsonResponse.length()) {
                    JSONObject object = jsonResponse.getJSONObject(index);
                    announcementContent = object.getString("announcementContent");
                    announcementTitle = object.getString("announcementTitle");
                    announcementDate = object.getString("announcementDate");
                    announcementList.add(new Announcement(announcementContent,announcementTitle,announcementDate));
                    index++;
                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }

    /*
        Press back double time and terminate the program.
     */
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    private long lastTimeBackPressed;
    @Override
    public void onBackPressed() {
       if(System.currentTimeMillis() - lastTimeBackPressed <500) {
           finish();
            return;
        }
        //Toast.makeText(this,"Press back to home screen.",Toast.LENGTH_SHORT).show();

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

        lastTimeBackPressed = System.currentTimeMillis();
    }


}
