package com.gttime.android.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.gttime.android.ui.fragment.MainFragment;
import com.gttime.android.ui.fragment.CourseFragment;
import com.gttime.android.R;
import com.gttime.android.ui.fragment.ScheduleFragment;
import com.gttime.android.ui.fragment.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    public static String userID;

    Fragment mainFragment;
    Fragment courseFragment;
    Fragment scheduleFragment;
    Fragment statisticFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView = this.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                mainFragment = getSupportFragmentManager().findFragmentByTag("mainFragment");
                if(!this.validateFragment(mainFragment)) {
                    mainFragment = new MainFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, mainFragment, "mainFragment").addToBackStack("mainTag").commit();
                return true;

            case R.id.course:
                courseFragment = getSupportFragmentManager().findFragmentByTag("courseFragment");
                if(!this.validateFragment(courseFragment)) {
                    courseFragment = new CourseFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, courseFragment, "courseFragment").addToBackStack("courseTag").commit();
                return true;

            case R.id.schedule:
                scheduleFragment = getSupportFragmentManager().findFragmentByTag("scheduleFragment");
                if(!this.validateFragment(scheduleFragment)) {
                    scheduleFragment = new ScheduleFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, scheduleFragment, "scheduleFragment").addToBackStack("scheduleTag").commit();
                return true;

            case R.id.statistics:
                statisticFragment = getSupportFragmentManager().findFragmentByTag("statisticsFragment");
                if(!this.validateFragment(statisticFragment)) {
                    statisticFragment = new StatisticsFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, statisticFragment, "statisticsFragment").addToBackStack("statisticsTag").commit();
                return true;
        }
        return false;
    }


    public boolean validateFragment(Fragment fragment) {
        return fragment != null;
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
