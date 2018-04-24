package apps.amazon.com.dide.activities;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import apps.amazon.com.dide.R;
import apps.amazon.com.dide.navFragments.TicTacToeFragment;
import apps.amazon.com.dide.navFragments.AboutFragment;
import apps.amazon.com.dide.navFragments.HomeFragment;
import apps.amazon.com.dide.navFragments.ProfileFragment;
import apps.amazon.com.dide.navFragments.FeedsFragment;
import apps.amazon.com.dide.navFragments.TriviaFragment;



public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    int ex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overridePendingTransition(0, 0);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if((getIntent().getStringExtra("HAA") != null) && getIntent().getStringExtra("HAA").equals("xando")){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.cont, new TicTacToeFragment(), "ticky");
            fragmentTransaction.commit();
        }

        else if((getIntent().getStringExtra("HAA") != null) && getIntent().getStringExtra("HAA").equals("game")){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.cont, new TriviaFragment(), "game");
            fragmentTransaction.commit();
        }

        else{
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.cont, new HomeFragment(), "home");
            fragmentTransaction.commit();
        }


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }

        else{
            HomeFragment homeFragment = (HomeFragment) getFragmentManager().findFragmentByTag("home");

            if(homeFragment != null && homeFragment.isVisible()){
                switch(ex){
                    case 0:
                        Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_LONG).show();
                        ex++;
                        new CountDownTimer(3000, 1000){
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                ex = 0;
                            }
                        }.start();
                        break;
                    case 1:
                        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;
                }
            }

            else{
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.cont, new HomeFragment(), "home");
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Home");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.nav_about){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.cont, new AboutFragment(), "about");
            fragmentTransaction.commit();

            getSupportActionBar().setTitle("About");

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        else if(id == R.id.nav_rate){
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            new AlertDialog.Builder(this)
                    .setTitle("Rate this app on Google Play Store?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "This setting has not been added yet, sorry ehn", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }

        else if(id == R.id.profile){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.cont, new ProfileFragment(), "profile");
            fragmentTransaction.commit();

            getSupportActionBar().setTitle("Profile");

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        else if(id == R.id.feeds){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.cont, new FeedsFragment(), "stories");
            fragmentTransaction.commit();

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            getSupportActionBar().setTitle("Stories");
        }

        else if(id == R.id.trivia){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.cont, new TriviaFragment(), "trivia");
            fragmentTransaction.commit();

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            getSupportActionBar().setTitle("Trivia");
        }


        else if(id == R.id.faq){
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(), "This setting has not been added yet, sorry ehn", Toast.LENGTH_LONG).show();
        }


        return true;
    }
}