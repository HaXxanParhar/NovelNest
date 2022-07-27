package com.arfideveloper.novelnest.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.AuthResponseModel;
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

public class ResetPasswordActivity extends AppCompatActivity {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    Button btn_back, btn_reset;
    TextInputLayout input_Password, input_confirmPassword;
    TextView text_signIn;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        context = ResetPasswordActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        email = getIntent().getStringExtra("email");
        btn_back  = findViewById(R.id.btn_back);
        btn_reset  = findViewById(R.id.btn_reset);
        text_signIn  = findViewById(R.id.text_signIn);
        input_Password  = findViewById(R.id.input_Password);
        input_confirmPassword  = findViewById(R.id.input_confirmPassword);

        btn_back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);

        });

        text_signIn.setOnClickListener(v -> {
            finishAffinity();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

        btn_reset.setOnClickListener(v -> {
            String password = Objects.requireNonNull(Objects.requireNonNull(input_Password.getEditText()).getText().toString());
            String confirmPassword = Objects.requireNonNull(Objects.requireNonNull(input_confirmPassword.getEditText()).getText().toString());
            if (password.isEmpty()){
                CustomCookieToast.showRequiredToast(ResetPasswordActivity.this, "Please Enter Password");
            }
            else if(password.length() < 6) {
                CustomCookieToast.showRequiredToast(ResetPasswordActivity.this, "Please Enter 6 digit Password");
            }
            else if (confirmPassword.isEmpty()){
                CustomCookieToast.showRequiredToast(ResetPasswordActivity.this, "Please Enter Confirm Password");
            }
            else if (!password.equals(confirmPassword)){
                CustomCookieToast.showRequiredToast(ResetPasswordActivity.this, "Can't Match Confirm Password");
            }
            else {
                Utilities.hideKeyboard(input_Password, context);
                shadow_View.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                requestForResetPassword(email, password, confirmPassword);
            }
        });
    }
    public void requestForResetPassword (String email, String password, String confirmPassword){
        Call<AuthResponseModel> call = service.resetPasswordUser(email, password, confirmPassword);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponseModel> call, @NonNull Response<AuthResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            finishAffinity();
                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                        } else {
                            CustomCookieToast.showFailureToast(ResetPasswordActivity.this, response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(ResetPasswordActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(ResetPasswordActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(ResetPasswordActivity.this, t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }
}