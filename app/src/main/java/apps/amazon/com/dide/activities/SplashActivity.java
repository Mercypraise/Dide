package apps.amazon.com.dide.activities;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import apps.amazon.com.dide.R;


public class SplashActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new CountDownTimer(1700, 1000){
            @Override
            public void onTick(long millisUntilFinished){

            }

            @Override
            public void onFinish(){
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(findViewById(R.id.image), View.ALPHA, 1.0f, 0.7f);
//                objectAnimator.setDuration(1000).addListener(new AnimatorListenerAdapter(){
//
//                    @Override
//                    public void onAnimationEnd(Animator animation){
//                        Log.d("CHECK", "got here");
//                        startActivity(new Intent(getApplicationContext(), TwinActivity.class));
//                    }
//
//                    @Override
//                    public void onAnimationResume(Animator animation) {
//                        onFinish();
//                    }
//
//                });
//
//                objectAnimator.start();
                startActivity(new Intent(getApplicationContext(), TwinActivity.class));
            }
        }.start();
    }
}