package com.example.go4lunch.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.go4lunch.R;
import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.service.colleague.ColleagueApiService;

import java.util.ArrayList;
import java.util.List;

public class ChooseColleagueForRestaurantActivity extends AppCompatActivity {

Spinner spinner;
Button button;
ColleagueApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_colleague_for_restaurant);
          apiService= DI.getColleagueApiService();
        initializeSpinner();
       confirmButtonChoice();

    }

    private void initializeSpinner() {
        spinner= findViewById(R.id.choose_colleague_for_restaurant_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,
                R.array.colleague_name, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void confirmButtonChoice() {
        button= findViewById(R.id.choose_colleague_for_restaurant_button);
        button.setOnClickListener((View view) -> {
                String colleagueName= spinner.getSelectedItem().toString();
                ArrayList<Colleague> myColleagueSelected= new ArrayList<>();
                boolean isSelected=false;
            List<Colleague> dummyColleagueList= apiService.getColleagues();
            for (int i=0; i<dummyColleagueList.size(); i++) {
                if ( dummyColleagueList.get(i).getColleagueName().equals(colleagueName)) {
                    Intent intent= new Intent();
                    myColleagueSelected.add(dummyColleagueList.get(i));
                    intent.putParcelableArrayListExtra("ColleagueList", myColleagueSelected);
                    isSelected= true;
                    intent.putExtra("IsSelected", isSelected);
                }
            }

                finish();
        });


    }
}