package apps.amazon.com.dide.activities;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import apps.amazon.com.dide.R;

public class SplashActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new CountDownTimer(2000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(findViewById(R.id.image), View.ALPHA, 1.0f, 0.0f);
                objectAnimator.setDuration(1500).addListener(new AnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(Animator animation){
                        Log.d("CHECK", "got here");
                        startActivity(new Intent(getApplicationContext(), TwinActivity.class));
                    }

                    @Override
                    public void onAnimationResume(Animator animation) {
                        onFinish();
                    }

                });

                objectAnimator.start();
            }
        }.start();
    }
}