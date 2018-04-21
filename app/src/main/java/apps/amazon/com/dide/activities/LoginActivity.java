package apps.amazon.com.dide.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import apps.amazon.com.dide.R;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener{


    EditText email, password;
    TextView login;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Handler handler = new Handler();

    int numberOfTaps = 0;
    long lastTapTimeMs = 0;
    long touchDownMs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.imagee).setOnTouchListener(this);

        findViewById(R.id.butsignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())){
                    email.setError("Invalid email");
                }
                else if(password.getText().toString().trim().length() < 6){
                    password.setError("Invalid password");
                }
                else{
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                            else{
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Failed, try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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
