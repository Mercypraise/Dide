package apps.amazon.com.dide.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import apps.amazon.com.dide.R;

public class EditActivity extends AppCompatActivity{

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    EditText name, email, number, emergency;
    LinearLayout nal, eml, nul, emerl;
    CardView skip, next;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final Context context = this;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nal = findViewById(R.id.displayNameLayout);
        eml = findViewById(R.id.emailLayout);
        nul = findViewById(R.id.numberLayout);
        emerl = findViewById(R.id.emergencyLayout);

        name = findViewById(R.id.editTextForDisplayName);
        email = findViewById(R.id.editTextForEmail);
        number = findViewById(R.id.editTextForNumber);
        emergency = findViewById(R.id.editTextForEmergency);

        skip = findViewById(R.id.skip);
        next = findViewById(R.id.next);

        findViewById(R.id.editID).setOnTouchListener(new View.OnTouchListener(){

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


        final ArrayList<String> items = new ArrayList<>();
        items.add("name");
        items.add("number");
        items.add("email");
        items.add("emergency");

        switch(items.get(i)){
            case "name":
                nal.setVisibility(View.VISIBLE);
                nul.setVisibility(View.GONE);
                emerl.setVisibility(View.GONE);
                eml.setVisibility(View.GONE);
                break;
            case "number":
                nal.setVisibility(View.GONE);
                nul.setVisibility(View.VISIBLE);
                emerl.setVisibility(View.GONE);
                eml.setVisibility(View.GONE);
                break;
            case "email":
                nal.setVisibility(View.GONE);
                nul.setVisibility(View.GONE);
                emerl.setVisibility(View.GONE);
                eml.setVisibility(View.VISIBLE);
                break;
            case "emergency":
                nal.setVisibility(View.GONE);
                nul.setVisibility(View.GONE);
                emerl.setVisibility(View.VISIBLE);
                eml.setVisibility(View.GONE);
                break;
        }


        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ++i;
                if(i == items.size()){
                    new AlertDialog.Builder(context)
                            .setMessage("Complete!")
                            .setCancelable(false)
                            .setPositiveButton("Back to profile", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }
                            })
                            .show();
                }
                else{
                    switch(items.get(i)){
                        case "name":
                            nal.setVisibility(View.VISIBLE);
                            nul.setVisibility(View.GONE);
                            emerl.setVisibility(View.GONE);
                            eml.setVisibility(View.GONE);
                            break;
                        case "number":
                            nal.setVisibility(View.GONE);
                            nul.setVisibility(View.VISIBLE);
                            emerl.setVisibility(View.GONE);
                            eml.setVisibility(View.GONE);
                            break;
                        case "email":
                            nal.setVisibility(View.GONE);
                            nul.setVisibility(View.GONE);
                            emerl.setVisibility(View.GONE);
                            eml.setVisibility(View.VISIBLE);
                            break;
                        case "emergency":
                            nal.setVisibility(View.GONE);
                            nul.setVisibility(View.GONE);
                            emerl.setVisibility(View.VISIBLE);
                            eml.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch(items.get(i)){
                    case "name":
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("displayName").setValue(name.getText().toString().trim());
                        break;
                    case "number":
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("pNumber").setValue(number.getText().toString().trim());
                        break;
                    case "email":
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("email").setValue(email.getText().toString());
                        break;
                    case "emergency":
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("emergencyNumber").setValue(emergency.getText().toString());
                        break;
                }

                ++i;

                if(i == items.size()){
                    new AlertDialog.Builder(getApplicationContext())
                            .setMessage("Complete!")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }
                            })
                            .show();
                }
                else{
                    switch(items.get(i)){
                        case "name":
                            nal.setVisibility(View.VISIBLE);
                            nul.setVisibility(View.GONE);
                            emerl.setVisibility(View.GONE);
                            eml.setVisibility(View.GONE);
                            break;
                        case "number":
                            nal.setVisibility(View.GONE);
                            nul.setVisibility(View.VISIBLE);
                            emerl.setVisibility(View.GONE);
                            eml.setVisibility(View.GONE);
                            break;
                        case "email":
                            nal.setVisibility(View.GONE);
                            nul.setVisibility(View.GONE);
                            emerl.setVisibility(View.GONE);
                            eml.setVisibility(View.VISIBLE);
                            break;
                        case "emergency":
                            nal.setVisibility(View.GONE);
                            nul.setVisibility(View.GONE);
                            emerl.setVisibility(View.VISIBLE);
                            eml.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(getApplicationContext())
                .setTitle("Back to profile")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
