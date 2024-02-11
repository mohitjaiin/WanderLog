package com.example.expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private ArrayList<Trip> trips;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewLocation;
        public TextView textViewBudget;
        public Button btnViewDetails;

        public TripViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_trip_name);
            textViewLocation = itemView.findViewById(R.id.text_view_trip_location);
            textViewBudget = itemView.findViewById(R.id.text_view_trip_budget);
            btnViewDetails = itemView.findViewById(R.id.btn_view_details);

            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public TripAdapter(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        TripViewHolder evh = new TripViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip currentItem = trips.get(position);

        holder.textViewName.setText(currentItem.getName());
        holder.textViewLocation.setText(currentItem.getLocation());
        holder.textViewBudget.setText("Budget: $" + currentItem.getBudget());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }


}

