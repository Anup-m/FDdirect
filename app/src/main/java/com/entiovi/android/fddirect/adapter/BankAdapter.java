package com.entiovi.android.fddirect.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.entiovi.android.fddirect.AccountOpeningActivity;
import com.entiovi.android.fddirect.Banks;
import com.entiovi.android.fddirect.R;

import java.util.ArrayList;


public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankHolder> {

    private ArrayList<Banks> android;
    private Context context;
    private String amountEntered, tenureEntered;

    public BankAdapter(ArrayList<Banks> android, Context context, String amountEntered, String tenureEntered) {
        this.android = android;
        this.context = context;
        this.amountEntered = amountEntered;
        this.tenureEntered = tenureEntered;
    }

    @NonNull
    @Override
    public BankAdapter.BankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bankView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_package, parent, false);
        return new BankHolder(bankView);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAdapter.BankHolder holder, int position) {
        final BankAdapter.BankHolder bankHolder = holder;
        bankHolder.interest.setText(android.get(position).getName());
        bankHolder.tenure.setText(android.get(position).getVer());
        bankHolder.amount.setText(android.get(position).getApi());

        bankHolder.proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setCancelable(false)
                        .setMessage("Do you have a Savings account in "+ bankHolder.bankName.getText().toString())
                        .setPositiveButton("YES", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO
                            }
                        })
                        .setNeutralButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(view.getContext(), AccountOpeningActivity.class);
                                intent.putExtra("BankName", bankHolder.bankName.getText().toString());
                                intent.putExtra("Amount", amountEntered);
                                intent.putExtra("Tenure", tenureEntered);
                                context.startActivity(intent);
                            }
                        }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class BankHolder extends RecyclerView.ViewHolder {

        private ImageView bankLogo;
        private TextView bankName, interest, tenure, amount;
        private Button proceed;
        CardView bankCard;

        public BankHolder(View itemView){
            super(itemView);
            bankLogo = itemView.findViewById(R.id.bank_logo);
            bankName = itemView.findViewById(R.id.bank_name);
            interest = itemView.findViewById(R.id.interest_rate);
            tenure = itemView.findViewById(R.id.fd_tenure);
            amount = itemView.findViewById(R.id.fd_amount);
            proceed = itemView.findViewById(R.id.proceed);
            bankCard = itemView.findViewById(R.id.bank_card);
        }
    }
}