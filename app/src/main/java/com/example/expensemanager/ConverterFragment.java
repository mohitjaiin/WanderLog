package com.example.expensemanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class ConverterFragment extends Fragment {

    private EditText editTextAmount;
    private Spinner spinnerFromCurrency;
    private Spinner spinnerToCurrency;
    private Button buttonConvert;
    private TextView textViewResult;

    private double[] exchangeRates = {1.23396, 0.75,0.882047,1.566015,1.560132,1.154727,7.827874,132.360679,0.013};

    public ConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_converter, container, false);

        editTextAmount = view.findViewById(R.id.editTextAmount);
        spinnerFromCurrency = view.findViewById(R.id.spinnerFromCurrency);
        spinnerToCurrency = view.findViewById(R.id.spinnerToCurrency);
        buttonConvert = view.findViewById(R.id.buttonConvert);
        textViewResult = view.findViewById(R.id.textViewResult);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.currencies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromCurrency.setAdapter(adapter);
        spinnerToCurrency.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });

        return view;
    }

    private void convertCurrency() {
        String amountString = editTextAmount.getText().toString();
        if (TextUtils.isEmpty(amountString)) {
            editTextAmount.setError("Please enter an amount");
            return;
        }

        double amount = Double.parseDouble(amountString);
        double fromRate = exchangeRates[spinnerFromCurrency.getSelectedItemPosition()];
        double toRate = exchangeRates[spinnerToCurrency.getSelectedItemPosition()];

        double result = (amount / fromRate) * toRate;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        textViewResult.setText(decimalFormat.format(result));
    }
}
