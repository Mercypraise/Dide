package apps.amazon.com.dide.navFragments;


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
import android.widget.EditText;
import android.widget.Toast;

import apps.amazon.com.dide.R;
import apps.amazon.com.dide.activities.UrgentEmergency;


public class ReportFragment extends android.app.Fragment{


    public ReportFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_report, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getView().findViewById(R.id.reportID).setOnTouchListener(new View.OnTouchListener() {

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

        getView().findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"dide.theamazons@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Case Report");
                i.putExtra(Intent.EXTRA_TEXT   , "From" + ((EditText) getView().findViewById(R.id.nameLink)).getText().toString().trim() +" a user of the \'Dide\' app\n\n\n"+ ((EditText) getView().findViewById(R.id.body)).getText().toString() +"\n\nThis individual can be reached on email: " +((EditText) getView().findViewById(R.id.mailLink)).getText().toString() +" or phone number: "+((EditText) getView().findViewById(R.id.numberLink)).getText().toString());

                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                }
                catch(android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
