package apps.amazon.com.dide.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

import apps.amazon.com.dide.activities.HomeActivity;
import apps.amazon.com.dide.R;


public class MotivationFragment extends Fragment{


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
                        R.drawable.b6, R.drawable.b7, R.drawable.b10,
                        R.drawable.b12, R.drawable.b16, R.drawable.b18, R.drawable.b19,
                        R.drawable.b21, R.drawable.b22, R.drawable.b23, R.drawable.b24, R.drawable.b25,
                        R.drawable.b27, R.drawable.b28, R.drawable.b30,
                        R.drawable.b31, R.drawable.b32, R.drawable.b34, R.drawable.b35,
                        R.drawable.b36};

        Random random = new Random();
        int rand = random.nextInt(quotes.length);

        ((ImageView) getView().findViewById(R.id.imageHolder)).setImageResource(quotes[rand]);

    }
}