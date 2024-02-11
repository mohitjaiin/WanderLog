package com.example.expensemanager;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.content.Intent;
import java.util.ArrayList;

import javax.annotation.Nullable;

public class MyTripsFragment extends Fragment {

    private static final int ADD_TRIP_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private ArrayList<Trip> trips;
    private Button btnAddTrip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_my_trips, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_trips);
        btnAddTrip = view.findViewById(R.id.btn_add_trip);

        // Initialize your RecyclerView and Adapter
        trips = new ArrayList<>();
        tripAdapter = new TripAdapter(trips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(tripAdapter);

        // Set OnClickListener for the "Add Trip" button
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch AddTripActivity to add a new trip
                Intent intent = new Intent(getActivity(), AddTripActivity.class);
                startActivityForResult(intent, ADD_TRIP_REQUEST_CODE);
            }
        });

        // Handle item click listener for the recycler view
        tripAdapter.setOnItemClickListener(new TripAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle trip item click
                // Here you can start an activity to view trip details or perform any other action
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("trip",trips.get(position));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TRIP_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Retrieve trip details from AddTripActivity result and add to the list
            Trip newTrip = (Trip) data.getSerializableExtra("new_trip");
            if (newTrip != null) {
                trips.add(newTrip);
                tripAdapter.notifyItemInserted(trips.size() - 1); // Notify adapter of data set change
            }
        }
    }
}


