package apps.amazon.com.dide.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity{


    int ex = 0;
    EditText email, password;
    TextView login;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

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
