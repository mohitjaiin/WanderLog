package com.example.expensemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyTripsFragment extends Fragment {

    private static final int ADD_TRIP_REQUEST_CODE = 1;
    private static final String SHARED_PREFS_KEY = "trip_data";

    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private ArrayList<Trip> trips;
    private Button btnAddTrip;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_my_trips, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_trips);
        btnAddTrip = view.findViewById(R.id.btn_add_trip);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        gson = new Gson();

        // Load trips from SharedPreferences
        trips = loadTrips();

        tripAdapter = new TripAdapter(trips, new TripAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openActivityForTrip(trips.get(position));
            }
        }, new TripAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteTrip(position);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(tripAdapter);

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trips.size() < 3) {
                    Intent intent = new Intent(getActivity(), AddTripActivity.class);
                    startActivityForResult(intent, ADD_TRIP_REQUEST_CODE);
                } else {
                    Toast.makeText(getActivity(), "You can't add more than three trips.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TRIP_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Trip newTrip = (Trip) data.getSerializableExtra("new_trip");
            if (newTrip != null) {
                trips.add(newTrip);
                tripAdapter.notifyDataSetChanged(); // Notify adapter of data set change
                saveTrips(trips); // Save trips to SharedPreferences
            }
        }
    }

    private void saveTrips(ArrayList<Trip> trips) {
        String json = gson.toJson(trips);
        sharedPreferences.edit().putString("trips", json).apply();
    }

    private ArrayList<Trip> loadTrips() {
        String json = sharedPreferences.getString("trips", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Trip>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }

    private void openActivityForTrip(Trip trip) {
        // Determine which activity to open based on the trip
        Intent intent;
        switch (trip.getId()) {
            case 1:
                // Open activity for the first trip
                intent = new Intent(getActivity(), FirstTripActivity.class);
                break;
            case 2:
                // Open activity for the second trip
                intent = new Intent(getActivity(), SecondTripActivity.class);
                break;
            case 3:
                // Open activity for the third trip
                intent = new Intent(getActivity(), MainActivity.class);
                break;
            default:
                // Open a default activity if needed
                intent = new Intent(getActivity(), MainActivity.class);
                break;
        }
        intent.putExtra("trip", trip);
        startActivity(intent);
    }

    private void deleteTrip(int position) {
        trips.remove(position);
        tripAdapter.notifyItemRemoved(position);
        saveTrips(trips);
    }
}
