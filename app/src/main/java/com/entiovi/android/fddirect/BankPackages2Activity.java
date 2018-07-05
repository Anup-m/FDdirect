package com.entiovi.android.fddirect;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BankPackages2Activity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView drawer;
    private EditText amount, tenor;
    private String amountEntered, tenorEntered;
    private TextView bank1Tenure, bank1Amount, bank2Tenure, bank2Amount, bank3Tenure, bank3Amount, bank4Tenure, bank4Amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_packages2);

        initializeViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        drawer.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        amount.setText(bundle.getString("Amount"));
        tenor.setText(bundle.getString("Tenure"));

        amountEntered = amount.getText().toString();
        tenorEntered = tenor.getText().toString();

        double amountInInt = Integer.parseInt(amountEntered);
        double tenureInInt = Integer.parseInt(tenorEntered);
        inflateAmount(amountInInt, tenureInInt);

        findViewById(R.id.bank1_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedDialog("CitiBank", R.drawable.citibank_logo);
            }
        });
        findViewById(R.id.bank2_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedDialog("TienPhong Bank", R.drawable.tienphong_bank);
            }
        });
        findViewById(R.id.bank3_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedDialog("Vietin Bank", R.drawable.vietinbank_logo);
            }
        });
        findViewById(R.id.bank4_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedDialog("Vietcom Bank", R.drawable.vietcom_bank);
            }
        });
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        drawer = findViewById(R.id.nav_view);
        amount = findViewById(R.id.amount_entered);
        tenor = findViewById(R.id.tenor_entered);
        findViewById(R.id.amount_research).setOnClickListener(this);
        findViewById(R.id.tenor_research).setOnClickListener(this);
        bank1Tenure = findViewById(R.id.bank1_fd_tenure);
        bank1Amount = findViewById(R.id.bank1_fd_amount);
        bank2Tenure = findViewById(R.id.bank2_fd_tenure);
        bank2Amount = findViewById(R.id.bank2_fd_amount);
        bank3Tenure = findViewById(R.id.bank3_fd_tenure);
        bank3Amount = findViewById(R.id.bank3_fd_amount);
        bank4Tenure = findViewById(R.id.bank4_fd_tenure);
        bank4Amount = findViewById(R.id.bank4_fd_amount);
        findViewById(R.id.show_more).setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_investment_history:
                Toast.makeText(getApplicationContext(), "Not Implemented", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_about:
                Toast.makeText(getApplicationContext(), "Not Implemented", Toast.LENGTH_LONG).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.amount_research:
                searchAgain();
                break;
            case R.id.tenor_research:
                searchAgain();
                break;
            case R.id.show_more:
                Toast.makeText(BankPackages2Activity.this, "No more items to show right now!", Toast.LENGTH_LONG).show();
        }
    }

    private void inflateAmount(double amountInInt, double tenureInInt) {
        double tenureInYear = tenureInInt/365f;
        bank1Tenure.setText(tenorEntered+" Days");
        double rateOfBank1 = amountInInt*(Math.pow((1 + 1.92/100), tenureInYear) - 1);
        int rateOfBank1Rounded = (int) (Math.round(rateOfBank1 * 100D) / 100D);
        int totalAmount1 = Integer.parseInt(amountEntered)+rateOfBank1Rounded;
        bank1Amount.setText(amountEntered+"\n+"+String.valueOf(rateOfBank1Rounded)+"\n="+String.valueOf(totalAmount1));
        bank2Tenure.setText(tenorEntered+" Days");
        double rateOfBank2 = amountInInt*(Math.pow((1 + 1.92/100), tenureInYear) - 1);
        int rateOfBank2Rounded = (int) (Math.round(rateOfBank2 * 100D) / 100D);
        int totalAmount2 = Integer.parseInt(amountEntered)+rateOfBank2Rounded;
        bank2Amount.setText(amountEntered+"\n+"+String.valueOf(rateOfBank2Rounded)+"\n="+String.valueOf(totalAmount2));
        bank3Tenure.setText(tenorEntered+" Days");
        double rateOfBank3 = amountInInt*(Math.pow((1 + 1.91/100), tenureInYear) - 1);
        int rateOfBank3Rounded = (int) (Math.round(rateOfBank3 * 100D) / 100D);
        int totalAmount3 = Integer.parseInt(amountEntered)+rateOfBank3Rounded;
        bank3Amount.setText(amountEntered+"\n+"+String.valueOf(rateOfBank3Rounded)+"\n="+String.valueOf(totalAmount3));
        bank4Tenure.setText(tenorEntered+" Days");
        double rateOfBank4 = amountInInt*(Math.pow((1 + 1.90/100), tenureInYear) - 1);
        int rateOfBank4Rounded = (int) (Math.round(rateOfBank4 * 100D) / 100D);
        int totalAmount4 = Integer.parseInt(amountEntered)+rateOfBank4Rounded;
        bank4Amount.setText(amountEntered+"\n+"+String.valueOf(rateOfBank4Rounded)+"\n="+String.valueOf(totalAmount4));
    }

    private void proceedDialog(final String bankName, int icon) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        final Dialog dialog = new Dialog(BankPackages2Activity.this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(true);
        dialog.show();

        TextView message = dialogView.findViewById(R.id.dialog_message);
        ImageView bankIcon = dialogView.findViewById(R.id.dialog_icon);
        Button btn_agree = dialogView.findViewById(R.id.ok);
        Button btn_disagree = dialogView.findViewById(R.id.cancel);

        message.setText("Do you have a Savings account in "+bankName);
        bankIcon.setBackgroundResource(icon);
        btn_agree.setText("YES");
        btn_disagree.setText("NO");

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FDOpeningActivity.class);
                intent.putExtra("BankName", bankName);
                intent.putExtra("Amount", amountEntered);
                intent.putExtra("Tenure", tenorEntered);
                startActivity(intent);
            }
        });

        btn_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccountOpeningActivity.class);
                intent.putExtra("BankName", bankName);
                intent.putExtra("Amount", amountEntered);
                intent.putExtra("Tenure", tenorEntered);
                startActivity(intent);
            }
        });
    }

    private void searchAgain() {
        int amountInInt, tenureInInt;
        amountEntered = amount.getText().toString();
        tenorEntered = tenor.getText().toString();
        if(!amountEntered.isEmpty() && !tenorEntered.isEmpty()) {
            if(Integer.parseInt(tenor.getText().toString()) >= 7 && Integer.parseInt(tenor.getText().toString()) <= 3650) {
                amountInInt = Integer.parseInt(amountEntered);
                tenureInInt = Integer.parseInt(tenorEntered);
                inflateAmount(amountInInt, tenureInInt);
            }
            else {
                Toast.makeText(getApplicationContext(), "Tenure must be between 7 days & 3650 days!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Amount & Tenure cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        final Dialog exitDialog = new Dialog(BankPackages2Activity.this, android.R.style.Theme_Dialog);
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