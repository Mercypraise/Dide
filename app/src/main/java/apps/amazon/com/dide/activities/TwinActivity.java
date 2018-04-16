package apps.amazon.com.dide.activities;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import apps.amazon.com.dide.R;
import apps.amazon.com.dide.fragments.EmergencyFragment;
import apps.amazon.com.dide.fragments.MotivationFragment;


public class TwinActivity extends AppCompatActivity{


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twin);

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new EmergencyFragment());
        list.add(new MotivationFragment());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), list, getApplicationContext());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }



    public static class PlaceholderFragment extends Fragment{


        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment(){

        }


        public static PlaceholderFragment newInstance(int sectionNumber){
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_container, container, false);
            return rootView;
        }
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> allFragments;
        private Context context;


        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> allFragments, Context context){
            super(fm);
            this.allFragments = allFragments;
            this.context = context;
        }

        @Override
        public Fragment getItem(int position){
            return allFragments.get(position);
        }

        @Override
        public int getCount(){
            return allFragments.size();
        }
    }


    @Override
    public void onBackPressed(){

    }
}