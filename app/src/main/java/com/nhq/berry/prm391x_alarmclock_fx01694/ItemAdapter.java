package com.nhq.berry.prm391x_alarmclock_fx01694;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

public class ItemAdapter extends BaseAdapter {

    AlarmManager alarm_manager;
    PendingIntent pending_intent;
    final Calendar calendar;
    final Intent my_intent;

    Context myContext;
    int myLayout;
    List<Item> arrayItem;
    Item item;
    public ItemAdapter(Context context, int layout, List<Item> itemList) {
        myContext = context;
        myLayout = layout;
        arrayItem = itemList;

        // initialise alarm manager
        alarm_manager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        // create an instance of a calender
        calendar = Calendar.getInstance();

        // create an intent to the Alarm Receiver class
        my_intent = new Intent(context, Alarm_Receiver.class);
    }

    @Override
    public int getCount() {
        return arrayItem.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);

        item = (Item) this.getItem(position);

        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(item.getTime());

        TextView isAM = (TextView) convertView.findViewById(R.id.ampm);
        if (item.isAM) {
            isAM.setText("AM");
        } else {
            isAM.setText("PM");
        }

        final Button onButton = (Button) convertView.findViewById(R.id.onButton);
        if (item.isOn) {
            onButton.setText("ON");
        } else {
            onButton.setText("OFF");
        }

        // In here we set on click button
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We will call database and change
                // database when user click on button on or off
                // We select the row that is selected to change state
                String query = "SELECT * FROM TimeTable WHERE Id = " + (position + 1);
                Cursor dataTimeAlarm = FragmentMain.database.GetData(query);
                String[] rowChanged = new String[3];
                while (dataTimeAlarm.moveToNext()) {
                    rowChanged[0] = dataTimeAlarm.getString(1);
                    rowChanged[1] = dataTimeAlarm.getString(2);
                    rowChanged[2] = dataTimeAlarm.getString(3);
                    Log.e("ItemAdapter", "rowChanged[0] :" + rowChanged[0]);

                }
                if (rowChanged[0].equals("0")) {
                    rowChanged[0] = "1";
                    setTimeOn(rowChanged[1], rowChanged[2]);
                } else if (rowChanged[0].equals("1")) {
                    rowChanged[0] = "0";
                    setTimeOff();
                } else {
                    rowChanged[0] = "0";
                    Log.e("ItemAdapter", "what's going on here?");
                }

                Log.d("alarmClock", "position: " + position);
                Log.d("alarmClock", "isON " + rowChanged[0]);
                Log.d("alarmClock", "Time: " + rowChanged[1]);
                // Then we update that to database
                String updateQuery = "UPDATE TimeTable SET ISON='" + rowChanged[0] + "'  WHERE Id = " + (position + 1);
                FragmentMain.database.QueryData(updateQuery);

                if (item.isOn) {
                    onButton.setText("OFF");
                    item.isOn = false;
                } else {
                    onButton.setText("ON");
                    item.isOn = true;
                }


            }
        });

        return convertView;
    }

    public void setTimeOn(String timeString, String isAM){

        String[] time = timeString.split(":");
        int hour = Integer.valueOf(time[0]);
        int minute = Integer.valueOf(time[1]);

        if(isAM.equals("0")){
            hour = hour+12;
        }

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);

        // put in extra string into my_intent
        // tell the clock that you pressed the "alarm on" button
        my_intent.putExtra("extra","alarm on");

        // create a pending intent that delays the intent
        // until the specified calendar time
        pending_intent = PendingIntent.getBroadcast(myContext,0,
                my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // set the alarm manager
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pending_intent);

    }

    public void setTimeOff(){
        // cancel the pending intent
        alarm_manager.cancel(pending_intent);

        // put extra string into my_intent
        // tells the clock that you pressed the "alarm off" button
        my_intent.putExtra("extra", "alarm off");

        // stop the ringtone
        myContext.sendBroadcast(my_intent);
    }


}




