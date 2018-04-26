package apps.amazon.com.dide.activities;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import apps.amazon.com.dide.R;
import apps.amazon.com.dide.adapters.RecyclerAdapter;
import apps.amazon.com.dide.models.PostModel;
import apps.amazon.com.dide.models.UserModel;
import apps.amazon.com.dide.navFragments.FeedsFragment;
import apps.amazon.com.dide.navFragments.TriviaFragment;

public class AddNewStory extends AppCompatActivity {

    EditText title, content;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String namee = "anonymous";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_story);

        final Context context = this;

        title = findViewById(R.id.titleField);
        content = findViewById(R.id.contentField);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.newStoryID).setOnTouchListener(new View.OnTouchListener(){

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


        findViewById(R.id.publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().trim().equals("")){
                    title.setError("Title cannot be empty");
                }
                else if(title.getText().toString().length() > 20){
                    title.setError("Title is too long");
                }
                else if(content.getText().toString().trim().equals("")){
                    content.setError("Content cannot be empty");
                }
                else{
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                    final ArrayList<String> comments = new ArrayList<>();
                    comments.add("null");
                    final ArrayList<String> commenters = new ArrayList<>();
                    commenters.add("null");

                    if(!(((CheckBox) findViewById(R.id.anon)).isChecked())){
                        mCheckAndSetValues(databaseReference);
                    }
                    else{
                        PostModel newPost = new PostModel(namee, title.getText().toString().trim(), content.getText().toString().trim(), databaseReference.child("posts").push().getKey(), comments, commenters);
                        databaseReference.child("posts").push().setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task){
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    new AlertDialog.Builder(context)
                                            .setTitle("Successful!")
                                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("haa", "feeds"));
                                                }
                                            })
                                            .show();
                                }
                                else{
                                    new AlertDialog.Builder(getApplicationContext())
                                            .setTitle("Failed!")
                                            .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    findViewById(R.id.publish).performClick();
                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }

                }
            }
        });
    }


    public interface MyPersonalListener{
        public void onStart();
        public void onSuccess(DataSnapshot data);
        public void onFailed(DatabaseError databaseError);
    }


    private void mReadDataOnce(final AddNewStory.MyPersonalListener listener, DatabaseReference databaseReference){
        listener.onStart();
        databaseReference.child("posts").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                listener.onFailed(databaseError);
            }
        });
    }


    private void mCheckAndSetValues(DatabaseReference databaseReferenc){

        new AddNewStory().mReadDataOnce(new AddNewStory.MyPersonalListener(){

            @Override
            public void onStart(){
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot){
                findViewById(R.id.progressBar).setVisibility(View.GONE);

                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                namee = userModel.getDisplayName();

            }

            @Override
            public void onFailed(DatabaseError databaseError){

            }
        }, databaseReferenc);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}