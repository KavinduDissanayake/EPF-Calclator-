
package com.example.epfetfbalancecheckersbewl.Screen.Home;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.epfetfbalancecheckersbewl.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {


    View rootView;


   // double Basic_Salary = 5000.00;


    TextView textBas_Salary;
    TextView textEPF;
    TextView textETF;
    TextView textEPF_12;
    TextView text_total_EPF_ETF;
    TextView text_total;
    TextView state;
    CollapsingToolbarLayout collapsingToolbarLayout;



    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        rootView = inflater.inflate(R.layout.home_fragment, container, false);

        init();

//load data from thedatabase
        LoadData();



        return rootView;
    }

    private void LoadData() {
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
                String basic_salary = dataSnapshot.child("basic_salary").getValue(String.class);

                collapsingToolbarLayout.setTitle("Hello ! "+firstname );
                collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getContext(), R.color.white));
                collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getContext(), R.color.white));


                try {
                    cal_and_view_salary(Double.parseDouble(basic_salary));

                }catch (Exception e){

                    state.setVisibility(View.VISIBLE);


                }





//                Lastname.setText(lastname+"");
//                NIC.setText(nic+"");
//                Birthday.setText(birthday+"");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        yourRef.addListenerForSingleValueEvent(eventListener);

    }



public  void  cal_and_view_salary(double Basic_Salary){

    //set the value to view
      textBas_Salary.setText("RS: " + Basic_Salary + "");


    Log.d("User Salary",Basic_Salary+"");

    textEPF.setText("RS: " + calculateEPF(Basic_Salary) + "");
    textETF.setText("RS: " + calculateEFP(Basic_Salary) + "");
    textEPF_12.setText("RS: " + calculateEPF_12_Present(Basic_Salary) + "");


    double total_Epf_ETF = calculateEPF(Basic_Salary) + calculateEFP(Basic_Salary) +calculateEPF_12_Present(Basic_Salary);
    text_total_EPF_ETF.setText("RS: " + total_Epf_ETF + "");


    double total_salary = Basic_Salary + total_Epf_ETF;
    text_total.setText("RS: " + total_salary + "");

}






    private void init() {
        textBas_Salary=rootView.findViewById(R.id.textBas_Salary);
        textEPF=rootView.findViewById(R.id.textEPF);
        textETF=rootView.findViewById(R.id.textETF);
        text_total_EPF_ETF=rootView.findViewById(R.id.text_total_EPF_ETF);
        text_total=rootView.findViewById(R.id.text_total);
        state=rootView.findViewById(R.id.state);


        textEPF_12=rootView.findViewById(R.id.textEPF_12);

        collapsingToolbarLayout=rootView.findViewById( R.id.collapsingToolbarLayoutProEdit );


    }


    //calculate EPF 8%
    public double calculateEPF( double basic_Salary){
        return basic_Salary*(1.08);
    }


    //calculate EPF 12%
    public double calculateEPF_12_Present( double basic_Salary){
        return basic_Salary*(1.12);
    }

    //calculate EFP
    public double calculateEFP( double basic_Salary){
        return basic_Salary*(1.03);
    }


}