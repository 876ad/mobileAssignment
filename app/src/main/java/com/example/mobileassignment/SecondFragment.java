package com.example.mobileassignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mobileassignment.databinding.FragmentFirstBinding;
import com.example.mobileassignment.databinding.FragmentSecondBinding;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.zip.Inflater;


public class SecondFragment extends Fragment {

    private FragmentFirstBinding binding;
    TextView showTextView1;
    private EditText input1;
    private EditText input2;
    private EditText input3;
    private TextView EMI_res;

    boolean fieldsFilled = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View fragmentSecondLayout = inflater.inflate(R.layout.fragment_second, container, false);
        showTextView1 = fragmentSecondLayout.findViewById(R.id.button_second);


        //input of loan amount
        input1 = (EditText) fragmentSecondLayout.findViewById(R.id.L_Size);

        //input of interest rate in %
        input2 = (EditText) fragmentSecondLayout.findViewById(R.id.R_Size);

        //input of tenure of loan in months
        input3 = (EditText) fragmentSecondLayout.findViewById(R.id.T_Size);

        //button to calc EMI
        Button calcEMI = (Button) fragmentSecondLayout.findViewById(R.id.calcEMI);

        EMI_res = (TextView) fragmentSecondLayout.findViewById(R.id.EMI_res);

        //listener to do calculations from input
        calcEMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //checks that all fields have been populated before calculation is run
                    validator();
                }
        });



        return fragmentSecondLayout;


    }
    public void validator(){

        if (input1.length() == 0) {
                input1.setError("Enter loan amount");
            }
                else if (input2.length() == 0){
                    input2.setError("Enter the rate of interest");
                }
                else if (input3.length() == 0 ){
                    input3.setError("Enter the amount tenure in months");
                }
                else{

            EmiCalc();}
    }
    public void EmiCalc()
    {
        //Below is the equation to calculate EMI:
        //EMI = P x r x ( 1 + r )n / ( ( 1 + r )n - 1 )

        //pAmount: the loan amount, it is P in the equation
        double pAmount = Double.valueOf(input1.getText().toString());
        //iRate: the interest rate per annum in %,
        double iRate = Double.valueOf(input2.getText().toString());
        //the duration of the loan in months, it is n in the equation
        double Tenure = Double.valueOf(input3.getText().toString());

        //numerator of equation
        double numerator;
        //denominator of equation
        double denom;
        //this is r in the equation
        double rAct;

        //calculation of EMI before rounding
        double E;
        //result after rounding
        double EMI;

        //as the rate is per year and in %, so this is to get the true amount
        rAct = iRate/12/100;
        numerator = (pAmount*rAct)*(Math.pow((1+rAct),Tenure));
        denom = (Math.pow((1+rAct),Tenure)) - 1;
        E=numerator/denom;

        //to get 2 decimal places
        EMI =  Math.round(E*100.0)/100.0;

        EMI_res.setText("Your EMI is: $" + EMI);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //button which navigates to home page
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}