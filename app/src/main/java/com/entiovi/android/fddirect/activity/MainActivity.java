package com.entiovi.android.fddirect.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.entiovi.android.fddirect.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String amount_Text = "";
    private String tenor_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this is the onCreate of main screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button invest_now = findViewById(R.id.invest_now);
        invest_now.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        /*on clicking invest now button a dialog asking investment amount & tenure will show up
        (both are compulsory)*/
        View dialogView = ViewGroup.inflate(MainActivity.this, R.layout.input_alert, null);
        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        dialog.show();

        //elements in the dialog
        final EditText amount = dialogView.findViewById(R.id.amount);
        final EditText tenor = dialogView.findViewById(R.id.tenor);
        final Button btn_agree = dialogView.findViewById(R.id.ok);
        ImageButton btn_disagree = dialogView.findViewById(R.id.cancel);

        /*detects text change one the amonut & tenure field and
        enables the submit button, if both are filled
        */
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    btn_agree.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tenor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    btn_agree.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!amount.getText().toString().isEmpty() && !tenor.getText().toString().isEmpty()){
                    if(Integer.parseInt(tenor.getText().toString()) >= 7 && Integer.parseInt(tenor.getText().toString()) <= 3650) {
                        dialog.dismiss();
                        amount_Text = amount.getText().toString();
                        tenor_Text = tenor.getText().toString();
                        Intent intent = new Intent(MainActivity.this, BankPackagesActivity.class);
                           intent.putExtra("Amount", amount_Text);
                        intent.putExtra("Tenure", tenor_Text);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Tenure must be between 7 days & 3650 days!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Please Enter Amount & Tenure to Continue", Toast.LENGTH_LONG).show();
                    btn_agree.setEnabled(false);
                }
            }
        });

        btn_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        final Dialog exitDialog = new Dialog(MainActivity.this, android.R.style.Theme_Dialog);
        exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        exitDialog.setContentView(dialogView);
        exitDialog.setCancelable(false);
        exitDialog.show();

        TextView message = dialogView.findViewById(R.id.dialog_message);
        Button btn_agree = dialogView.findViewById(R.id.ok);
        Button btn_disagree = dialogView.findViewById(R.id.cancel);

        message.setText("Sure you want to exit!");
        btn_agree.setText("Close");
        btn_disagree.setText("Cancel");

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        btn_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });
    }
}
