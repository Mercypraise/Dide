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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import apps.amazon.com.dide.R;
import apps.amazon.com.dide.activities.AddNewStory;
import apps.amazon.com.dide.activities.HomeActivity;
import apps.amazon.com.dide.activities.InterludeActivity;
import apps.amazon.com.dide.activities.LoginActivity;
import apps.amazon.com.dide.activities.UrgentEmergency;
import apps.amazon.com.dide.adapters.RecyclerAdapter;
import apps.amazon.com.dide.models.PostModel;


public class FeedsFragment extends android.app.Fragment {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;

    public FeedsFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_feeds, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getView().findViewById(R.id.groot).setVisibility(View.GONE);
        getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        getView().findViewById(R.id.addNewStory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), AddNewStory.class));
            }
        });

        getView().findViewById(R.id.root).setOnTouchListener(new View.OnTouchListener() {

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


        ArrayList<PostModel> posts = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.feeds);


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
            mCheckAndSetValues(databaseReference);
        }

    }


    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public interface MyPersonalListener{
        public void onStart();
        public void onSuccess(DataSnapshot data);
        public void onFailed(DatabaseError databaseError);
    }


    private void mReadDataOnce(final FeedsFragment.MyPersonalListener listener, DatabaseReference databaseReference){
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

        new FeedsFragment().mReadDataOnce(new FeedsFragment.MyPersonalListener(){

            @Override
            public void onStart(){
                getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot){
                getView().findViewById(R.id.groot).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.progressBar).setVisibility(View.GONE);

                ArrayList<PostModel> models = new ArrayList<>();
                Iterable<DataSnapshot> allposts = dataSnapshot.getChildren();

                for(DataSnapshot i: allposts){
                    models.add(i.getValue(PostModel.class));
                }

                if(models.isEmpty()){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("No stories, create one?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getView().findViewById(R.id.addNewStory).performClick();
                                }
                            })
                            .setNegativeButton("Back to Home", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
                                }
                            })
                            .show();
                }
                else{
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    recyclerView.setAdapter(new RecyclerAdapter(getActivity().getApplicationContext(), models));
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
                                fragmentTransaction.replace(R.id.cont, new FeedsFragment(), "feeds");
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
        }, databaseReferenc);

    }

}