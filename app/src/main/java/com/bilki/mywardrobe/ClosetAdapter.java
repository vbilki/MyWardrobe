package com.bilki.mywardrobe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClosetAdapter extends RecyclerView.Adapter<ClosetAdapter.ClosetViewHolder> {

    private Context context;
    private ArrayList<FeaturedHelperClass> closetLocations;

    public ClosetAdapter(ArrayList<FeaturedHelperClass> closetLocations) {



        this.closetLocations = closetLocations;

    }

    @NonNull
    @Override
    public ClosetAdapter.ClosetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.close_card_design, parent, false);
        ClosetAdapter.ClosetViewHolder closetViewHolder = new ClosetAdapter.ClosetViewHolder(view);
        return closetViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ClosetViewHolder holder, int position) {

        FeaturedHelperClass featuredHelperClass = closetLocations.get(position);

        //Picasso.get().load(featuredHelperClass.getUrl()).resize(95,95).into(holder.image);
        holder.image.setImageResource(featuredHelperClass.getImage());
        holder.name.setText(featuredHelperClass.getName());

    }



    @Override
    public int getItemCount() {

        return closetLocations.size();

    }

    public static class ClosetViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ClosetViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.close_image);
            name = itemView.findViewById(R.id.close_name);


        }
    }
}