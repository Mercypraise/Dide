package apps.amazon.com.dide;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{


    int ex = 0;
//    FirebaseAuth mAuth;
    EditText email, password;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        login = findViewById(R.id.btn_login);


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
//                    mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task){
//                            if(task.isSuccessful()){
//                                findViewById(R.id.progressBar).setVisibility(View.GONE);
//                                //Some shii
//                            }
//                        }
//                    });
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
