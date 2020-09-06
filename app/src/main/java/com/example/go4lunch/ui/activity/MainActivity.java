package com.example.go4lunch.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    @BindView(R.id.main_activity_frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.login_button)
    Button login_button;
    @BindView(R.id.log_out_main_activity_button)
    Button sign_out_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
        sign_out_button.setVisibility(View.GONE);
        } else {
            login_button.setText(getString(R.string.start_application));
        }
    }

    @OnClick(R.id.login_button)
    public void onClickLoginButton() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
        this.startSignInActivity();
        }else {
            this.startMapsActivity();
        }
    }
    @OnClick(R.id.log_out_main_activity_button)
    public void onClickLogOutButton() {
        this.signOutFromFirebase();
    }

    private void startSignInActivity() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.mipmap.bol_fumant)
                        .build(),
                RC_SIGN_IN);
    }

    private void signOutFromFirebase() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        sign_out_button.setVisibility(View.GONE);
                    }
                });

        login_button.setText(getString(R.string.connexion));
    }

    private void startMapsActivity() {
        finish();
        Intent intent= new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Snackbar.make(frameLayout, getString(R.string.connection_succeed), Snackbar.LENGTH_SHORT).show();
                login_button.setText(getString(R.string.start_application));
                sign_out_button.setVisibility(View.VISIBLE);
            } else {
                if (response == null) {
                    Snackbar.make(frameLayout, getString(R.string.connection_canceled), Snackbar.LENGTH_SHORT).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Snackbar.make(frameLayout, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Snackbar.make(frameLayout, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT).show();
                    Log.e("error", response.getError().getMessage());
                }

            }
        }
    }
}