package apps.amazon.com.dide.activities;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import apps.amazon.com.dide.R;

public class UrgentEmergency extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_emergency);


//        AudioManager audioManager = AudioManager.;


        findViewById(R.id.quickCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //place the call
            }
        });
    }
}
