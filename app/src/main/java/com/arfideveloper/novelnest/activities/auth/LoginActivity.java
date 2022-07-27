package com.arfideveloper.novelnest.activities.auth;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.BottomNavActivity;
import com.arfideveloper.novelnest.apimodels.AuthResponseModel;
import com.arfideveloper.novelnest.apimodels.CategoriesModel;
import com.arfideveloper.novelnest.apimodels.HomeResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelBannersModel;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.apimodels.UserDataModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static List<NovelBannersModel> novelBannersModelList;
    public static List<NovelsModel> novelsModelList;
    public static List<CategoriesModel> categoriesModelList;
    public static int totalPages = 0;
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;
    TextView text_ForgotPassword, text_signup;
    Button btn_login;
    TextInputLayout input_Email, input_password;
    String token = "12213112121qweqqwqq";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        text_ForgotPassword = findViewById(R.id.text_ForgotPassword);
        text_signup = findViewById(R.id.text_signup);
        btn_login = findViewById(R.id.btn_login);
        input_Email = findViewById(R.id.input_Email);
        input_password = findViewById(R.id.input_Password);


        text_ForgotPassword.setOnClickListener(v -> {
            Intent forgotIntent = new Intent(context, ForgotPasswordActivity.class);
            startActivity(forgotIntent);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        text_signup.setOnClickListener(v -> {
            Intent forgotIntent = new Intent(context, SignUpActivity.class);
            startActivity(forgotIntent);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });
        getFCMToken();

        btn_login.setOnClickListener(v -> {
            String email = Objects.requireNonNull(input_Email.getEditText()).getText().toString();
            boolean isValidEmail = Utilities.isValidEmail(email);
            String password = Objects.requireNonNull(Objects.requireNonNull(input_password.getEditText()).getText().toString());
            if (email.isEmpty()) {
                CustomCookieToast.showRequiredToast(LoginActivity.this, "Please Enter Email");
            } else if (!isValidEmail) {
                CustomCookieToast.showRequiredToast(LoginActivity.this, "Please enter valid email");
            } else if (password.isEmpty()) {
                CustomCookieToast.showRequiredToast(LoginActivity.this, "Please enter password");
            } else if (password.length() < 6) {
                CustomCookieToast.showRequiredToast(LoginActivity.this, "Please Enter 6 digit Password");
            } else {
                Utilities.hideKeyboard(input_Email, context);
                shadow_View.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                requestForSignIn(email, password, token);
            }
        });
    }

    private void requestForSignIn(String inputEmail, String inputPassword, String token) {
        Call<AuthResponseModel> call = service.loginUser(inputEmail, inputPassword, token);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<AuthResponseModel> call, @NonNull Response<AuthResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            UserDataModel user = response.body().getUserDataModel();
                            int id = user.getId();
                            String facebook_id = user.getFacebook_id();
                            String google_id = user.getGoogle_id();
                            String name = user.getName();
                            String email = user.getEmail();
                            String email_verified_at = user.getEmail_verified_at();
                            String profile_image = user.getProfile_image();
                            String bio = user.getBio();
                            String gender = user.getGender();
                            String date_of_birth = user.getDate_of_birth();
                            String portfolio_url = user.getPortfolio_url();
                            String CNIC_front_image = user.getCnic_front_image();
                            String CNIC_back_image = user.getCnic_back_image();
                            String average_rating = user.getAverage_rating();
                            String is_verified = user.getIs_verified();
                            String type = user.getType();
                            String token = user.getToken();


                            Utilities.saveInt(context, "user_id", id);
                            Utilities.saveString(context, "type", type);
                            Utilities.saveString(context, "is_verified", is_verified);
                            Utilities.saveString(context, "facebook_id", facebook_id);
                            Utilities.saveString(context, "google_id", google_id);
                            Utilities.saveString(context, "name", name);
                            Utilities.saveString(context, "email", email);
                            Utilities.saveString(context, "email_verified_at", email_verified_at);
                            Utilities.saveString(context, "profile_image", profile_image);
                            Utilities.saveString(context, "bio", bio);
                            Utilities.saveString(context, "gender", gender);
                            Utilities.saveString(context, "date_of_birth", date_of_birth);
                            Utilities.saveString(context, "portfolio_url", portfolio_url);
                            Utilities.saveString(context, "CNIC_front_image", CNIC_front_image);
                            Utilities.saveString(context, "CNIC_back_image", CNIC_back_image);
                            Utilities.saveString(context, "average_rating", average_rating);
                            Utilities.saveString(context, "token", token);
                            Utilities.saveString(context, "isLoggedIn", "true");
                            Utilities.saveString(context, "user_Password", inputPassword);
                            preLoadHomeScreenData(String.valueOf(id));

                        } else {
                            CustomCookieToast.showFailureToast(LoginActivity.this, response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(LoginActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showRequiredToast(LoginActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(LoginActivity.this, t.getMessage());

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }


    public void preLoadHomeScreenData(String user_id) {
        Call<HomeResponseModel> call = service.getHomeScreen(user_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HomeResponseModel> call, @NonNull Response<HomeResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        totalPages = response.body().getNovelsPaginationDataModel().getLast_page();

                        //get data and show
                        novelBannersModelList = response.body().getNovelBannersModelList();
                        categoriesModelList = response.body().getCategoriesModelList();
                        novelsModelList = response.body().getNovelsPaginationDataModel().getNovelsModel();
                        Utilities.saveString(context, "home_loading_from", "login");

                        finishAffinity();
                        Intent intent = new Intent(LoginActivity.this, BottomNavActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                    } else {
                        CustomCookieToast.showFailureToast(LoginActivity.this, response.body().getMessage());
                    }

                } else {
                    shadow_View.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    CustomCookieToast.showFailureToast(LoginActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(LoginActivity.this, t.getMessage());
            }
        });
    }

    public void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Get new FCM registration token
                token = task.getResult();
            }
        }).addOnFailureListener(e -> Log.w(TAG, "Failed to get token" + e.getLocalizedMessage()));
    }
}