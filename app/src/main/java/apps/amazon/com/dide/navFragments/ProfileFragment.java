package apps.amazon.com.dide.navFragments;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import apps.amazon.com.dide.activities.EditActivity;
import apps.amazon.com.dide.R;
import apps.amazon.com.dide.activities.HomeActivity;
import apps.amazon.com.dide.activities.InterludeActivity;
import apps.amazon.com.dide.activities.UrgentEmergency;
import apps.amazon.com.dide.models.UserModel;


public class ProfileFragment extends android.app.Fragment{

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    TextView name, email, number, gender, emergencyNumber;
    ImageView image;

    public ProfileFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);


        getView().findViewById(R.id.root).setVisibility(View.GONE);
        getView().findViewById(R.id.mama).setOnTouchListener(new View.OnTouchListener(){

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
                            startActivity(new Intent(getActivity().getApplicationContext(), UrgentEmergency.class));
                        }
                }

                return true;
            }
        });

        name = getView().findViewById(R.id.nameCheck);
        number = getView().findViewById(R.id.numberCheck);
        gender = getView().findViewById(R.id.genderCheck);
        emergencyNumber = getView().findViewById(R.id.emergencyNumberCheck);
        image = getView().findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "To preserve anonymity, we don't allow profile pictures", Toast.LENGTH_LONG).show();
            }
        });

        Snackbar.make(getActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
                .setAction("Tap to edit your profile", new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        startActivity(new Intent(getActivity().getApplicationContext(), EditActivity.class));
                    }
                })
                .setDuration(1200000)
                .show();


        if(!isNetworkAvailable()){
            getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
            new AlertDialog.Builder(getActivity())
                    .setMessage("You don't seem to be connected to the internet. Check your connection and try again")
                    .setCancelable(false)
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
                        }
                    })
                    .show();
        }
        else if(mAuth.getCurrentUser() == null){
            getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
            new AlertDialog.Builder(getActivity())
                    .setMessage("You need to be logged in to use this feature")
                    .setCancelable(false)
                    .setPositiveButton("Log in", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getActivity().getApplicationContext(), InterludeActivity.class));
                        }
                    })
                    .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
                        }
                    })
                    .show();
        }
        else{
            mCheckAndSetValues(mAuth, databaseReference);
        }
    }


    public interface MyPersonalListener{
        public void onStart();
        public void onSuccess(DataSnapshot data);
        public void onFailed(DatabaseError databaseError);
    }


    private void mReadDataOnce(final MyPersonalListener listener, DatabaseReference databaseReference, FirebaseAuth mAuth){
        listener.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener(){
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


    private void mCheckAndSetValues(FirebaseAuth mAut, DatabaseReference databaseReferenc){

        new ProfileFragment().mReadDataOnce(new MyPersonalListener(){

            @Override
            public void onStart(){
              getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot){

                UserModel userTree = dataSnapshot.getValue(UserModel.class);

                getView().findViewById(R.id.progressBar).setVisibility(View.GONE);

                name.setText(userTree.getDisplayName());
                email.setText(userTree.getEmail());
                gender.setText(userTree.getGender());
                emergencyNumber.setText(userTree.getEmergencyNumber());

                if(userTree.getGender().trim().toLowerCase().equals("male")){
                    image.setImageResource(R.drawable.male);
                }
                else if(userTree.getGender().trim().toLowerCase().equals("female")){
                    image.setImageResource(R.drawable.female);
                }

            }

            @Override
            public void onFailed(DatabaseError databaseError){
                getView().findViewById(R.id.progressBar).setVisibility(View.GONE);

                new AlertDialog.Builder(getActivity())
                        .setMessage("Something went wrong, try again?")
                        .setCancelable(false)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.cont, new ProfileFragment(), "profile");
                                fragmentTransaction.commit();
                            }
                        })
                        .setNegativeButton("Back to home", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
                            }
                        })
                        .show();
            }
        }, databaseReferenc, mAut);

    }


    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    }