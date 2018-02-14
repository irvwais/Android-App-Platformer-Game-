package com.example.irving.finalproject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    private Button buttonPlay, buttonQuit, buttonHTP, buttonHS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        SplashActivity.splashmp.start();

        // Make the activity Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the Title Bar
        getSupportActionBar().hide();

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonHTP = (Button) findViewById(R.id.buttonHTP);
        buttonHS = (Button) findViewById(R.id.buttonHS);
        buttonQuit = (Button) findViewById(R.id.buttonQuit2);
    }

    // Go to Game View via Play Activity
    public void onClickPlay(View view) {
        Intent intentToGame = new Intent (this, PlayActivity.class);
        startActivity(intentToGame);
    }

    // Go to High Score Activity
    public void onClickHS(View view) {
        Intent intentToHS = new Intent (this, HighScoresActivity.class);
        startActivity(intentToHS);
    }

    // Go to How to Play Activity
    public void onClickHTP(View view) {
        Intent intentToHTP = new Intent (this, HowToPlayActivity.class);
        startActivity(intentToHTP);
    }

    // Quit and Finish all Activities
    public void onClickQuit(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
