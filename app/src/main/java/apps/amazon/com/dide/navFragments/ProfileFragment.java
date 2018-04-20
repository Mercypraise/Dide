package apps.amazon.com.dide.navFragments;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
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
import apps.amazon.com.dide.activities.LoginActivity;
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

        Snackbar.make(getView().findViewById(android.R.id.content), "Edit profile", Snackbar.LENGTH_LONG)
                .setAction("Make changes to your profile?", new View.OnClickListener(){
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
                    .setTitle("You don't seem to be connected to the internet. Check your connection and try again")
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
                    .setTitle("You need to be logged in to use this feature")
                    .setCancelable(false)
                    .setPositiveButton("Log in", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
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
                getView().findViewById(R.id.root).setVisibility(View.VISIBLE);

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
                        .setTitle("Something went wrong, try again?")
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