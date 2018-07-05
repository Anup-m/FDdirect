package com.entiovi.android.fddirect;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Arrays;
import java.util.List;

public class FDOpeningActivity extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle;
    private ScrollView scrollView;
    private TextView bankName, alert;
    private EditText fullName, accountNo, depositAmount, depositPeriod;
    private MaterialBetterSpinner natureOfDeposit, maturityInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdopening);

        bundle = getIntent().getExtras();

        bankName = findViewById(R.id.bank_name);
        bankName.setText("Welcome to "+bundle.getString("BankName")+" Fixed Deposit opening form");
        alert = findViewById(R.id.alert);
        scrollView = findViewById(R.id.scrollView);
        fullName = findViewById(R.id.full_name);
        accountNo = findViewById(R.id.acc_no);
        depositAmount = findViewById(R.id.deposit_amount);
        natureOfDeposit = findViewById(R.id.nod);
        depositPeriod = findViewById(R.id.deposit_period);
        maturityInstruction = findViewById(R.id.maturity_instruction);
        findViewById(R.id.submit).setOnClickListener(this);

        populateSpinner(natureOfDeposit, R.array.nature_of_deposit_array);

        populateSpinner(maturityInstruction, R.array.maturity_instructions_array);
    }

    private void populateSpinner(MaterialBetterSpinner spinner, int resource) {
        List<String> data = Arrays.asList(getResources().getStringArray(resource));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown_items, data);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(validateData()) {
            //TODO
            Toast.makeText(getApplicationContext(),"cleared", Toast.LENGTH_SHORT).show();
        }
        else {
            alert.setVisibility(View.VISIBLE);
            bankName.requestFocus();
        }
    }

    private Boolean validateData() {
        if(fullName.getText().toString().isEmpty()) {
            fullName.setError("Please enter your full name");
            return false;
        }
        if(accountNo.getText().toString().isEmpty()) {
            accountNo.setError("Please enter Account No. to debit from");
            return false;
        }
        if(depositAmount.getText().toString().isEmpty()) {
            depositAmount.setError("Please enter Deposit Amount");
            return false;
        }
        if(natureOfDeposit.getText().toString().isEmpty()) {
            natureOfDeposit.setError("Please specify nature of deposit");
            return false;
        }
        if(depositPeriod.getText().toString().isEmpty()) {
            depositPeriod.setError("Please enter period of deposit");
            return false;
        }
        if(maturityInstruction.getText().toString().isEmpty()) {
            maturityInstruction.setError("Please specify Maturity Instruction");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        final Dialog dialog = new Dialog(FDOpeningActivity.this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(true);
        dialog.show();

        TextView title = dialogView.findViewById(R.id.dialog_title);
        TextView message = dialogView.findViewById(R.id.dialog_message);
        Button btn_agree = dialogView.findViewById(R.id.ok);
        Button btn_disagree = dialogView.findViewById(R.id.cancel);

        title.setText("There are some unsaved data");
        message.setText("If you go back data will be erased..");
        btn_agree.setText("Erase");
        btn_disagree.setVisibility(View.GONE);

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FDOpeningActivity.this, BankPackages2Activity.class);
                intent.putExtra("Amount", bundle.getString("Amount"));
                intent.putExtra("Tenure", bundle.getString("Tenure"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}