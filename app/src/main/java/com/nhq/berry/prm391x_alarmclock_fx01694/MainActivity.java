package com.nhq.berry.prm391x_alarmclock_fx01694;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Begin app, the app automatically changes to FragmentMain class
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMain fragmentMain = new FragmentMain();
        fragmentTransaction.replace(R.id.frame_container, fragmentMain);
        fragmentTransaction.commit();
    }

    public void changeFragment(View view){
        // When user click on button to set clock and back to list alarm
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        switch (view.getId()){
            // When user click to add alarm
            // App should change to clock set screen
            case R.id.add_button:
                fragment = new FragmentSetClock();
                break;
            // When user click to back alarm
            // App should come back to main screen
            case R.id.back_button:
                fragment = new FragmentMain();
                break;
        }
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
