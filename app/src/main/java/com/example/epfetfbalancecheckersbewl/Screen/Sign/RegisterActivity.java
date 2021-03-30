package com.example.epfetfbalancecheckersbewl.Screen.Sign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.epfetfbalancecheckersbewl.R;
import com.example.epfetfbalancecheckersbewl.Screen.Model.Profile_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {


    EditText emp_num_edit;
    EditText email_edit;
    EditText first_name_edit;
    EditText last_name_edit;
    EditText id_edit;
    EditText phone_number_edit;
    EditText password_edit;



    public FirebaseAuth mAuth;

    DatabaseReference reference;
    FirebaseDatabase rootNode;

    Button RegisterBtn;
   ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();




        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterUser();
            }
        });

    }

    private void RegisterUser() {



        String  emp_num=emp_num_edit.getText().toString();
        String  email_create=emp_num_edit.getText().toString();

        String  first_name=first_name_edit.getText().toString();
        String  last_name=last_name_edit.getText().toString();
        String  nic=id_edit.getText().toString();

        String password_create=password_edit.getText().toString();




        //user fields validation

        if (TextUtils.isEmpty(emp_num)) {
            emp_num_edit.setError("Employee Number is Required!");
            return;
        }
        if (TextUtils.isEmpty(email_create)) {
            email_edit.setError("Email is Required!");
            return;
        }

        if (TextUtils.isEmpty(first_name)) {
            first_name_edit.setError("First  Name is Required!");
            return;
        }
        if (TextUtils.isEmpty(last_name)) {
            last_name_edit.setError("Last Name is Required!");
            return;
        }

        if (TextUtils.isEmpty(nic)) {
            id_edit.setError("Nic  is Required!");
            return;
        }



        if (TextUtils.isEmpty(password_create)) {
            password_edit.setError("Password is Required !");

            return;
        }

        if (password_edit.length() < 8) {
            password_edit.setError("Password must be 8 characters!");
            return;
        }
//
//

//                  Log.d("user Name",emp_num_edit.getText().toString())   ;
//                  Log.d("password_edit ",password_edit.getText().toString())   ;
//
        // User Register to Database                                                                                   

    progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email_edit.getText().toString(), password_edit.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();

//                            final String  userID=mAuth.getCurrentUser().getUid();




                            rootNode = FirebaseDatabase.getInstance();
                            reference= FirebaseDatabase.getInstance().getReference();
                            reference = rootNode.getReference("UserData");

                            Profile_model Profile_model = new Profile_model(
                                    FirebaseAuth.getInstance().getUid(),
                                    emp_num_edit.getText().toString(),
                                    first_name_edit.getText().toString(),
                                    last_name_edit.getText().toString(),
                                    id_edit.getText().toString(),
                                    " ",
                                    " "


                            );


                            reference.child(FirebaseAuth.getInstance().getUid()).setValue(Profile_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();


                                    startActivity( new Intent(RegisterActivity.this,LoginActivity.class));

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Data Inserted Failed!", Toast.LENGTH_SHORT).show();


                                }
                            });

                            //updateUI(user);
                        } else {
                                       progressBar.setVisibility(View.INVISIBLE);
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //  updateUI(null);
                        }

                        // ...
                    }
                });


    }

    private void init() {


        emp_num_edit=findViewById(R.id.emp_num_edit);
        email_edit=findViewById(R.id.email_edit);
        first_name_edit=findViewById(R.id.first_name_edit);
        last_name_edit=findViewById(R.id.last_name_edit);
        id_edit=findViewById(R.id.id_edit);
       // phone_number_edit=findViewById(R.id.phone_number_edit);
        RegisterBtn=findViewById(R.id.RegisterBtn);
        password_edit=findViewById(R.id.password_edit);
        progressBar=findViewById(R.id.progressBar);



//Firebase init
        mAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();
        reference= FirebaseDatabase.getInstance().getReference();
        reference = rootNode.getReference("UserData");
    }

    public void goTologin(View view) {


          startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}