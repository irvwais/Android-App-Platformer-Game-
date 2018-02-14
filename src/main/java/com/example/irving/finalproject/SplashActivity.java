package com.example.irving.finalproject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    // Initialize Variable for background music call
    public static MediaPlayer splashmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Make the activity Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the Title Bar
        getSupportActionBar().hide();

        // Grab background music file
        splashmp = MediaPlayer.create(this, R.raw.ehonda);
        splashmp.start();
        splashmp.setLooping(true);



        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intentToMain = new Intent(SplashActivity.this, MainMenuActivity.class);
                    startActivity(intentToMain);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        splashmp.pause();

        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                splashmp.stop();
            }
        }
        super.onPause();
    }

    @Override
    public void onResume () {
        super.onResume();
        splashmp.start();
    }
}

