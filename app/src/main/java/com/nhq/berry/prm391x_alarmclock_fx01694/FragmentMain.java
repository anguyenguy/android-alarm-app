package com.nhq.berry.prm391x_alarmclock_fx01694;

import android.app.AlarmManager;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;

public class FragmentMain extends Fragment {

    // We prepare adapter, arraylist and database for the app
    ItemAdapter adapter;
    ArrayList<Item> arrayItem;
    public static Database database;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Take the rootview to find another elements in this view
        View RootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Create new array list that contain items from item class
        arrayItem = new ArrayList<>();

        // Call addArrayItem function that
        // take data from sqlite
        addArrayItem(RootView);

        // We take adapter and push data into it
        adapter = new ItemAdapter(getActivity(), R.layout.row_item, arrayItem);
        ListView listItem = (ListView) RootView.findViewById(R.id.listview);
        listItem.setAdapter(adapter);
        return RootView;

    }

    private void addArrayItem(View RootView){

        // create database
        database = new Database(RootView.getContext(), "alarm.sqlite", null, 1);

        // create table in database
        // row in table is formated:
        // 2     0      "10:24       1
        // ID    ISON   ALARMTIME    ISAM
        // 0--> false     1-->true
        database.QueryData("CREATE TABLE IF NOT EXISTS TimeTable(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ISON INTEGER," +
                "ALARMTIME VARCHAR(100)," +
                "ISAM INTEGER)");

        Cursor dataTimeAlarm = database.GetData("SELECT * FROM TimeTable");
        while(dataTimeAlarm.moveToNext()){
            boolean isON=false, isAM=false;
            if(dataTimeAlarm.getInt(1)==0){isON=false;}
            else if(dataTimeAlarm.getInt(1)==1){isON=true;}
            else{Log.e("FragmentMain"," Something wrong in a row from database");}
            if(dataTimeAlarm.getInt(3)==0){isAM=false;}
            else if(dataTimeAlarm.getInt(3)==1){isAM=true;}
            else{Log.e("FragmentMain"," Something wrong in a row from database");}
            arrayItem.add(new Item(isON, dataTimeAlarm.getString(2),isAM));
        }
    }

}
