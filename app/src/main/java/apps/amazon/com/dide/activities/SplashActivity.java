package apps.amazon.com.dide.activities;


import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import apps.amazon.com.dide.R;


public class SplashActivity extends AppCompatActivity{

    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        cdt = new CountDownTimer(1700, 1000){
            @Override
            public void onTick(long millisUntilFinished){

                findViewById(R.id.image).setOnTouchListener(new View.OnTouchListener() {

                    Handler handler = new Handler();

                    int numberOfTaps = 0;
                    long lastTapTimeMs = 0;
                    long touchDownMs = 0;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                touchDownMs = System.currentTimeMillis();
                                break;
                            case MotionEvent.ACTION_UP:
                                handler.removeCallbacksAndMessages(null);

                                if((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout()){

                                    numberOfTaps = 0;
                                    lastTapTimeMs = 0;
                                    break;
                                }

                                if(numberOfTaps > 0 && (System.currentTimeMillis() - lastTapTimeMs) < ViewConfiguration.getDoubleTapTimeout()) {
                                    numberOfTaps += 1;

                                }

                                else{
                                    numberOfTaps = 1;
                                }

                                lastTapTimeMs = System.currentTimeMillis();

                                if (numberOfTaps == 4){
                                    startActivity(new Intent(getApplicationContext(), UrgentEmergency.class));
                                }
                        }

                        return true;
                    }
                });

            }

            @Override
            public void onFinish(){
                startActivity(new Intent(getApplicationContext(), TwinActivity.class));
            }
        }.start();


    }
}