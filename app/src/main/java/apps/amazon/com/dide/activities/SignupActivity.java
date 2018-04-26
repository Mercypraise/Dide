package apps.amazon.com.dide.activities;


import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import apps.amazon.com.dide.R;
import apps.amazon.com.dide.models.UserModel;


public class SignupActivity extends AppCompatActivity implements View.OnTouchListener{

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    EditText email, password, name, number;
    RadioButton mGender, fGender;
    String gend = "not set";
    CardView nameCard, emailCard, numberCard, genderCard, passwordCard;
    int i = 0;
    Handler handler = new Handler();
    int numberOfTaps = 0;
    long lastTapTimeMs = 0;
    long touchDownMs = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameCard = findViewById(R.id.displayNameCard);
        emailCard = findViewById(R.id.emailCard);
        numberCard = findViewById(R.id.numberCard);
        genderCard = findViewById(R.id.genderCard);
        passwordCard = findViewById(R.id.passwordCard);

        final String[] sohowhowhow = new String[5];

        switch(i){

            case 0:
                nameCard.setVisibility(View.VISIBLE);
                emailCard.setVisibility(View.GONE);
                numberCard.setVisibility(View.GONE);
                genderCard.setVisibility(View.GONE);
                passwordCard.setVisibility(View.GONE);
                break;
            case 1:
                nameCard.setVisibility(View.GONE);
                emailCard.setVisibility(View.VISIBLE);
                numberCard.setVisibility(View.GONE);
                genderCard.setVisibility(View.GONE);
                passwordCard.setVisibility(View.GONE);
                break;
            case 2:
                nameCard.setVisibility(View.GONE);
                emailCard.setVisibility(View.GONE);
                numberCard.setVisibility(View.GONE);
                genderCard.setVisibility(View.VISIBLE);
                passwordCard.setVisibility(View.GONE);
                break;
            case 3:
                nameCard.setVisibility(View.GONE);
                emailCard.setVisibility(View.GONE);
                numberCard.setVisibility(View.VISIBLE);
                genderCard.setVisibility(View.GONE);
                passwordCard.setVisibility(View.GONE);
                break;
            case 4:
                nameCard.setVisibility(View.GONE);
                emailCard.setVisibility(View.GONE);
                numberCard.setVisibility(View.GONE);
                genderCard.setVisibility(View.GONE);
                passwordCard.setVisibility(View.VISIBLE);
                break;
        }


        mGender = findViewById(R.id.maleGender);
        fGender = findViewById(R.id.femaleGender);

        mGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mGender.isChecked()){
                    gend = "male";
                    fGender.setChecked(false);
                }

            }
        });

        fGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(fGender.isChecked()){
                    mGender.setChecked(false);
                    gend = "female";
                }

            }
        });



        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch(i){
                    case 0:
                        if(((EditText) findViewById(R.id.nameText)).getText().toString().trim().equals("")){
                            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
                        }
                        else{
                            sohowhowhow[0] = ((EditText) findViewById(R.id.nameText)).getText().toString().trim();
                            ++i;

                            switch(i){

                                case 0:
                                    nameCard.setVisibility(View.VISIBLE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 1:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.VISIBLE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.VISIBLE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.VISIBLE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.VISIBLE);
                                    break;
                            }

                        }
                        break;
                    case 1:
                        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(((EditText) findViewById(R.id.emailText)).getText().toString().trim()).matches())){
                            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
                        }
                        else{
                            sohowhowhow[1] = ((EditText) findViewById(R.id.emailText)).getText().toString().trim();
                            ++i;

                            switch(i){

                                case 1:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.VISIBLE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.VISIBLE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.VISIBLE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.VISIBLE);
                                    break;
                            }

                        }
                        break;
                    case 2:
                        if(gend.equals("not set")){
                            Toast.makeText(getApplicationContext(), "Please choose a gender", Toast.LENGTH_LONG).show();
                        }
                        else{
                            sohowhowhow[2] = gend;
                            ++i;

                            switch(i){

                                case 2:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.VISIBLE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.VISIBLE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.VISIBLE);
                                    break;
                            }

                        }
                        break;
                    case 3:
                        if(!(Patterns.PHONE.matcher(((EditText) findViewById(R.id.numberText)).getText().toString().trim()).matches())){
                            Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_LONG).show();
                        }
                        else{
                            sohowhowhow[3] = ((EditText) findViewById(R.id.numberText)).getText().toString().trim();
                            ++i;

                            switch(i){

                                case 3:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.VISIBLE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    nameCard.setVisibility(View.GONE);
                                    emailCard.setVisibility(View.GONE);
                                    numberCard.setVisibility(View.GONE);
                                    genderCard.setVisibility(View.GONE);
                                    passwordCard.setVisibility(View.VISIBLE);
                                    break;
                            }

                        }
                        break;
                    case 4:
                        if(((EditText) findViewById(R.id.passwordText)).getText().toString().trim().length() < 6){
                            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG).show();
                        }
                        else{
                            sohowhowhow[4] = ((EditText) findViewById(R.id.numberText)).getText().toString().trim();
                            ++i;

                            if(i == 5){
                                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                                mAuth.createUserWithEmailAndPassword(sohowhowhow[1], sohowhowhow[4]).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            UserModel newUser = new UserModel(sohowhowhow[0], sohowhowhow[1], sohowhowhow[3], sohowhowhow[2], "09080515774");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            databaseReference.child("users").child(user.getUid()).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                }
                                            });

                                        }
                                        else{
                                            findViewById(R.id.progressBar).setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                        break;
                }

            }
        });
    }

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

}
