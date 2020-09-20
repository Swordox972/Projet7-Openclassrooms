package com.example.go4lunch.ui.adapter;

import android.graphics.Color;
import android.util.TypedValue;
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
import com.example.go4lunch.service.colleague.ColleagueChoice;

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
        //Set colleague choice
        ColleagueChoice.setScarlettAndHughEatingAt();
        ColleagueChoice.setNanaAndGodfreyEatingAt();
        ColleagueChoice.setOtherColleagueNotDecided();

        holder.colleagueRestaurantName.setText(myColleague.getColleagueName());
        //Put colleague status text
        if (myColleague.getColleagueStatus() == Colleague.Status.isEatingAt) {
            holder.colleagueRestaurantStatus.setText(holder.itemView.getContext()
                    .getString(R.string.is_eating_at));
        } else if (myColleague.getColleagueStatus() == Colleague.Status.notDecided) {
            holder.colleagueRestaurantStatus.setText(holder.itemView.getContext()
                    .getString(R.string.has_not_decided_yet));
        }

        holder.colleagueRestaurantChoice.setText(myColleague.getColleagueRestaurantChoice());

        if (!myColleague.getColleagueStatus().equals(Colleague.Status.notDecided)) {
            holder.colleagueRestaurantName.setTextColor(Color.parseColor("#000000"));
            holder.colleagueRestaurantStatus.setTextColor(Color.parseColor("#000000"));
        }
        holder.colleagueRestaurantStatus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
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
        @BindView(R.id.colleague_restaurant_name)
        TextView colleagueRestaurantName;
        @BindView(R.id.colleague_restaurant_status)
        TextView colleagueRestaurantStatus;
        @BindView(R.id.colleague_restaurant_choice)
        TextView colleagueRestaurantChoice;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
