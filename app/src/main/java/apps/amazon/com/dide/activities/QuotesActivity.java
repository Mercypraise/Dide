package apps.amazon.com.dide.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import apps.amazon.com.dide.R;

public class QuotesActivity extends AppCompatActivity{

    int i = 0;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);


        mp = MediaPlayer.create(this, R.raw.quote_sound);
        mp.setLooping(true);
        mp.start();


        final int[] quotes = {R.drawable.b1, R.drawable.b3, R.drawable.b4, R.drawable.b6,
                R.drawable.b10, R.drawable.b18, R.drawable.b19, R.drawable.b21,
                R.drawable.b22, R.drawable.b23, R.drawable.b28,
                R.drawable.b30, R.drawable.b31, R.drawable.b35};

        findViewById(R.id.imageHolder).setBackgroundResource(quotes[i]);


        new CountDownTimer(5000, 1000){
            @Override
            public void onTick(long millisUntilFinished){
                findViewById(R.id.next).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        ++i;
                        if(i >= quotes.length){
                            i = 0;
                        }
                        findViewById(R.id.imageHolder).setBackgroundResource(quotes[i]);

                    }
                });

                findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        --i;
                        if(i < 0){
                            i = quotes.length - 1;
                        }
                        findViewById(R.id.imageHolder).setBackgroundResource(quotes[i]);
                    }
                });
            }

            @Override
            public void onFinish(){
                if(i == quotes.length){
                    i = 0;
                }

                findViewById(R.id.imageHolder).setBackgroundResource(quotes[i]);
                ++i;
                start();
            }
        }.start();

        findViewById(R.id.quotesID).setOnTouchListener(new View.OnTouchListener() {

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
    public void onBackPressed(){
        mp.pause();
        super.onBackPressed();
    }


    @Override
    protected void onStop() {
        mp.pause();
        super.onStop();
    }


}
