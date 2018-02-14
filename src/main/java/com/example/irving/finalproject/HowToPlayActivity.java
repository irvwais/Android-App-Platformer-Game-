package com.example.irving.finalproject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

public class HowToPlayActivity extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        SplashActivity.splashmp.start();

        // Make the activity Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the Title Bar
        getSupportActionBar().hide();

        backButton = (Button) findViewById(R.id.buttonBack);
    }

    // Go back to Main Menu
    public void onClickBack(View view) {
        Intent intentToBack = new Intent (this, MainMenuActivity.class);
        startActivity(intentToBack);
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
    }

    @Override
    public void onResume () {
        super.onResume();
        SplashActivity.splashmp.start();
    }
}
