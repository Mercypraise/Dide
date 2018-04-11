package apps.amazon.com.dide;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        new CountDownTimer(3000, 1000){
            @Override
            public void onTick(long millisUntilFinished){

            }


            @Override
            public void onFinish(){
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }.start();
    }
}
