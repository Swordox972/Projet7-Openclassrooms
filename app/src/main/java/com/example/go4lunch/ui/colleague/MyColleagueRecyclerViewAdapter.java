package com.example.go4lunch.ui.colleague;

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

public class MyColleagueRecyclerViewAdapter extends
        RecyclerView.Adapter<MyColleagueRecyclerViewAdapter.ViewHolder> {
    private List<Colleague> myColleagueList;

    public MyColleagueRecyclerViewAdapter(List<Colleague> items) {
        this.myColleagueList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_colleague,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Colleague myColleague = myColleagueList.get(position);

        holder.colleagueRestaurantChoice.setText(myColleague.getColleagueName());
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
        @BindView(R.id.colleague_photo)
        ImageView colleaguePhoto;
        @BindView(R.id.colleague_restaurant_choice)
        TextView colleagueRestaurantChoice;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
