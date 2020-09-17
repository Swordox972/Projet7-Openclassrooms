package com.example.go4lunch.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.go4lunch.R;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.Users;
import com.example.go4lunch.service.restaurant.RestaurantInformation;
import com.example.go4lunch.ui.fragment.OnClickRestaurantFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnClickRestaurantActivity extends AppCompatActivity {
    //Initialize variables
    private boolean isSelected = false;
    Drawable fao_not_selected;
    Drawable fao_selected;
    private MyRestaurantModel myRestaurant;
    private Colleague me;
    Bundle args;
    OnClickRestaurantFragment onClickRestaurantFragment;
    @BindView(R.id.detail_restaurant_name)
    TextView restaurantName;
    @BindView(R.id.detail_restaurant_address)
    TextView restaurantAddress;
    @BindView(R.id.detail_restaurant_photo)
    ImageView restaurantPhoto;
    @BindView(R.id.fao_chose_restaurant)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.restaurant_call)
    ImageButton restaurantCall;
    @BindView(R.id.restaurant_like)
    ImageButton restaurantLike;
    @BindView(R.id.restaurant_website)
    ImageButton restaurantWebsite;
    @BindView(R.id.on_click_star1)
    ImageView star1;
    @BindView(R.id.on_click_star2)
    ImageView star2;
    @BindView(R.id.on_click_star3)
    ImageView star3;
    @BindView(R.id.on_click_activity_button_finish)
    Button buttonFinish;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_restaurant);
        ButterKnife.bind(this);
        args = new Bundle();
        // commit fragment
        onClickRestaurantFragment = new OnClickRestaurantFragment();
        args.putBoolean("isSelected", false);
        onClickRestaurantFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.on_click_activity_fragment_container_view,
                onClickRestaurantFragment).commit();

        //Get restaurant data to know which restaurant the user clicked on
        //From List View
        if ((MyRestaurantModel) getIntent().getSerializableExtra("Restaurant") != null) {
            this.initializeRestaurant("Restaurant");

        }
        //From MapView
        if ((MyRestaurantModel) getIntent().getSerializableExtra("MapsActivityRestaurant") !=
                null) {
            this.initializeRestaurant("MapsActivityRestaurant");
        }

        this.initializeFao();
    }

    @Override
    protected void onStop() {
        myRestaurant = null;
        super.onStop();
    }

    private void initializeRestaurant(String restaurantIntentCode) {
        myRestaurant = (MyRestaurantModel) getIntent().getSerializableExtra(restaurantIntentCode);
        restaurantName.setText(myRestaurant.getRestaurantName());
        restaurantAddress.setText(myRestaurant.getRestaurantAddress());
        restaurantPhoto.setImageBitmap(RestaurantInformation.StringToBitMap(
                myRestaurant.getRestaurantImageName()));

        restaurantCall.setOnClickListener((View view) -> {
            if (myRestaurant.getRestaurantPhoneNumber() != null) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + myRestaurant.getRestaurantPhoneNumber()));
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Phone number", Toast.LENGTH_SHORT).show();
            }
        });

        restaurantLike.setOnClickListener(view -> {
            if (!myRestaurant.isLiked()) {
                myRestaurant.getColleagueLikeList().add(new Colleague());
                initializeRestaurantStars();
                Toast.makeText(this, getString(R.string.restaurant_liked), Toast.LENGTH_SHORT)
                        .show();
                myRestaurant.setLiked(true);
            } else {
                int i = myRestaurant.getColleagueLikeList().size() - 1;
                myRestaurant.getColleagueLikeList().remove(i);
                initializeRestaurantStars();
                myRestaurant.setLiked(false);
                Toast.makeText(this, getString(R.string.restaurant_not_liked), Toast.LENGTH_SHORT)
                        .show();
            }
        });

        restaurantWebsite.setOnClickListener((View view) -> {
            if (myRestaurant.getRestaurantWebsite() != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(myRestaurant.getRestaurantWebsite()));
                startActivity(intent);
            } else {
                Toast.makeText(this, "No website", Toast.LENGTH_SHORT).show();
            }
        });

        initializeRestaurantStars();
    }

    private void initializeRestaurantStars() {
        List<Colleague> colleagueLikeList = myRestaurant.getColleagueLikeList();
        if (myRestaurant.getColleagueLikeList().size() < 3) {
            star1.setImageDrawable(null);
            star2.setImageDrawable(null);
            star3.setImageDrawable(null);
        } else if (colleagueLikeList.size() == 3 && colleagueLikeList.size() < 5) {
            star1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_yellow_18));
            star2.setImageDrawable(null);
            star3.setImageDrawable(null);
        } else if (colleagueLikeList.size() == 5 && colleagueLikeList.size() < 7) {
            star1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_yellow_18));
            star2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_yellow_18));
            star3.setImageDrawable(null);
        } else if (colleagueLikeList.size() == 7 || colleagueLikeList.size() > 7) {
            star1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_yellow_18));
            star2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_yellow_18));
            star3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_yellow_18));
        }


    }

    private void initializeFao() {
        fao_not_selected = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_circle_24, null);
        fao_selected = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_circle_selected_24, null);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        me = new Colleague(9, user.getDisplayName(), Colleague.Status.isJoining,
                myRestaurant.getRestaurantName(), user.getPhotoUrl().toString());
        //Add user to Users' list
        Users.getInstance().getMyUserList().add(me);
        floatingActionButton.setOnClickListener((View view) -> {
            if (!isSelected) {
                floatingActionButton.setImageDrawable(fao_selected);
                isSelected = true;
                myRestaurant.getColleagueList().add(me);
                args.putSerializable("MyRestaurant", myRestaurant);
                args.putBoolean("isSelected", isSelected);

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.on_click_activity_fragment_container_view);
                getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
            } else {
                floatingActionButton.setImageDrawable(fao_not_selected);
                isSelected = false;
            }

        });
    }

    @OnClick(R.id.on_click_activity_button_finish)
    public void onClickButtonFinish() {
        Intent intent = getIntent().putExtra("Restaurant", myRestaurant);
        setResult(RESULT_OK, intent);
        finish();
    }

}