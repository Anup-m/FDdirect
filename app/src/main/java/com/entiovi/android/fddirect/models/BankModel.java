package com.entiovi.android.fddirect.models;

import com.entiovi.android.fddirect.R;

public class BankModel {

    private double interest, amount;
    private int bank_logo;
    private int tenure;
    private String bankName;

    public BankModel() { }

    public BankModel(double interest, double amount, int tenure) {
        setInterest(interest);
        setAmount(amount);
        setTenure(tenure);
    }

    public static int[] getLogos(){
        return new int[]{ R.drawable.citibank_logo, R.drawable.tienphong_bank, R.drawable.vietinbank_logo,R.drawable.vietcom_bank };
    }

    public static String[] getBankNames(){
        return new String[]{ "CitiBank","TienPhong Bank","Vietin Bank","Vietcom Bank"};
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getBank_logo() {
        return bank_logo;
    }

    public void setBank_logo(int bank_logo) {
        this.bank_logo = bank_logo;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }
}
