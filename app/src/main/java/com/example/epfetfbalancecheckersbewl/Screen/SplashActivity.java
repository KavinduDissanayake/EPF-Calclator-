package com.example.epfetfbalancecheckersbewl.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.epfetfbalancecheckersbewl.HomeNav;
import com.example.epfetfbalancecheckersbewl.R;
import com.example.epfetfbalancecheckersbewl.Screen.Sign.LoginActivity;
import com.example.epfetfbalancecheckersbewl.Screen.Sign.RegisterActivity;

public class SplashActivity extends AppCompatActivity {
    ImageView img;

    SharedPreferences sharedpreferences;

    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        img=findViewById( R.id.image );

        Animation myanim= AnimationUtils.loadAnimation( this,R.anim.mytransition );
        img.startAnimation(myanim);


        //to check current user types
        sharedpreferences= getSharedPreferences("user_details", MODE_PRIVATE);

        isLoggedIn=sharedpreferences.getBoolean("isLoggedIn",false);



        Thread timer= new Thread(){
            public void run(){
                try{
                    sleep( 3000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {



                    //if user login then navigation process
                    if(isLoggedIn) {
                        startActivity(new Intent(SplashActivity.this, HomeNav.class));
                        finish();

                    }else{

                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }



                }
            }
        };
        timer.start();


    }
}