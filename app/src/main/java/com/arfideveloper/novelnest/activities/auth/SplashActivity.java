package com.arfideveloper.novelnest.activities.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.BottomNavActivity;
import com.arfideveloper.novelnest.apimodels.CategoriesModel;
import com.arfideveloper.novelnest.apimodels.HomeResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelBannersModel;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.Utilities;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    public static List<NovelBannersModel> novelBannersModelList;
    public static List<NovelsModel> novelsModelList;
    public static List<CategoriesModel> categoriesModelList;
    public static int totalPages = 0;
    Context context;
    String isLoggedIn;
    GetDataService service;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        isLoggedIn = Utilities.getString(context, "isLoggedIn");

        final Handler handler = new Handler();
        if (isLoggedIn.equals("true")) {
            user_id = String.valueOf(Utilities.getInt(context, "user_id"));
            Utilities.saveString(context, "home_loading_from", "splash");
            preLoadHomeScreenData(user_id);
        } else {
            handler.postDelayed(() -> {
                Intent mInHome = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mInHome);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                finish();
            }, 2000);
        }
    }


    public void preLoadHomeScreenData(String user_id) {
        Call<HomeResponseModel> call = service.getHomeScreen(user_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HomeResponseModel> call, @NonNull Response<HomeResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        totalPages = response.body().getNovelsPaginationDataModel().getLast_page();
                        //get data and show
                        novelBannersModelList = response.body().getNovelBannersModelList();
                        categoriesModelList = response.body().getCategoriesModelList();
                        novelsModelList = response.body().getNovelsPaginationDataModel().getNovelsModel();
                        String userType = response.body().getType();
                        String isVerified = response.body().getIsVerified();
                        Utilities.saveString(context, "type", userType);
                        Utilities.saveString(context, "is_verified", isVerified);

                        Intent intent = new Intent(SplashActivity.this, BottomNavActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                        finish();
                    } else {
                        CustomCookieToast.showFailureToast(SplashActivity.this, response.body().getMessage());
                        Intent intent = new Intent(SplashActivity.this, BottomNavActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                        finish();
                    }

                } else {
                    CustomCookieToast.showFailureToast(SplashActivity.this, response.message());
                    Intent intent = new Intent(SplashActivity.this, BottomNavActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeResponseModel> call, @NonNull Throwable t) {
                CustomCookieToast.showFailureToast(SplashActivity.this, t.getMessage());
                Intent intent = new Intent(SplashActivity.this, BottomNavActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                finish();
            }
        });

    }
}