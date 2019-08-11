package com.nhq.berry.prm391x_alarmclock_fx01694;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("LocalService", "Received start id " + startId + ": " + intent);

        // fetch the extra string values
        String state = intent.getExtras().getString("extra");

        Log.e("Ringtone state:extra is", state);

        // This converts the extra strings from the intent
        // to start IDs, value 0 or 1

        switch (state){
            case "alarm on":
                startId=1;
                break;
            case "alarm off":
                startId=0;
                break;
            default:
        }

        // if else statements

        // if there is no music playing, and the user pressed "alarm on"
        // music should start playing
        if(!this.isRunning&& startId==1){
            // create an instance of media player
            media_song = MediaPlayer.create(this, R.raw.nhacchuong);
            media_song.start();
            this.isRunning= true;
        }
        // if there is music playing, and the user pressed "alarm off"
        // music should stop playing
        else if(this.isRunning&& startId==0){
            media_song.stop();
            media_song.reset();
            this.isRunning= false;
        }
        // there are if the user presses random buttons
        // just to bug-proof the app
        // if there is no music playing, and the user pressed "alarm off"
        // do nothing
        else if(!this.isRunning&& startId==0){
            Log.e("No music playing","do not thing");
        }
        // if there is music playing and the user pressed "alarm on"
        // do nothing
        else if(this.isRunning&& startId==1){
            Log.e("Music is playing","do not thing");
        }
        // can't thing of anything else, just to catch the odd event
        else{
            Log.e("Opps","something go wrong!");
        }




        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Toast.makeText(this, "On destroy called!", Toast.LENGTH_SHORT).show();
    }




}
