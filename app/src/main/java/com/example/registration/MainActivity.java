package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    public static String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = getIntent().getStringExtra("userID");

        final BottomNavigationView bottomNavigationView = this.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, new MainFragment()).commit();
                return true;

            case R.id.course:
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, new CourseFragment()).commit();
                return true;

            case R.id.schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, new ScheduleFragment()).commit();
                return true;

            case R.id.statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, new StatisticsFragment()).commit();
                return true;
        }
        return false;
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
