package com.example.expensemanager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.databinding.ActivityAddExpenseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    ActivityAddExpenseBinding binding;
    private String type;
    private ExpenseModel expenseModel;

    // Define the category options
    private List<String> categoryOptions = Arrays.asList("Transport", "Food", "Accommodation", "Other");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("type");
        expenseModel = (ExpenseModel) getIntent().getSerializableExtra("model");

        if (type == null) {
            type = expenseModel.getType();
            binding.amount.setText(String.valueOf(expenseModel.getAmount()));
            binding.note.setText(expenseModel.getNote());
        }

        if (type.equals("Income")) {
            binding.incomeRadio.setChecked(true);
        } else {
            binding.expenseRadio.setChecked(true);
        }

        // Initialize the category spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);

        // Set the selected category or enable editing for custom category
        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == categoryOptions.size() - 1) { // Last item is "Other"
                    binding.customCategory.setVisibility(View.VISIBLE);
                } else {
                    binding.customCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        binding.incomeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Income";
            }
        });

        binding.expenseRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Expense";
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (expenseModel == null) {
            menuInflater.inflate(R.menu.add_menu, menu);
        } else {
            menuInflater.inflate(R.menu.update_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.saveExpense) {
            if (type != null) {
                createExpense();
            } else {
                updateExpense();
            }
            return true;
        }
        if (id == R.id.deleteExpense) {
            deleteExpense();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteExpense() {
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseModel.getExpenseId())
                .delete();
        finish();
    }

    private void createExpense() {
        String expenseId = UUID.randomUUID().toString();
        String amount = binding.amount.getText().toString().trim();
        String note = binding.note.getText().toString().trim();
        String category = binding.categorySpinner.getSelectedItem().toString().trim();

        // If "Other" is selected, use the custom category
        if (category.equals("Other")) {
            category = binding.customCategory.getText().toString().trim();
        }

        boolean incomeChecked = binding.incomeRadio.isChecked();
        if (incomeChecked) {
            type = "Income";
        } else {
            type = "Expense";
        }

        // Validate amount, note, and category
        if (amount.isEmpty()) {
            binding.amount.setError("Amount is required");
            binding.amount.requestFocus();
            return;
        }

        if (note.isEmpty()) {
            binding.note.setError("Note is required");
            binding.note.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            binding.categorySpinner.requestFocus();
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return;
        }

        ExpenseModel expenseModel = new ExpenseModel(expenseId, note, category, type, Long.parseLong(amount), Calendar.getInstance().getTimeInMillis(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(expenseModel);
        finish();
    }

    private void updateExpense() {
        String expenseId = expenseModel.getExpenseId();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.categorySpinner.getSelectedItem().toString();

        // If "Other" is selected, use the custom category
        if (category.equals("Other")) {
            category = binding.customCategory.getText().toString();
        }

        boolean incomeChecked = binding.incomeRadio.isChecked();
        if (incomeChecked) {
            type = "Income";
        } else {
            type = "Expense";
        }

        if (amount.trim().length() == 0) {
            binding.amount.setError("Empty");
            return;
        }

        ExpenseModel model = new ExpenseModel(expenseId, note, category, type, Long.parseLong(amount), expenseModel.getTime(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(model);
        finish();
    }
}
