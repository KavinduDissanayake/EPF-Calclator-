package com.example.epfetfbalancecheckersbewl.Screen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epfetfbalancecheckersbewl.R;
import com.example.epfetfbalancecheckersbewl.Screen.Model.Profile_model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
View rootView;


EditText emp_num_edit;
EditText email_edit;
EditText first_name_edit;
EditText last_name_edit;
EditText id_edit;
EditText phone_number_edit;
Button upDate;
ImageView userImageView;
ImageButton imageButton;

TextView salary;
 private static final int PReqCode = 2 ;







//database reference

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    //to store the image for database

    StorageReference storageReference;



    DatabaseReference databaseReference ;
    FirebaseDatabase firebaseDatabase;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView=inflater.inflate(R.layout.profile_fragment, container, false);
        init();




        //image picker

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAndRequestForPermission();
                Intent OpenGalleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(OpenGalleryIntent,1000);
            }
        });






        //insert data
        upDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //insert Query
                rootNode = FirebaseDatabase.getInstance();
                reference=FirebaseDatabase.getInstance().getReference();
                reference = rootNode.getReference("UserData");

                //Toast.makeText(ProfileActivity.this,Firstname.getText().toString(),Toast.LENGTH_SHORT);


                Profile_model Profile_model = new Profile_model(
                        FirebaseAuth.getInstance().getUid(),
                        emp_num_edit.getText().toString(),
                        first_name_edit.getText().toString(),
                        last_name_edit.getText().toString(),
                        id_edit.getText().toString(),
                        phone_number_edit.getText().toString(),
                        salary.getText().toString()

                );


                reference.child(FirebaseAuth.getInstance().getUid()).setValue(Profile_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Data Update Successfully!", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Data Update Failed!", Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });



        LoadProfileData();
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        LoadProfileData();
    }

    private void LoadProfileData() {

        //loading image from the database

        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("Users/"+FirebaseAuth.getInstance().getUid()+"/Profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get().load(uri)
                        .into(userImageView);

            }
        });



        //to load the data from th edatabase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserData");

        //reference to database by current user id

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference yourRef = rootRef.child("UserData").child(FirebaseAuth.getInstance().getUid());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // String birthday = dataSnapshot.child("birthday").getValue(String.class);
                String firstname = dataSnapshot.child("firstname").getValue(String.class);
                String lastname = dataSnapshot.child("lastname").getValue(String.class);
                String emo_id = dataSnapshot.child("emo_id").getValue(String.class);
                String nic_id = dataSnapshot.child("nic_id").getValue(String.class);
                String phone_number = dataSnapshot.child("phone_number").getValue(String.class);
                String basic_salary = dataSnapshot.child("basic_salary").getValue(String.class);


                salary.setText(basic_salary+"");
                first_name_edit.setText(firstname+"");
                last_name_edit.setText(lastname+"");
                id_edit.setText(nic_id+"");
                emp_num_edit.setText(emo_id+"");
                phone_number_edit.setText(phone_number+"");
//                Lastname.setText(lastname+"");
//                NIC.setText(nic+"");
//                Birthday.setText(birthday+"");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        yourRef.addListenerForSingleValueEvent(eventListener);

    }




    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(getContext(),"Please accept for required permission", Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }


            return;
        }else{

        }

    }
    private void init() {


        rootNode = FirebaseDatabase.getInstance();
        reference=FirebaseDatabase.getInstance().getReference();
        reference = rootNode.getReference("UserData");


        emp_num_edit=rootView.findViewById(R.id.emp_num_edit);
       // email_edit=rootView.findViewById(R.id.email_edit);
        first_name_edit=rootView.findViewById(R.id.first_name_edit);
        last_name_edit=rootView.findViewById(R.id.last_name_edit);
        id_edit=rootView.findViewById(R.id.id_edit);
        phone_number_edit=rootView.findViewById(R.id.phone_number_edit);
        upDate=rootView.findViewById(R.id.upDate);
        userImageView=rootView.findViewById(R.id.userImageView);
        imageButton=rootView.findViewById(R.id.imageButton);
        salary=rootView.findViewById(R.id.salary);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri=data.getData();
                // image_user_profile.setImageURI(imageUri);


              uploadImageToFirebase(imageUri);

            }

        }
    }




    private void uploadImageToFirebase(Uri imageUri) {



        storageReference= FirebaseStorage.getInstance().getReference();
        //uploade image to firebase storage
        final StorageReference fileRef=storageReference.child("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                //retriving
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        Picasso.get().load(uri).into(userImageView);


                        //to set picked data to current user data



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Image Not Retive !",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Image Uploaded is Failed",Toast.LENGTH_SHORT).show();
            }
        });


    }

}