package com.example.midni.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener{

    //declare your variables for the widgets
    private EditText editTextBillAmount;
    private TextView tipAmount;
    private TextView totalAmount;
    private TextView textViewBillAmount;
    private SeekBar tipBar;
    private TextView tipPercentage;
    //declare the variables for the calculations
    private double billAmount = 0.0;
    private double percent = .10;

    //set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add Listeners to Widgets
        editTextBillAmount = (EditText)findViewById(R.id.editText_BillAmount);
        editTextBillAmount.addTextChangedListener((TextWatcher) this);
        tipPercentage = findViewById(R.id.percentageTextView);
        tipBar = (SeekBar)findViewById(R.id.seekBar);
        tipBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)this);
        textViewBillAmount = (TextView)findViewById(R.id.textView_BillAmount);
        tipAmount = findViewById(R.id.tipTV);
        totalAmount = findViewById(R.id.totalTV);

        Log.d("Main Activity", " TEXT CHANGED");


        textViewBillAmount = (TextView)findViewById(R.id.textView_BillAmount);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /*
    Note:   int i, int i1, and int i2
            represent start, before, count respectively
            The charSequence is converted to a String and parsed to a double for you
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= " + charSequence);
        //surround risky calculations with try catch (what if billAmount is 0 ?
        try {
            //charSequence is converted to a String and parsed to a double for you
            billAmount = Double.parseDouble(charSequence.toString()) / 100;
            Log.d("MainActivity", "Bill Amount = " + billAmount);
            //setText on the textView
            textViewBillAmount.setText(currencyFormat.format(billAmount));
            //perform tip and total calculation and update UI by calling calculate
            calculate();//uncomment this line
        } catch(Exception e){
            Log.d("MainActivity", "INVALID ENTRY");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if(progress<15){
            progress = 10;
        }
        else if(progress<20){
            progress = 15;
        }
        else if(progress<25){
            progress = 20;
        }
        else if(progress<30){
            progress = 25;
        }
        percent = progress / 100.0; //calculate percent based on seeker value

       calculate();
        Log.d("MainActivity", "onProgressChanged: "+percent);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tipPercentage.setText(percentFormat.format(percent));
    }

    // calculate and display tip and total amounts
    private void calculate() {
        Log.d("MainActivity", "inside calculate method");
        //uncomment below

       // format percent and display in percentTextView
      tipPercentage.setText(percentFormat.format(percent));

       // calculate the tip and total
       double tip = billAmount * percent;
      //use the tip example to do the same for the Total

       // display tip and total formatted as currency
       //user currencyFormat instead of percentFormat to set the textViewTip
       tipAmount.setText(currencyFormat.format(tip));
       //use the tip example to do the same for the Total
       double billTotal = tip+billAmount;
       totalAmount.setText(currencyFormat.format(billTotal));

    }
}
