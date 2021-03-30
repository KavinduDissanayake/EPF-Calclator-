package com.example.epfetfbalancecheckersbewl.Screen.Sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.epfetfbalancecheckersbewl.HomeNav;
import com.example.epfetfbalancecheckersbewl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email_edit;
    EditText password_edit;

    ProgressBar progressBar3;



    public FirebaseAuth mAuth;




    //session preparing for Set the values
    SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {

        mAuth = FirebaseAuth.getInstance();


        email_edit=findViewById(R.id.email_edit);
        password_edit=findViewById(R.id.password_edit);
        progressBar3=findViewById(R.id.progressBar3);
    }

    public void goToRegister(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

    }

    public void Login(View view) {


        String email_c = email_edit.getText().toString();
        String passl_c = password_edit.getText().toString();

        if (TextUtils.isEmpty(email_c)) {
            email_edit.setError("Email is Required!");
            return;
        }
        if (TextUtils.isEmpty(passl_c)) {
            password_edit.setError("Password is Required!");
            return;
        }



        progressBar3.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email_c, passl_c)

                .addOnCompleteListener(this,(task) -> {

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success.");
                        FirebaseUser user = mAuth.getCurrentUser();


                        //set the session
                        sharedpreferences =getSharedPreferences("user_details", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("isLoggedIn",true);
                        editor.commit();



                        startActivity(new Intent(LoginActivity.this, HomeNav.class));
                        finish();
                    } else {


                        progressBar3.setVisibility(View.INVISIBLE);

                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.please re enter correct email and password.",
                                Toast.LENGTH_SHORT).show();
                        // updateUI(null);
                    }

                    // ...

                });

    }
}