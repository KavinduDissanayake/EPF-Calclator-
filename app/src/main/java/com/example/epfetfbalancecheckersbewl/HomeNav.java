package com.example.epfetfbalancecheckersbewl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.epfetfbalancecheckersbewl.Screen.HelpCenterFragment;
import com.example.epfetfbalancecheckersbewl.Screen.Home.HomeFragment;
import com.example.epfetfbalancecheckersbewl.Screen.ProfileFragment;
import com.example.epfetfbalancecheckersbewl.Screen.SplashActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeNav extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    //session for get values
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_nav);

        bottomNavigation = findViewById(R.id.nav_view);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        // Passing each menu ID as a set of Ids because each
        openFragment(new HomeFragment());
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            //Log.d("menu event","Home");
                            openFragment(new HomeFragment());
                            return true;
                        case R.id.navigation_my_appoi:
                            // Log.d("menu event","Home");
                           openFragment(new ProfileFragment());
                            return true;
                        case R.id.navigation_profile:
                            // Log.d("menu event","Home");
                            openFragment(new HelpCenterFragment());

                            return true;


                        case R.id.navigation_logout:
                            // Log.d("menu event","Home");
                            AlertMessageLogout();

                            return true;
                    }
                    return false;
                }
            };




    private void AlertMessageLogout(){

        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(HomeNav.this);
        dialog.setTitle( "Task Status" )
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setMessage("Do you want to logout ?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Logout();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }}).show();

    }




    private void Logout() {
        //declare a session
        sharedpreferences=getSharedPreferences("user_details",MODE_PRIVATE);
        //sessions clear when user log out
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("isLoggedIn",false);


        FirebaseAuth.getInstance().signOut();


        editor.clear();
        editor.commit();
        startActivity(new Intent(HomeNav.this, SplashActivity.class));
        finish();
    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}