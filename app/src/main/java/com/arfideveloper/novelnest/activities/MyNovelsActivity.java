package com.arfideveloper.novelnest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.adapters.HomeNovelAdapter;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;


public class MyNovelsActivity extends AppCompatActivity implements HomeNovelAdapter.HomeNovelAdapterCallBacks {
    Context context;
    RelativeLayout layout_backBtn;

    RecyclerView myNovelsRecyclerView;
    List<NovelsModel> novelsModelList;
    HomeNovelAdapter homeNovelDetailsAdapter;

    String novel_image, novel_name, category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_novels);
        context = MyNovelsActivity.this;
        Utilities.setWhiteBars(MyNovelsActivity.this);

        novel_image = getIntent().getStringExtra("novel_image");
        novel_name = getIntent().getStringExtra("novel_name");
        category_id = getIntent().getStringExtra("category_id");

        myNovelsRecyclerView = findViewById(R.id.myNovelRecyclerView);
        layout_backBtn = findViewById(R.id.layout_backBtn);

        layout_backBtn.setOnClickListener(view -> finish());

        novelsModelList = new ArrayList<>();


        myNovelsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager homeLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        myNovelsRecyclerView.setLayoutManager(homeLayoutManager);
        homeNovelDetailsAdapter = new HomeNovelAdapter(context, novelsModelList, this);
        myNovelsRecyclerView.setAdapter(homeNovelDetailsAdapter);
    }

    @Override
    public void onNovelItemClicked(NovelsModel novelsModel) {
        Intent intent = new Intent(context, NovelDetailActivity.class);
        intent.putExtra("novel", novelsModel);
        context.startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);

    }

    @Override
    public void likeNovel(String novelId) {

    }
}