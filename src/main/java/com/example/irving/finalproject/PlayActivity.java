package com.example.irving.finalproject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

public class PlayActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashActivity.splashmp.start();

        // Make the activity Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set view of play activity to Game View Canvas
        setContentView(new GameView(this));

        // Hide the Title Bar
        getSupportActionBar().hide();

    }

    @Override
    protected void onPause() {
        SplashActivity.splashmp.pause();

        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                SplashActivity.splashmp.stop();
            }
        }
        super.onPause();
        this.finish();
    }

    @Override
    public void onResume () {
        super.onResume();
        SplashActivity.splashmp.start();
    }
}
