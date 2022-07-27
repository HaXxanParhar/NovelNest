package com.arfideveloper.novelnest.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    Button btn_back, btn_reset;

   String user_id;
   TextInputLayout input_oldPassword, input_newPassword, input_confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context = ChangePasswordActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        user_id = String.valueOf(Utilities.getInt(context, "user_id"));
        btn_back = findViewById(R.id.btn_back);
        btn_reset = findViewById(R.id.btn_reset);


        input_oldPassword = findViewById(R.id.input_oldPassword);
        input_newPassword = findViewById(R.id.input_newPassword);
        input_confirmPassword = findViewById(R.id.input_confirmPassword);

        btn_back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

        btn_reset.setOnClickListener(v -> {
            String oldPassword = Objects.requireNonNull(Objects.requireNonNull(input_oldPassword.getEditText()).getText().toString());
            String password = Utilities.getString(context, "user_Password");
            String newPassword = Objects.requireNonNull(Objects.requireNonNull(input_newPassword.getEditText()).getText().toString());
            String confirmPassword = Objects.requireNonNull(Objects.requireNonNull(input_confirmPassword.getEditText()).getText().toString());

            if (oldPassword.isEmpty()){
                CustomCookieToast.showRequiredToast(ChangePasswordActivity.this, "Please enter old password");
            }
            else if (!oldPassword.equals(password)){
                CustomCookieToast.showRequiredToast(ChangePasswordActivity.this, "Please enter correct old password");
            }
            else if (newPassword.isEmpty()){
                CustomCookieToast.showRequiredToast(ChangePasswordActivity.this, "Please enter new password");
            }
            else if(newPassword.length() < 6) {
                CustomCookieToast.showRequiredToast(ChangePasswordActivity.this, "Please Enter 6 digit Password");
            }
            else if (confirmPassword.isEmpty()){
                CustomCookieToast.showRequiredToast(ChangePasswordActivity.this, "Please enter confirm password");
            }
            else if (!newPassword.equals(confirmPassword)){
                CustomCookieToast.showRequiredToast(ChangePasswordActivity.this, "Can't match confirm password");
            }
            else {
                Utilities.hideKeyboard(input_newPassword, context);
                shadow_View.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                requestForChangePassword(user_id, oldPassword, newPassword );
            }
        });

    }

    public void requestForChangePassword(String user_id, String old_password, String new_password){
        Call<MainResponseModel> call = service.changePasswordFor(user_id, old_password, new_password);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            requestLogoutUser();
                        } else {
                            shadow_View.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                            CustomCookieToast.showRequiredToast(ChangePasswordActivity.this, response.body().getMessage());
                        }
                    } else {
                        shadow_View.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        CustomCookieToast.showFailureToast(ChangePasswordActivity.this, response.message());
                    }
                } else {
                    shadow_View.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    CustomCookieToast.showFailureToast(ChangePasswordActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(ChangePasswordActivity.this, t.getMessage());
            }
        });
    }

    public void requestLogoutUser(){
        Call<MainResponseModel> call = service.changePasswordLogOut(user_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            finishAffinity();
                            Utilities.clearSharedPref(context);
                            Intent intent = new Intent(context , LoginActivity.class);
                            intent.putExtra("finish", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            ((Activity)context).overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                            startActivity(intent);
                        } else {
                            CustomCookieToast.showRequiredToast(ChangePasswordActivity.this, response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(ChangePasswordActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(ChangePasswordActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }
}