package com.arfideveloper.novelnest.activities.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;

import in.aabhasjindal.otptextview.OtpTextView;
import pl.droidsonroids.gif.GifImageView;

public class OtpActivity extends AppCompatActivity {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    String verification_code, email;
    OtpTextView otpView;

    Button btn_reset, btn_back;
    TextView text_ResendCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        context = OtpActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        email = getIntent().getStringExtra("email");
        verification_code = getIntent().getStringExtra("verification_code");

        btn_reset = findViewById(R.id.btn_reset);
        btn_back = findViewById(R.id.btn_back);
        otpView = findViewById(R.id.otp_view);
        text_ResendCode = findViewById(R.id.text_ResendCode);

        btn_back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

        btn_reset.setOnClickListener(v -> {
            String otp = otpView.getOTP();
            if (otp.isEmpty()){
                CustomCookieToast.showRequiredToast(OtpActivity.this, "Please enter otp Code");
            }
            else if(! otp.equals(verification_code)){
                CustomCookieToast.showRequiredToast(OtpActivity.this, "Your code is invalid!, try again");
            }
            else {
                Intent intent = new Intent(context, ResetPasswordActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
            }
        });

        text_ResendCode.setOnClickListener(view -> {

        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }
}