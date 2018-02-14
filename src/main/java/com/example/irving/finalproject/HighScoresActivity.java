package com.example.irving.finalproject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    GameOverActivity gameover;

    private Button backButton2;
    private TextView score1, score2, score3, score4, score5;

    // Initialize Array for High Scores
    public static int[] highscores = new int[] { 0, 0, 0, 0, 0 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        SplashActivity.splashmp.start();

        // Make the activity Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the Title Bar
        getSupportActionBar().hide();

        backButton2 = (Button) findViewById(R.id.buttonBack2);
        score1 = (TextView) findViewById(R.id.textViewScore1);
        score2 = (TextView) findViewById(R.id.textViewScore2);
        score3 = (TextView) findViewById(R.id.textViewScore3);
        score4 = (TextView) findViewById(R.id.textViewScore4);
        score5 = (TextView) findViewById(R.id.textViewScore5);

        SharedPreferences set = getSharedPreferences("ALL_HIGH_SCORES", Context.MODE_PRIVATE);
        // Grab stored High Scores
        int highScore1 = set.getInt("HIGH_SCORE1", 0);
        int highScore2 = set.getInt("HIGH_SCORE2", 0);
        int highScore3 = set.getInt("HIGH_SCORE3", 0);
        int highScore4 = set.getInt("HIGH_SCORE4", 0);
        int highScore5 = set.getInt("HIGH_SCORE5", 0);

        // Save High Scores
        SharedPreferences.Editor editor = set.edit();

        // if Game score is higher then the stored High score
        if (highscores[0] > highScore1) {
            // Set all high Scores as scores that are gotten during game time
            score1.setText("1.               " + highscores[0]);
            editor.putInt("HIGH_SCORE1", highscores[0]).commit();
        }
        // Else display stored High Score
        else {
            // Set All High Scores as saved High Scores
            score1.setText("1.               " + highScore1);
        }
        if (highscores[1] > highScore2) {
            score2.setText("2.               " + highscores[1]);
            editor.putInt("HIGH_SCORE2", highscores[1]).commit();
        }
        else {
            score2.setText("2.               " + highScore2);
        }
        if (highscores[2] > highScore3) {
            score3.setText("3.               " + highscores[2]);
            editor.putInt("HIGH_SCORE3", highscores[2]).commit();
        }
        else {
            score3.setText("3.               " + highScore3);
        }
        if (highscores[3] > highScore4) {
            score4.setText("4.               " + highscores[3]);
            editor.putInt("HIGH_SCORE4", highscores[3]).commit();
        }
        else {
            score4.setText("4.               " + highScore4);
        }
        if (highscores[4] > highScore5) {
            score5.setText("5.               " + highscores[4]);
            editor.putInt("HIGH_SCORE5", highscores[4]).commit();
        }
        else {
            score5.setText("5.               " + highScore5);
        }
    }

    // Add each coin score from Game View if it is higher then the High Score
    // in the first list and then move previous highscore down the list if it is
    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--)
                    highscores[j] = highscores[j - 1];
                highscores[i] = score;
                break;
            }
        }
    }

    // Go back to Main Menu
    public void onClickBack2(View view) {
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
