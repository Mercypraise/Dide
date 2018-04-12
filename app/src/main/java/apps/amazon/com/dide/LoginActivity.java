package apps.amazon.com.dide;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    int ex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public void onBackPressed(){
        if(ex == 0){
            Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_LONG).show();
            ex = 1;

            new CountDownTimer(2000, 1000){
                @Override
                public void onTick(long millisUntilFinished){

                }

                @Override
                public void onFinish(){
                    ex = 0;
                }
            }.start();
        }


        else if(ex == 1){
            startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }



    }
}
