package com.example.go4lunch.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.profile_activity_user_image_view)
    ImageView userImageView;
    @BindView(R.id.profile_activity_user_name)
    TextView userName;
    @BindView(R.id.profile_activity_user_email)
    TextView userEmail;
    @BindView(R.id.profile_activity_log_out_button)
    Button log_out_button;
    @BindView(R.id.profile_activity_delete_user_button)
    Button delete_user_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        initializeUserDescriptions();
    }

    private void initializeUserDescriptions() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform())
                .into(userImageView);

        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());
    }

    @OnClick(R.id.profile_activity_log_out_button)
    public void onClickLogOutButton() {
        signOutFromFirebase();
    }

    @OnClick(R.id.profile_activity_delete_user_button)
    public void onClickDeleteUserButton() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.pop_up_delete_user)
                .setPositiveButton(R.string.pop_up_positive_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUserFromFirebase();
                    }
                })
                .setNegativeButton(R.string.pop_up_negative_button_text, null)
                .show();
    }

    private void signOutFromFirebase() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startMainActivityWhileUserLogOutOrDeleteAccount();
                    }
                });
    }

    private void deleteUserFromFirebase() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startMainActivityWhileUserLogOutOrDeleteAccount();
                    }
                });
    }



    private void startMainActivityWhileUserLogOutOrDeleteAccount() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}