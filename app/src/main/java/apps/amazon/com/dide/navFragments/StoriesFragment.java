package apps.amazon.com.dide.navFragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import apps.amazon.com.dide.R;
import apps.amazon.com.dide.activities.HomeActivity;
import apps.amazon.com.dide.activities.LoginActivity;


public class StoriesFragment extends android.app.Fragment {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    public StoriesFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_stories, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getView().findViewById(R.id.root).setVisibility(View.GONE);
        getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

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
            //code here!
        }

    }


    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
