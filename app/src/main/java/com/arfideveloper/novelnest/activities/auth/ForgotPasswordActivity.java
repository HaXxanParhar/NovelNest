package com.arfideveloper.novelnest.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.ForgotResponseModel;
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

public class ForgotPasswordActivity extends AppCompatActivity {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    Button btn_send, btn_back;
    TextInputLayout input_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        context = ForgotPasswordActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        btn_send  = findViewById(R.id.btn_send);
        input_Email  = findViewById(R.id.input_Email);
        btn_back  = findViewById(R.id.btn_back);


        btn_send.setOnClickListener(v -> {
            String email = Objects.requireNonNull(input_Email.getEditText()).getText().toString();
            boolean isValidEmail = Utilities.isValidEmail(email);
            if (email.isEmpty()){
                CustomCookieToast.showRequiredToast(ForgotPasswordActivity.this, "Please Enter Email");
            }
            else if (!isValidEmail){
                CustomCookieToast.showRequiredToast(ForgotPasswordActivity.this, "Please Enter Valid Email");
            }
            else {
                Utilities.hideKeyboard(input_Email, context);
                shadow_View.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                requestForForgotPassword(email);
            }
        });

        btn_back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });
    }
    public void requestForForgotPassword (String email){
        Call<ForgotResponseModel> call = service.forgotPassword(email);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ForgotResponseModel> call, @NonNull Response<ForgotResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            String email = String.valueOf(response.body().getForgotPasswordDataModel().getEmail());
                            String verificationCode = String.valueOf(response.body().getForgotPasswordDataModel().getVerification_code());
                            Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("verification_code", verificationCode);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                        } else {
                            CustomCookieToast.showRequiredToast(ForgotPasswordActivity.this, response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(ForgotPasswordActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(ForgotPasswordActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForgotResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(ForgotPasswordActivity.this, t.getMessage());

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }
}