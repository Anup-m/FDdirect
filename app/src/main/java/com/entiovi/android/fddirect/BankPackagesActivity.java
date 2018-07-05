package com.entiovi.android.fddirect;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.entiovi.android.fddirect.adapter.BankAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BankPackagesActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ArrayList<Banks> data;
    private boolean ascending = false;
    private BankAdapter adapter;
    private ProgressDialog dialog;
    private EditText amount, tenor;
    private String amountEntered, tenorEntered;
    private TextView interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_packages);

        amount = findViewById(R.id.amount_entered);
        tenor = findViewById(R.id.tenor_entered);
        findViewById(R.id.amount_research).setOnClickListener(this);
        findViewById(R.id.tenor_research).setOnClickListener(this);
        interest = findViewById(R.id.interest);
        interest.setOnClickListener(this);
        findViewById(R.id.show_more).setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        amount.setText(bundle.getString("Amount"));
        tenor.setText(bundle.getString("Tenure"));

        initViews();

        amountEntered = amount.getText().toString();
        tenorEntered = tenor.getText().toString();
    }

    private void initViews() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Fetching Bank List");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        recyclerView = findViewById(R.id.package_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
        //loadImage();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.learn2crack.com")//TODO change base URL as required
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {

            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getBanks()));
                adapter = new BankAdapter(data, BankPackagesActivity.this, amountEntered, tenorEntered);
                recyclerView.setAdapter(adapter);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void loadImage() {
        //TODO
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.amount_research:
                //TODO send request & receive response
                initViews();
                amountEntered = amount.getText().toString();
                tenorEntered = tenor.getText().toString();
                break;
            case R.id.tenor_research:
                //TODO send request & receive response
                initViews();
                amountEntered = amount.getText().toString();
                tenorEntered = tenor.getText().toString();
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

    private void sortData(boolean asc) {
        if (asc) {
            Collections.reverse(data);
            interest.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.desc_sort, 0);
        } else {
            Collections.reverse(data);
            interest.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.asc_sort, 0);
        }

        adapter = new BankAdapter(data, BankPackagesActivity.this, amountEntered, tenorEntered);
        recyclerView.setAdapter(adapter);
    }
}