package com.geekykelvin.taskplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private final int mSplashTime = 5000; // time to display the splash screen in ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /****** Create Thread that will sleep for 5 seconds****/
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(mSplashTime);

                } catch (Exception e) {

                } finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    // Remove Activity
                    finish();
                }
            }
        };
        // Start Thread
        splashThread.start();
    }
}
