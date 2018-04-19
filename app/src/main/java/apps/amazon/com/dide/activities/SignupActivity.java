package apps.amazon.com.dide.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    EditText email, password, name, number;
    RadioButton mGender, fGender;
    String gend = "not set";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        name = findViewById(R.id.nameText);
        number = findViewById(R.id.numberText);
        mGender = findViewById(R.id.maleGender);
        fGender = findViewById(R.id.femaleGender);

        mGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gend = "male";
                fGender.setChecked(false);
            }
        });

        fGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mGender.setChecked(false);
                gend = "female";
            }
        });



        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserModel newUser = new UserModel(name.getText().toString().trim(), email.getText().toString().trim(), number.getText().toString().trim(), gend, "07061979046");
                            FirebaseUser user = mAuth.getCurrentUser();
                            databaseReference.child(user.getUid()).setValue(newUser);
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Failed, try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
