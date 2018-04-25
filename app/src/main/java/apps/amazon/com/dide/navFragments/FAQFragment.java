package apps.amazon.com.dide.navFragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;

import apps.amazon.com.dide.R;
import apps.amazon.com.dide.activities.Answer;
import apps.amazon.com.dide.activities.UrgentEmergency;


public class FAQFragment extends android.app.Fragment implements View.OnClickListener{


    public FAQFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.one).setOnClickListener(this);
        getView().findViewById(R.id.two).setOnClickListener(this);
        getView().findViewById(R.id.three).setOnClickListener(this);
        getView().findViewById(R.id.four).setOnClickListener(this);
        getView().findViewById(R.id.five).setOnClickListener(this);
        getView().findViewById(R.id.six).setOnClickListener(this);
        getView().findViewById(R.id.seven).setOnClickListener(this);
        getView().findViewById(R.id.eight).setOnClickListener(this);
        getView().findViewById(R.id.nine).setOnClickListener(this);
        getView().findViewById(R.id.ten).setOnClickListener(this);


        getView().findViewById(R.id.faqIQ).setOnTouchListener(new View.OnTouchListener(){

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
    }

    @Override
    public void onClick(View v){
        String answer = "";
        String question = ((Button) getView().findViewById(v.getId())).getText().toString();

        switch(v.getId()){
            case R.id.one:
                answer = "Every lodged complaint goes directly to the creators of this application, and is immediately taken up. Necessary agencies are contacted, if need be.";
                break;
            case R.id.two:
                answer = "You get a reply as soon as possible, in less than 24 hours.";
                break;
            case R.id.three:
                answer = "YES!. DIDE respects identity. A lot of people cannot voice out, due to the fact that when they do, their story goes viral, and they become the subject of discussion all over town. We totally kick against that, so DIDE keeps the identity hidden from the general public. We can guarantee you that!";
                break;
            case R.id.four:
                answer = "No. Sexual harassment is not until an actual rape happens.\nSexual harassment majorly involves a person being deprived of a right by another person, using sex as a bait.";
                break;
            case R.id.five:
                answer = "The life feeds give people an opportunity to voice out. Their identity would be respected. People that can help intervene, who sees their post, would see to their aid. People also can comment and also take a que from their experience.";
                break;
            case R.id.six:
                answer = "The Amazons are a group of a five (5) friends in Ile-Ife, Osun State, Nigeria, who because of the disgust for cases of harassment around from the past, came together to try to curb the way cases go unreported, how justice is not really attended to, and how people can’t voice out; by aggressively launching DIDE with the honest aim of putting an end to sexual harassment in our society.";
                break;
            case R.id.seven:
                answer = "DIDE is a native Yoruba word meaning ‘RISE’. We want people to be able to boldly rise to speak out, to get justice and be their happy selves once again.";
                break;
            case R.id.eight:
                answer = "The emergency sound is to scare off any noticed stalking and suspicion around. NOTE: The sounds aren’t for prank purpose.";
                break;
            case R.id.nine:
                answer = "NO. DIDE is dedicated to the course of humanity, for both MALES AND FEMALES.";
                break;
            case R.id.ten:
                answer = "The developers in partnership with counsellors in our local community";
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putString("QUESTION", question);
        bundle.putString("ANSWER", answer);

        startActivity(new Intent(getActivity().getApplicationContext(), Answer.class).putExtras(bundle));
    }
}
