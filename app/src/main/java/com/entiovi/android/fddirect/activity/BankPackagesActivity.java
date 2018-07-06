package com.entiovi.android.fddirect.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.entiovi.android.fddirect.R;
import com.entiovi.android.fddirect.adapter.BankAdapter;
import com.entiovi.android.fddirect.models.BankModel;

import java.util.ArrayList;
import java.util.Collections;

public class BankPackagesActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private boolean ascending = false;
    private ProgressDialog dialog;
    private TextView tv_interest;

    private String amountEntered, tenorEntered;
    private EditText et_principle, et_tenor;
    private double principle;
    private int tenure;
    double rate = 1.92;

    private ArrayList<BankModel> bankList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BankAdapter adapter;

    private DrawerLayout mDrawerLayout;

    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_packages);

        initViews();
        bundle = getIntent().getExtras();
        et_principle.setText(bundle.getString("Amount"));
        et_tenor.setText(bundle.getString("Tenure"));

        principle = Double.parseDouble(et_principle.getText().toString());
        tenure = Integer.parseInt(et_tenor.getText().toString());

        bankList = getBankList();
        recyclerView = findViewById(R.id.package_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BankAdapter(bankList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BankAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(BankModel model) {
                proceedDialog(model.getBankName(),model.getBank_logo());
            }
        });
    }

    public ArrayList<BankModel> getBankList(){
        ArrayList<BankModel> list = new ArrayList<>();
        int logos[] = BankModel.getLogos();
        String bankNames[] = BankModel.getBankNames();

        for(int i=0;i<4;i++){
            BankModel model = new BankModel();
            model.setBank_logo(logos[i]);
            model.setBankName(bankNames[i]);
            model.setInterest(rate);
            model.setTenure(tenure);
            model.setAmount(calculateMaturityAmount(principle,tenure));
            list.add(model);
        }
        return list;
    }

    private double calculateMaturityAmount(double principle, double tenure){
        double maturityAmount = 0.0;
        double  interest = principle*(Math.pow((1 + rate/100), tenure) - 1);
        int interestAmt = (int) (Math.round(interest * 100D) / 100D);
        maturityAmount = principle + interestAmt;
        return maturityAmount;
    }

    private void proceedDialog(final String bankName, int icon) {

        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(true);
        dialog.show();

        TextView message = dialogView.findViewById(R.id.dialog_message);
        ImageView bankIcon = dialogView.findViewById(R.id.dialog_icon);
        Button btn_agree = dialogView.findViewById(R.id.ok);
        Button btn_disagree = dialogView.findViewById(R.id.cancel);

        message.setText(String.format("Do you have a Savings account in %s", bankName));
        bankIcon.setBackgroundResource(icon);
        btn_agree.setText("YES");
        btn_disagree.setText("NO");

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FDOpeningActivity.class);
                intent.putExtra("BankName", bankName);
                intent.putExtra("Amount", bundle.getString("Amount"));
                intent.putExtra("Tenure", bundle.getString("Tenure"));
                startActivity(intent);
            }
        });

        btn_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccountOpeningActivity.class);
                intent.putExtra("BankName", bankName);
                intent.putExtra("Amount", bundle.getString("Amount"));
                intent.putExtra("Tenure", bundle.getString("Tenure"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.amount_research:
                //TODO send request & receive response
                initViews();
                amountEntered = et_principle.getText().toString();
                tenorEntered = et_tenor.getText().toString();
                break;
            case R.id.tenor_research:
                //TODO send request & receive response
                initViews();
                amountEntered = et_principle.getText().toString();
                tenorEntered = et_tenor.getText().toString();
                break;
            case R.id.interest:
                //TODO sorting
                sortData(ascending);
                ascending = !ascending;
                break;
            case R.id.show_more:
                //TODO
                recyclerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                recyclerView.requestLayout();
                break;
        }
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

    private void initViews() {
        et_principle = findViewById(R.id.amount_entered);
        et_tenor = findViewById(R.id.tenor_entered);
        findViewById(R.id.amount_research).setOnClickListener(this);
        findViewById(R.id.tenor_research).setOnClickListener(this);
        tv_interest = findViewById(R.id.interest);
        tv_interest.setOnClickListener(this);
        findViewById(R.id.show_more).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView drawer = findViewById(R.id.nav_view);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        drawer.setNavigationItemSelectedListener(this);
    }

    private void sortData(boolean asc) {
        if (asc) {
            Collections.reverse(bankList);
            tv_interest.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.desc_sort, 0);
        } else {
            Collections.reverse(bankList);
            tv_interest.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.asc_sort, 0);
        }

        adapter = new BankAdapter(bankList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        final Dialog exitDialog = new Dialog(this, android.R.style.Theme_Dialog);
        exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        exitDialog.setContentView(dialogView);
        exitDialog.setCancelable(false);
        exitDialog.show();

        TextView message = dialogView.findViewById(R.id.dialog_message);
        Button btn_agree = dialogView.findViewById(R.id.ok);
        Button btn_disagree = dialogView.findViewById(R.id.cancel);

        message.setText(getString(R.string.exit_alert));
        btn_agree.setText(getString(R.string.close));
        btn_disagree.setText(getString(R.string.cancel));

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
