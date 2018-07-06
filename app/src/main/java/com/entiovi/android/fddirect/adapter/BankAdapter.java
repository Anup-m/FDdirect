package com.entiovi.android.fddirect.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.entiovi.android.fddirect.R;
import com.entiovi.android.fddirect.models.BankModel;

import java.util.ArrayList;


public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankHolder> {

    private ArrayList<BankModel> banksList;
    private OnItemClickListener onItemClickListener;

    public BankAdapter(ArrayList<BankModel> banks) {
        this.banksList = banks;
    }

    public interface OnItemClickListener{
        void OnItemClick(BankModel bankModel);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public BankAdapter.BankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bankView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_card_item, parent, false);
        return new BankHolder(bankView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BankAdapter.BankHolder holder, final int position) {
        final BankAdapter.BankHolder bankHolder = holder;

        bankHolder.interest.setText(String.valueOf(banksList.get(position).getInterest()));
        bankHolder.tenure.setText(String.valueOf(banksList.get(position).getTenure()));
        bankHolder.amount.setText(String.valueOf(banksList.get(position).getAmount()));
        bankHolder.bankLogo.setImageResource(banksList.get(position).getBank_logo());
        bankHolder.bankName = banksList.get(position).getBankName();

        bankHolder.proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(onItemClickListener != null){
                    onItemClickListener.OnItemClick(banksList.get(position));
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return banksList.size();
    }

    public class BankHolder extends RecyclerView.ViewHolder {

        private ImageView bankLogo;
        private TextView interest, tenure, amount;
        private Button proceed;
        //private CardView bankCard;
        private String bankName;


        public BankHolder(View itemView){
            super(itemView);
            bankLogo = itemView.findViewById(R.id.bank_logo);
            interest = itemView.findViewById(R.id.interest_rate);
            tenure = itemView.findViewById(R.id.fd_tenure);
            amount = itemView.findViewById(R.id.fd_amount);
            proceed = itemView.findViewById(R.id.proceed);
            //bankCard = itemView.findViewById(R.id.bank_card);
        }
    }
}