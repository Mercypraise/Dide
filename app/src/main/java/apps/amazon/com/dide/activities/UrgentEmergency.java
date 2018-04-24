package apps.amazon.com.dide.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import apps.amazon.com.dide.R;

public class UrgentEmergency extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_emergency);


        mp = MediaPlayer.create(this, R.raw.gun);
        mp.setLooping(true);
        mp.start();

        findViewById(R.id.quickCall).setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v){
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String number = settings.getString("emergency", "07061979046");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
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
