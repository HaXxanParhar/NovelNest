package com.arfideveloper.novelnest.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.adapters.FavouriteNovelAdapter;
import com.arfideveloper.novelnest.apimodels.FavouritesResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity implements FavouriteNovelAdapter.FavouritesNovelCallBacks {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    SwipeRefreshLayout layout_swipe;

    RelativeLayout layout_backBtn;

    RecyclerView favoritesRecyclerView;
    List<NovelsModel> favouriteNovelsList;
    FavouriteNovelAdapter favouriteNovelAdapter;

//    int openedDetailPosition;
//    @SuppressLint("NotifyDataSetChanged")

    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        context = FavoritesActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);
        user_id = String.valueOf(Utilities.getInt(context, "user_id"));

        layout_backBtn = findViewById(R.id.layout_backBtn);
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        layout_swipe = findViewById(R.id.layout_swipe);


        layout_swipe.setOnRefreshListener(() -> {
            requestForFavoritesNovel(user_id);
            layout_swipe.setRefreshing(false);
        });

        favouriteNovelsList = new ArrayList<>();

        layout_backBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });


        shadow_View.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        requestForFavoritesNovel(user_id);

    }

    public void requestForFavoritesNovel(String user_id) {
        Call<FavouritesResponseModel> call = service.favouriteNovels(user_id);
        call.enqueue(new Callback<>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<FavouritesResponseModel> call, @NonNull Response<FavouritesResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        favouriteNovelsList = response.body().getNovelsList();
                        showFavoritesNovelRecyclerView(favoritesRecyclerView, favouriteNovelsList);

                    } else {
                        CustomCookieToast.showFailureToast(FavoritesActivity.this, response.message());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavouritesResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(FavoritesActivity.this, t.getMessage());
            }
        });
    }

    private void showFavoritesNovelRecyclerView(RecyclerView favoritesRecyclerView, List<NovelsModel> favouriteNovelsList) {
        favoritesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager favourLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        favoritesRecyclerView.setLayoutManager(favourLayoutManager);
        favouriteNovelAdapter = new FavouriteNovelAdapter(context, favouriteNovelsList, this);
        favoritesRecyclerView.setAdapter(favouriteNovelAdapter);

    }

    @Override
    public void onNovelItemClicked(int position, NovelsModel novelsModel) {
        Intent intent = new Intent(context, NovelDetailActivity.class);
        intent.putExtra("novel_id", novelsModel.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
    }

//    @SuppressLint("NotifyDataSetChanged")
//    ActivityResultLauncher<Intent> onChatGroupJoinedLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if (result.getResultCode() == Activity.RESULT_OK) {
//            Intent intent = result.getData();
//            if (intent != null){
//                boolean isChatGroupJoined = intent.getBooleanExtra("isChatGroupJoined", false);
//                favouriteNovelsList.get(openedDetailPosition).setChat_group_joined(isChatGroupJoined);
//                favouriteNovelAdapter.notifyDataSetChanged();
//            }
//
//        }
//    });

}