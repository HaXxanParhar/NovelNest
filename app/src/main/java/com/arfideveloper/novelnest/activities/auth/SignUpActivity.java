package com.arfideveloper.novelnest.activities.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    public static List<NovelBannersModel> novelBannersModelList;
    public static List<NovelsModel> novelsModelList;
    public static List<CategoriesModel> categoriesModelList;
    public static int totalPages = 0;
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;
    TextView text_signIn;
    Button btn_signup;
    TextInputLayout input_FullName, input_Email, input_Password, input_confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = SignUpActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        text_signIn = findViewById(R.id.text_signIn);
        btn_signup = findViewById(R.id.btn_signup);
        input_FullName = findViewById(R.id.input_FullName);
        input_Email = findViewById(R.id.input_Email);
        input_Password = findViewById(R.id.input_Password);
        input_confirmPassword = findViewById(R.id.input_confirmPassword);

        text_signIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btn_signup.setOnClickListener(v -> {
            String fullName = Objects.requireNonNull(Objects.requireNonNull(input_FullName.getEditText()).getText().toString());
            String email = Objects.requireNonNull(input_Email.getEditText()).getText().toString();
            boolean isValidEmail = Utilities.isValidEmail(email);
            String password = Objects.requireNonNull(Objects.requireNonNull(input_Password.getEditText()).getText().toString());
            String confirmPassword = Objects.requireNonNull(Objects.requireNonNull(input_confirmPassword.getEditText()).getText().toString());
            if (fullName.isEmpty()) {
                CustomCookieToast.showRequiredToast(SignUpActivity.this, "Please enter full name");
            } else if (email.isEmpty()) {
                CustomCookieToast.showRequiredToast(SignUpActivity.this, "Please enter email");
            } else if (!isValidEmail) {
                CustomCookieToast.showRequiredToast(SignUpActivity.this, "Please enter valid email");
            } else if (password.isEmpty()) {
                CustomCookieToast.showRequiredToast(SignUpActivity.this, "Please enter password");
            } else if (password.length() < 6) {
                CustomCookieToast.showRequiredToast(SignUpActivity.this, "Please enter 6 digit password");
            } else if (confirmPassword.isEmpty()) {
                CustomCookieToast.showRequiredToast(SignUpActivity.this, "Please enter confirm password");
            } else if (!password.equals(confirmPassword)) {
                CustomCookieToast.showRequiredToast(SignUpActivity.this, "Can't match password");
            } else {
                Utilities.hideKeyboard(input_Email, context);
                shadow_View.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                requestForRegisterUser(fullName, email, password);
            }
        });
    }


    private void requestForRegisterUser(String name, String email, String password) {
        Call<AuthResponseModel> call = service.registerUser(name, email, password);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<AuthResponseModel> call, @NonNull Response<AuthResponseModel> response) {
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
                            Utilities.saveString(context, "is_verified", is_verified);
                            Utilities.saveString(context, "type", type);
                            Utilities.saveString(context, "token", token);
                            Utilities.saveString(context, "isLoggedIn", "true");
                            Utilities.saveString(context, "user_Password", password);

                            Utilities.saveString(context, "home_loading_from", "signup");
                            preLoadHomeScreenData(String.valueOf(id));
                        } else {
                            shadow_View.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                            CustomCookieToast.showFailureToast(SignUpActivity.this, response.body().getMessage());
                        }
                    } else {
                        shadow_View.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        CustomCookieToast.showFailureToast(SignUpActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showRequiredToast(SignUpActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(SignUpActivity.this, t.getMessage());
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

                        finishAffinity();
                        Intent intent = new Intent(SignUpActivity.this, BottomNavActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                    } else {
                        CustomCookieToast.showFailureToast(SignUpActivity.this, response.body().getMessage());
                    }

                } else {
                    shadow_View.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    CustomCookieToast.showFailureToast(SignUpActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(SignUpActivity.this, t.getMessage());
            }
        });

    }
}