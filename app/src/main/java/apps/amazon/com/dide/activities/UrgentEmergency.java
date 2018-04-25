package apps.amazon.com.dide.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
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

        final Activity activity = this;

        findViewById(R.id.quickCall).setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v){


                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 101);


                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String number = settings.getString("emergency", "09080515774");
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number));
                    startActivity(intent);
                }
                else{
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String number = settings.getString("emergency", "09080515774");
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number));
                    startActivity(intent);
                }



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
