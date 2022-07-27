package com.arfideveloper.novelnest.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.aghajari.zoomhelper.ZoomHelper;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelDetailResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelDetailsDataModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.MyStrings;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.google.android.material.switchmaterial.SwitchMaterial;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelDetailActivity extends AppCompatActivity {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    RelativeLayout layout_backBtn, layout_joinChat;
    TextView textview_novel, text_novelName, text_join;
    SwitchMaterial switch_dark_mode;

    RelativeLayout layout_topBar;
    LinearLayout layout_novelNameText;
    String user_id, novel_id;

    ZoomHelper zoomHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = NovelDetailActivity.this;
        boolean isNightMode = Utilities.getBoolean(context, MyStrings.IS_NIGHT_MODE);
        if (isNightMode) {
            setTheme(R.style.Theme_NovelNestNight);
            Utilities.setBlackBars(NovelDetailActivity.this);
        } else {
            setTheme(R.style.Theme_NovelNestDay);
            Utilities.setCustomStatusAndNavColor(NovelDetailActivity.this, R.color.AppColor, R.color.white);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_detail);


        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);
        user_id = String.valueOf(Utilities.getInt(context, "user_id"));
        novel_id = String.valueOf(getIntent().getIntExtra("novel_id", 0));

        layout_novelNameText = findViewById(R.id.layout_novelNameText);
        layout_joinChat = findViewById(R.id.layout_joinChat);

        layout_topBar = findViewById(R.id.layout_topBar);
        layout_backBtn = findViewById(R.id.layout_backBtn);
        text_novelName = findViewById(R.id.text_novelName);
        textview_novel = findViewById(R.id.textview_novel);
        text_join = findViewById(R.id.text_join);
        switch_dark_mode = findViewById(R.id.switch_dark_mode);
        switch_dark_mode.setChecked(isNightMode);

        switch_dark_mode.setOnCheckedChangeListener((compoundButton, checked) -> {
            Utilities.saveBoolean(context, MyStrings.IS_NIGHT_MODE, checked);
            recreate();
        });

//       get novel details api
        shadow_View.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        requestForNovelDetails(user_id, String.valueOf(novel_id));
        text_join.setVisibility(View.VISIBLE);


        layout_backBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

    }

    public void requestForJoining(String user_id, String novel_id) {
        Call<MainResponseModel> call = service.joinChatGroup(user_id, novel_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (!status) {
                            CustomCookieToast.showRequiredToast(NovelDetailActivity.this, response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(NovelDetailActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(NovelDetailActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {
                CustomCookieToast.showFailureToast(NovelDetailActivity.this, t.getMessage());
            }
        });
    }

    public void requestForNovelDetails(String user_id, String novel_id) {
        Call<NovelDetailResponseModel> call = service.getNovelDetails(user_id, novel_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<NovelDetailResponseModel> call, @NonNull Response<NovelDetailResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            NovelDetailsDataModel novelDetailsDataModel = response.body().getNovelDetailsDataModel();
                            showNovelData(novelDetailsDataModel);
                            String category_id = novelDetailsDataModel.getCategory_id();
                            String novel_title = novelDetailsDataModel.getNovel_title();
                            String novel_banner = novelDetailsDataModel.getNovel_banner();
                            String novel_cover = novelDetailsDataModel.getNovel_cover();
                            String background_color = novelDetailsDataModel.getBackground_color();
                            String novel_type = novelDetailsDataModel.getNovel_type();
                            String novel_text = novelDetailsDataModel.getNovel_text();
                            String novel_file = novelDetailsDataModel.getNovel_file();
                            String category_name = novelDetailsDataModel.getCategory_name();

                            Utilities.saveString(context, "category_id", category_id);



                        }
                    } else {
                        CustomCookieToast.showFailureToast(NovelDetailActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(NovelDetailActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NovelDetailResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(NovelDetailActivity.this, t.getMessage());
            }
        });
    }

    private void initZoom() {
        zoomHelper = ZoomHelper.Companion.getInstance();
        zoomHelper.setMaxScale(Float.MAX_VALUE);
        zoomHelper.setShadowColor(Color.TRANSPARENT);
        zoomHelper.setMaxShadowAlpha(0f);
        zoomHelper.setShadowAlphaFactory(0);
        zoomHelper.setDismissDuration(300);
        zoomHelper.setEnabled(true);

        ZoomHelper.Companion.addZoomableView(layout_novelNameText);

        zoomHelper.addOnZoomStateChangedListener((zoomHelper, view, isZooming) -> showViews(isZooming));
    }

    private void showViews(boolean isZooming) {
        final int duration = 300;
        if (isZooming) {
            layout_topBar.animate().alpha(0).setDuration(duration);
        } else {
            layout_topBar.animate().alpha(1).setDuration(duration);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return ZoomHelper.Companion.getInstance().dispatchTouchEvent(ev, this) || super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }

    @SuppressLint("SetTextI18n")
    private void showNovelData(NovelDetailsDataModel novel) {
        layout_joinChat.setVisibility(View.VISIBLE);
        textview_novel.setText(novel.getNovel_text());
        text_novelName.setText(novel.getNovel_title());
        text_novelName.requestFocus();
        initZoom();

        if (novel.getIs_joined()) {
            text_join.setText("Joined");
        } else {
            text_join.setText("Join chat group");
        }

        text_join.setOnClickListener(view -> {
            if (novel.getIs_joined()) {
                novel.setIs_joined(false);
                text_join.setText("Join chat group");
                Toast toast = Toast.makeText(NovelDetailActivity.this, "Leave Chat Group", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                novel.setIs_joined(true);
                text_join.setText("Joined");
                Toast toast = Toast.makeText(NovelDetailActivity.this, "Join Chat", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            requestForJoining(user_id, String.valueOf(novel_id));
        });
    }

}