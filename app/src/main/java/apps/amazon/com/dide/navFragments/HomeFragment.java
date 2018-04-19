package apps.amazon.com.dide.navFragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.amazon.com.dide.R;



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

        getView().findViewById(R.id.dontmess).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //code goes here!
            }
        });

        getView().findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //code goes here
            }
        });

        getView().findViewById(R.id.motiv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code goes here
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
                //code goes here
            }
        });

        getView().findViewById(R.id.counsellor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code goes here
            }
        });
    }
}
