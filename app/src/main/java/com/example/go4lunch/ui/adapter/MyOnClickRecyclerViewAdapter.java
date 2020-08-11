package com.example.go4lunch.ui.colleague;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class MyOnClickRecyclerViewAdapter extends
        RecyclerView.Adapter<MyOnClickRecyclerViewAdapter.ViewHolder> {




    public class ViewHolder extends RecyclerView.ViewHolder{




        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
