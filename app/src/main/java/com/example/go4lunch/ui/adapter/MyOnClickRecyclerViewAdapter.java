package com.example.go4lunch.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.model.Colleague;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOnClickRecyclerViewAdapter extends
        RecyclerView.Adapter<MyOnClickRecyclerViewAdapter.ViewHolder> {
    private List<Colleague> myColleagueList;

    public MyOnClickRecyclerViewAdapter(List<Colleague> items) {
        this.myColleagueList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_on_click_colleague, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Colleague myColleague = myColleagueList.get(position);

        holder.colleagueName.setText(myColleague.getColleagueName());
        holder.restaurantJoining.setText(myColleague.getColleagueJoinOrNot());
        Glide.with(holder.colleaguePhoto.getContext())
                .load(myColleague.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.colleaguePhoto);
    }

    @Override
    public int getItemCount() {
        return myColleagueList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_on_click_colleague_photo)
        ImageView colleaguePhoto;
        @BindView(R.id.fragment_on_click_colleague_name)
        TextView colleagueName;
        @BindView(R.id.fragment_on_click_colleague_restaurant_joining)
        TextView restaurantJoining;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
