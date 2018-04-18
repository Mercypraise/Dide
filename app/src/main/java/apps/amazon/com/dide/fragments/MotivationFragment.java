package apps.amazon.com.dide.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

import apps.amazon.com.dide.activities.HomeActivity;
import apps.amazon.com.dide.R;


public class MotivationFragment extends Fragment{

    int i = 0;

    public MotivationFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_motivation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.btn_proceed).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
            }
        });



        int[] quotes = {R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4,
                        R.drawable.b6, R.drawable.b7, R.drawable.b10, R.drawable.b12,
                        R.drawable.b16, R.drawable.b18, R.drawable.b19, R.drawable.b21,
                        R.drawable.b22, R.drawable.b23, R.drawable.b24, R.drawable.b25,
                        R.drawable.b27, R.drawable.b28, R.drawable.b30, R.drawable.b31,
                        R.drawable.b32, R.drawable.b34, R.drawable.b35, R.drawable.b36};

        final int[] colours = {android.R.color.holo_red_dark, android.R.color.holo_blue_dark,
                         android.R.color.holo_orange_dark};

        ((ImageView) getView().findViewById(R.id.imageHolder)).setImageResource(quotes[((new Random()).nextInt(quotes.length))]);

        new CountDownTimer(1500, 1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish(){
                if(i == colours.length){
                    i = 0;
                }
                (getView().findViewById(R.id.btn_proceed)).setBackgroundColor(colours[i]);
                i++;
                start();
            }
        }.start();
    }
}