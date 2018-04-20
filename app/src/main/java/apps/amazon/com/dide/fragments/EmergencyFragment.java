package apps.amazon.com.dide.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jgabrielfreitas.core.BlurImageView;

import apps.amazon.com.dide.R;
import apps.amazon.com.dide.activities.UrgentEmergency;


public class EmergencyFragment extends Fragment implements View.OnTouchListener{


    Handler handler = new Handler();

    int numberOfTaps = 0;
    long lastTapTimeMs = 0;
    long touchDownMs = 0;


    public EmergencyFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_emergency, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.img).setOnTouchListener(this);
        getView().findViewById(R.id.cdf).setOnTouchListener(this);
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

                if (numberOfTaps == 4) {
                    startActivity(new Intent(getActivity().getApplicationContext(), UrgentEmergency.class));
                }
        }

        return true;
    }
}
