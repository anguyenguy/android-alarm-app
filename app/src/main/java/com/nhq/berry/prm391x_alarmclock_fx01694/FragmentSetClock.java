package com.nhq.berry.prm391x_alarmclock_fx01694;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;


public class FragmentSetClock extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View RootView = inflater.inflate(R.layout.fragment_set_clock, container, false);
        Button addButton = (Button) RootView.findViewById(R.id.add_time);
        final TimePicker timePicker = (TimePicker) RootView.findViewById(R.id.timePicker);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On Click Add Button
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String hourString;
                String minuteString;
                int isAM =1;
                if(hour>=12){
                    hour=hour-12;
                    isAM=0;
                }
                hourString=String.valueOf(hour);
                minuteString=String.valueOf(minute);
                if(minute<10){
                    minuteString="0"+minuteString;
                }
                String query = "INSERT INTO TimeTable VALUES(null,0,'"+hourString+":"+minuteString+"',"+isAM+")";
                FragmentMain.database.QueryData(query);

                // Back to Main Fragment
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new FragmentMain();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.commit();

            }
        });
        return RootView;

    }


}
