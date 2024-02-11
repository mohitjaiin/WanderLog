package com.example.expensemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlaceAdapter adapter;
    private List<Place> places;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_explore, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_popular_places);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Dummy data for demonstration
        places = new ArrayList<>();
        places.add(new Place("Eiffel Tower", "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France. It is named after the engineer Gustave Eiffel.", R.drawable.eiffeltower));
        places.add(new Place("Great Wall of China", "The Great Wall of China is a series of fortifications made of stone, brick, tamped earth, wood, and other materials, generally built along an east-to-west line across the historical northern borders of China to protect the Chinese states and empires against the raids and invasions of the various nomadic groups of the Eurasian Steppe.", R.drawable.gwoc));
        places.add(new Place("Machu Picchu", "Machu Picchu is a 15th-century Inca citadel located in the Eastern Cordillera of southern Peru on a mountain ridge 2,430 meters above sea level.", R.drawable.machupichu));
        places.add(new Place("Taj Mahal", "The Taj Mahal is an ivory-white marble mausoleum on the right bank of the river Yamuna in the Indian city of Agra. It was commissioned in 1631 by the Mughal emperor Shah Jahan to house the tomb of his favorite wife, Mumtaz Mahal.", R.drawable.tajmahal));
        places.add(new Place("Pyramids of Giza", "The Pyramids of Giza are ancient monuments that include the three pyramid complexes known as the Great Pyramids, along with the massive sculpture known as the Great Sphinx of Giza.", R.drawable.giza));

        adapter = new PlaceAdapter(places);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

        private List<Place> places;

        PlaceAdapter(List<Place> places) {
            this.places = places;
        }

        @NonNull
        @Override
        public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_popular_place, parent, false);
            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
            Place place = places.get(position);
            holder.placeName.setText(place.getName());
            holder.imageView.setImageResource(place.getImage());
            holder.description.setText(place.getDescription());

            holder.buttonSeeMore.setOnClickListener(v -> {
                if (holder.layoutDetails.getVisibility() == View.VISIBLE) {
                    holder.layoutDetails.setVisibility(View.GONE);
                    holder.buttonSeeMore.setText("See More");
                } else {
                    holder.layoutDetails.setVisibility(View.VISIBLE);
                    holder.buttonSeeMore.setText("Minimize");
                }
            });
        }

        @Override
        public int getItemCount() {
            return places.size();
        }

        class PlaceViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView placeName, description;
            LinearLayout layoutDetails;
            Button buttonSeeMore;

            PlaceViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image_view_place);
                placeName = itemView.findViewById(R.id.text_view_place_name);
                description = itemView.findViewById(R.id.text_view_description);
                layoutDetails = itemView.findViewById(R.id.layout_details);
                buttonSeeMore = itemView.findViewById(R.id.button_see_more);
            }
        }
    }

    private static class Place {
        private String name;
        private String description;
        private int image;

        Place(String name, String description, int image) {
            this.name = name;
            this.description = description;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getImage() {
            return image;
        }
    }
}
