package com.example.expensemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private ArrayList<Trip> trips;
    private OnItemClickListener mListener;
    private OnDeleteClickListener mDeleteListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public TripAdapter(ArrayList<Trip> trips, OnItemClickListener listener, OnDeleteClickListener deleteListener) {
        this.trips = trips;
        this.mListener = listener;
        this.mDeleteListener = deleteListener;
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewLocation;

        public Button btnViewDetails;
        public ImageButton btnDelete;

        public TripViewHolder(@NonNull View itemView, final OnItemClickListener listener, final OnDeleteClickListener deleteListener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_trip_name);
            textViewLocation = itemView.findViewById(R.id.text_view_trip_location);

            btnViewDetails = itemView.findViewById(R.id.btn_view_details);
            btnDelete = itemView.findViewById(R.id.btn_delete);

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

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            deleteListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(v, mListener, mDeleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip currentItem = trips.get(position);

        holder.textViewName.setText(currentItem.getName());
        holder.textViewLocation.setText(currentItem.getLocation());

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }
}
