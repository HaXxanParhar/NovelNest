package com.arfideveloper.novelnest.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.adapters.HomeNovelAdapter;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.apimodels.SearchResponseModel;
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

public class SearchActivity extends AppCompatActivity implements HomeNovelAdapter.HomeNovelAdapterCallBacks {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    EditText edit_searchNovel;
    RelativeLayout layout_searchNovel;

    String user_id;
    TextView text_Cancel;

    RecyclerView searchRecyclerView;
    List<NovelsModel> novelsModelList;
    HomeNovelAdapter homeNovelDetailsAdapter;

//    int openedDetailPosition;
//    @SuppressLint("NotifyDataSetChanged")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = SearchActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        user_id = String.valueOf(Utilities.getInt(context, "user_id"));

        text_Cancel = findViewById(R.id.text_Cancel);
        edit_searchNovel = findViewById(R.id.edit_searchNovel);
        layout_searchNovel = findViewById(R.id.layout_searchNovel);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);


        novelsModelList = new ArrayList<>();

        text_Cancel.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });

        edit_searchNovel.requestFocus();

        edit_searchNovel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                if (!keyword.isEmpty()) {
                    requestForSearchNovel(user_id, keyword);
                } else {
                    requestForSearchNovel(user_id, "././././././././");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void requestForSearchNovel(String user_id, String search_term) {
        Call<SearchResponseModel> call = service.searchNovel(user_id, search_term);
        call.enqueue(new Callback<>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SearchResponseModel> call, @NonNull Response<SearchResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        novelsModelList = response.body().getNovelsModelList();
                        showSearchRecyclerview(searchRecyclerView, novelsModelList);

                    } else {
                        CustomCookieToast.showFailureToast(SearchActivity.this, response.body().getMessage());
                    }

                } else {
                    CustomCookieToast.showFailureToast(SearchActivity.this, response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<SearchResponseModel> call, @NonNull Throwable t) {
                CustomCookieToast.showFailureToast(SearchActivity.this, t.getMessage());

            }
        });
    }


    private void showSearchRecyclerview(RecyclerView homeRecyclerView, List<NovelsModel> novelsModelList) {
        homeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager homeLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        homeRecyclerView.setLayoutManager(homeLayoutManager);
        homeNovelDetailsAdapter = new HomeNovelAdapter(context, novelsModelList, this);
        homeRecyclerView.setAdapter(homeNovelDetailsAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onNovelItemClicked(NovelsModel novelsModel) {
        Intent intent = new Intent(context, NovelDetailActivity.class);
        intent.putExtra("novel_id", novelsModel.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);

    }

    @Override
    public void likeNovel(String novel_id) {
        Call<MainResponseModel> call = service.likeNovelSearch(user_id, novel_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (!status) {
                        CustomCookieToast.showFailureToast(SearchActivity.this, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {
                CustomCookieToast.showFailureToast(SearchActivity.this, t.getMessage());
            }
        });
    }
//    @SuppressLint("NotifyDataSetChanged")
//    ActivityResultLauncher<Intent> onChatGroupJoinedLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if (result.getResultCode() == Activity.RESULT_OK) {
//            Intent intent = result.getData();
//            if (intent != null){
//                boolean isChatGroupJoined = intent.getBooleanExtra("isChatGroupJoined", false);
//                novelsModelList.get(openedDetailPosition).setChat_group_joined(isChatGroupJoined);
//                homeNovelDetailsAdapter.notifyDataSetChanged();
//            }
//
//        }
//    });


}