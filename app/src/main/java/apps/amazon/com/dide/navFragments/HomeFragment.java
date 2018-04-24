package apps.amazon.com.dide.navFragments;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import apps.amazon.com.dide.R;
import apps.amazon.com.dide.activities.MessFragment;
import apps.amazon.com.dide.activities.QuotesActivity;
import apps.amazon.com.dide.activities.UrgentEmergency;


public class HomeFragment extends android.app.Fragment{


    public HomeFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.homeID).setOnTouchListener(new View.OnTouchListener(){

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
        getView().findViewById(R.id.dontmess).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.cont, new MessFragment(), "categories");
                fragmentTransaction.commit();
            }
        });

        getView().findViewById(R.id.feeds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.cont, new FeedsFragment(), "feeds");
                fragmentTransaction.commit();
            }
        });

        getView().findViewById(R.id.motiv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), QuotesActivity.class));
            }
        });

        getView().findViewById(R.id.report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code goes here
            }
        });

        getView().findViewById(R.id.trivia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.cont, new TriviaFragment(), "trivia");
                fragmentTransaction.commit();
            }
        });

        getView().findViewById(R.id.counsellor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.cont, new CounsellorFragment(), "counsellor");
                fragmentTransaction.commit();
            }
        });
    }
}
