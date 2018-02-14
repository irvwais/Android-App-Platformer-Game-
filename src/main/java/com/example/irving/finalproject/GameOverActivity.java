package com.example.irving.finalproject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameOverActivity extends AppCompatActivity {

    private Button buttonPlayAgain, buttonReturn, buttonQuit2;
    private TextView coinScoreText;

    public static int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        SplashActivity.splashmp.start();

        // Make the activity Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the Title Bar
        getSupportActionBar().hide();

        buttonPlayAgain = (Button) findViewById(R.id.buttonPlayAgain);
        buttonReturn = (Button) findViewById(R.id.buttonReturn);
        buttonQuit2 = (Button) findViewById(R.id.buttonQuit2);
        coinScoreText = (TextView) findViewById(R.id.scoreView);

        // Display Coin Count from Game View
        score = getIntent().getIntExtra("SCORE", 0);
        coinScoreText.setText("Coins: " + score);
    }

    // Reset Game View and play again
    public void onClickPA(View view) {
        Intent intentToPlay = new Intent (this, PlayActivity.class);
        startActivity(intentToPlay);
    }

    // Go back to Main Menu
    public void onClickReturn(View view) {
        Intent intentToMenu = new Intent (this, MainMenuActivity.class);
        startActivity(intentToMenu);
    }

    // Quit app and finish all Activities
    public void onClickQuit2(View view) {
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
