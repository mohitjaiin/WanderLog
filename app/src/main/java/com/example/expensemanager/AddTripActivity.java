package com.example.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTripActivity extends AppCompatActivity {

    private EditText editTextTripName;
    private EditText editTextTripLocation;
    private EditText editTextTripBudget;
    private Button buttonSaveTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trips);

        editTextTripName = findViewById(R.id.edit_text_trip_name);
        editTextTripLocation = findViewById(R.id.edit_text_trip_location);

        buttonSaveTrip = findViewById(R.id.button_save_trip);

        buttonSaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get trip details entered by the user
                String tripName = editTextTripName.getText().toString().trim();
                String tripLocation = editTextTripLocation.getText().toString().trim();


                // Validate trip name
                if (TextUtils.isEmpty(tripName)) {
                    editTextTripName.setError("Please enter a trip name");
                    return;
                }

                // Validate trip location
                if (TextUtils.isEmpty(tripLocation)) {
                    editTextTripLocation.setError("Please enter a trip location");
                    return;
                }




                // Create a new Trip object
                Trip newTrip = new Trip(tripName, tripLocation);

                // Return the new trip to MyTripsFragment
                Intent resultIntent = new Intent();
                resultIntent.putExtra("new_trip", newTrip);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
