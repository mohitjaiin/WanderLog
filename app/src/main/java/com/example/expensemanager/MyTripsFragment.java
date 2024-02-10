package com.example.expensemanager;

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

public class MyTripsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private ArrayList<Trip> trips;
    private Button btnAddTrip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_my_trips, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_trips);
        btnAddTrip = view.findViewById(R.id.btn_add_trip);

        trips = new ArrayList<>();
        // Initialize your trips data here
        trips.add(new Trip("Trip 1", "Location 1", 1000));
        trips.add(new Trip("Trip 2", "Location 2", 1500));
        trips.add(new Trip("Trip 3", "Location 3", 1200));

        tripAdapter = new TripAdapter(trips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(tripAdapter);

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add trip button click
                // Here you can start an activity to add a new trip or perform any other action
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
}
