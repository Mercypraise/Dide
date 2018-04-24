package apps.amazon.com.dide.activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import apps.amazon.com.dide.R;

public class QuotesActivity extends AppCompatActivity{

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        final int[] quotes = {R.drawable.b1, R.drawable.b3, R.drawable.b4, R.drawable.b6,
                R.drawable.b10, R.drawable.b18, R.drawable.b19, R.drawable.b21,
                R.drawable.b22, R.drawable.b23, R.drawable.b28,
                R.drawable.b30, R.drawable.b31, R.drawable.b34, R.drawable.b35};

        new CountDownTimer(3000, 1000){
            @Override
            public void onTick(long millisUntilFinished){
                findViewById(R.id.next).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        ++i;
                        findViewById(R.id.imageHolder).setBackgroundResource(quotes[i]);

                    }
                });

                findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        --i;
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

    }
}
