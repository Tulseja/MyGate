package com.onemoreerror.mygate.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.onemoreerror.mygate.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_image)
    ImageView splashImage1;

    int[] imageArray = { R.mipmap.ic_launcher, R.mipmap.ic_launcher_round

    };

    int splashDuration = 3000;

    boolean redirected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                splashImage1.setImageResource(imageArray[i]);
                i++;
                if (i > imageArray.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 500);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                switchToHomeOrLoginActivity();
            }
        }, splashDuration);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    private void switchToHomeOrLoginActivity() {

//            ZPreferences.setIsUserLogin(this,false);
//        if (ZPreferences.getIsUserLogin(this)) {
            openHomeActivity();
//        } else {
//            openLoginActivity();
//        }
        finish();

    }
    public void openHomeActivity(){
        Intent homeIntent = new Intent(this,MainActivity.class);
        startActivity(homeIntent);
    }
//    @Override
//    protected void onDestroy() {
////        if (progressBar != null) {
////            progressBar.setVisibility(View.GONE);
////        }
////        super.onDestroy();
////    }
}
