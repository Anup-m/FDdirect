package com.entiovi.android.fddirect.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.entiovi.android.fddirect.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class AccountOpeningActivity extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle;
    private ScrollView scrollView;
    private EditText fullName, nationality, visa, identity, poi, phoneNo, eMail, resiAddress, permAddress, overAddress, workPlace, taxPayersCode;
    private CheckBox copyResidentialAddress;
    private TextView bankName, alert, dob, doi;
    private ListView countryList;
    private MaterialBetterSpinner gender, residentialStatus, martialStatus, education, occupation, position;
    private RadioButton usCitizen, nonUsCitizen;
    private Boolean copy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_opening);

        bundle = getIntent().getExtras();

        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        scrollView = findViewById(R.id.scrollView);
        bankName = findViewById(R.id.bank_name);
        bankName.setText("Welcome to "+bundle.getString("BankName")+" account opening form");
        alert = findViewById(R.id.alert);
        fullName = findViewById(R.id.full_name);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        nationality = findViewById(R.id.nationality);
        countryList = findViewById(R.id.country_list);
        visa = findViewById(R.id.visa);
        identity = findViewById(R.id.id);
        doi = findViewById(R.id.doi);
        poi = findViewById(R.id.poi);
        residentialStatus = findViewById(R.id.residential_status);
        martialStatus = findViewById(R.id.martial_status);
        phoneNo = findViewById(R.id.phone);
        eMail = findViewById(R.id.email);
        education = findViewById(R.id.education);
        occupation = findViewById(R.id.occupation);
        position = findViewById(R.id.position);
        copyResidentialAddress = findViewById(R.id.copy_address);
        resiAddress = findViewById(R.id.resi_add);
        permAddress = findViewById(R.id.per_add);
        overAddress = findViewById(R.id.over_add);
        workPlace = findViewById(R.id.workplace);
        usCitizen = findViewById(R.id.us_citizen);
        taxPayersCode = findViewById(R.id.us_tax_code);
        nonUsCitizen = findViewById(R.id.non_us_citizen);
        findViewById(R.id.submit).setOnClickListener(this);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String strDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dob.setText(strDate);
                    }
                }, year, month, day );
                dialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

        populateSpinner(gender, R.array.gender_array);

        nationality.addTextChangedListener(new TextWatcher() {
            int count;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<String> countries = new ArrayList<>();
                ArrayAdapter<String> nationalityAdapter;
                if(charSequence.length() != 0) {
                    Locale[] locale = Locale.getAvailableLocales();
                    String country;
                    for(Locale loc : locale) {
                        country = loc.getDisplayCountry();
                        String firstUpperCaseInput = charSequence.toString().replaceFirst(String.valueOf(charSequence.charAt(0)), String.valueOf(Character.toUpperCase(charSequence.charAt(0))));
                        if(country.length() > 0 && !countries.contains(country) && country.startsWith(firstUpperCaseInput)) {
                            countries.add(country);
                        }
                    }
                    Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

                    nationalityAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown_items, countries);
                    countryList.setAdapter(nationalityAdapter);
                    count = countryList.getCount();
                    //Toast.makeText(getApplicationContext(), "Count: "+count, Toast.LENGTH_SHORT).show();
                }
            }

            /*private String localeToEmoji(Locale locale) {
                String countryCode = locale.getCountry();
                int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
                int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
                return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
            }*/

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() != 0) {
                    adjustListViewHeight(count * 95);
                }
                else {
                    adjustListViewHeight(0);
                }
            }
        });

        countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = countryList.getItemAtPosition(i).toString();
                nationality.setText(text);
                adjustListViewHeight(0);
                nationality.clearFocus();
            }
        });

        doi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String strDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        doi.setText(strDate);
                        doi.setTextSize(14);
                    }
                }, year, month, day );
                dialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

        populateSpinner(residentialStatus, R.array.residential_status_array);

        populateSpinner(martialStatus, R.array.martial_status_array);

        populateSpinner(education, R.array.education_array);

        populateSpinner(occupation, R.array.occupation_array);

        populateSpinner(position, R.array.position_array);

        copyResidentialAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            String copiedText;
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                copy = b;
                if(copy) {
                    copiedText = resiAddress.getText().toString();
                    permAddress.setText(copiedText);
                    resiAddress.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            copiedText = charSequence.toString();
                            permAddress.setText(copiedText);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                }
                else {
                    permAddress.setText("");
                    resiAddress.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            permAddress.setText("");
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                }
            }
        });

        usCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usCitizen.setError(null);
                nonUsCitizen.setError(null);
                taxPayersCode.setVisibility(View.VISIBLE);
                usCitizen.setChecked(true);
                nonUsCitizen.setChecked(false);
            }
        });

        nonUsCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usCitizen.setError(null);
                nonUsCitizen.setError(null);
                usCitizen.setChecked(false);
                nonUsCitizen.setChecked(true);
                taxPayersCode.setVisibility(View.GONE);
            }
        });
    }

    private void populateSpinner(MaterialBetterSpinner spinner, int resource) {
        List<String> data = Arrays.asList(getResources().getStringArray(resource));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown_items, data);
        spinner.setAdapter(adapter);
    }

    private void adjustListViewHeight(int height) {
        ViewGroup.LayoutParams lp = countryList.getLayoutParams();
        lp.height = height;
        countryList.setLayoutParams(lp);
        countryList.requestLayout();
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
        if(dob.getText().toString().isEmpty()) {
            dob.setHint("Please specify your date of birth");
            dob.setHintTextColor(Color.RED);
            return false;
        }
        if(gender.getText().toString().isEmpty()) {
            gender.setError("Please specify your gender");
            return false;
        }
        if(nationality.getText().toString().isEmpty()) {
            nationality.setError("Please enter your country name");
            return false;
        }
        if(!nationality.getText().toString().isEmpty()) {
            if(countryList.getCount() == 0) {
                nationality.setError("Please select enlisted countries only");
                return false;
            }
        }
        if(identity.getText().toString().isEmpty()) {
            identity.setError("Please enter your id card no");
            return false;
        }
        if(doi.getText().toString().isEmpty()) {
            doi.setHint("Please specify date of issue");
            doi.setHintTextColor(Color.RED);
            doi.setTextSize(12);
            return false;
        }
        if(poi.getText().toString().isEmpty()) {
            poi.setError("Please specify place of issue");
            return false;
        }
        if(residentialStatus.getText().toString().isEmpty()) {
            residentialStatus.setError("Please specify your residential status");
            return false;
        }
        if(martialStatus.getText().toString().isEmpty()) {
            martialStatus.setError("Please specify your martial status");
            return false;
        }
        if(phoneNo.getText().toString().isEmpty()) {
            phoneNo.setError("Please enter your phone no");
            return false;
        }
        if(!eMail.getText().toString().isEmpty()) {
            if(!eMail.getText().toString().contains("@") || !Patterns.EMAIL_ADDRESS.matcher(eMail.getText().toString()).matches()) {
                eMail.setError("Please enter a valid email id");
                return false;
            }
        }
        if(education.getText().toString().isEmpty()) {
            education.setError("Please specify education qualification");
            return false;
        }
        if(occupation.getText().toString().isEmpty()) {
            occupation.setError("Please specify your occupation details");
            return false;
        }
        if(position.getText().toString().isEmpty()) {
            position.setError("Please specify your position");
            return false;
        }
        if(resiAddress.getText().toString().isEmpty()) {
            resiAddress.setError("Please enter your residential address");
            return false;
        }
        if(permAddress.getText().toString().isEmpty()) {
            permAddress.setError("Please enter your permanent address");
            return false;
        }
        if(workPlace.getText().toString().isEmpty()) {
            workPlace.setError("Please enter your work place address");
            return false;
        }
        if(!usCitizen.isChecked() && !nonUsCitizen.isChecked()) {
            usCitizen.setError("Please select any one of the following cases");
            nonUsCitizen.setError("Please select any one of the following cases");
            return false;
        }
        if(usCitizen.isChecked()) {
            if(taxPayersCode.getText().toString().isEmpty()) {
                taxPayersCode.setError("Please enter your US Tax Code");
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        final Dialog dialog = new Dialog(AccountOpeningActivity.this, android.R.style.Theme_Dialog);
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
                Intent intent = new Intent(AccountOpeningActivity.this, BankPackagesActivity.class);
                intent.putExtra("Amount", bundle.getString("Amount"));
                intent.putExtra("Tenure", bundle.getString("Tenure"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
