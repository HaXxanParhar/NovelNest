package com.arfideveloper.novelnest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.utilities.Utilities;

public class NovelTextDetailsActivity extends AppCompatActivity {
    Context context;
    TextView novel;
    RelativeLayout layout_backBtn;

    String text_novel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_text_details);
        context = NovelTextDetailsActivity.this;
        Utilities.setCustomStatusAndNavColor(NovelTextDetailsActivity.this, R.color.AppColor, R.color.white);

        text_novel = getIntent().getStringExtra("text_novel");
        novel = findViewById(R.id.text_novel);
        layout_backBtn = findViewById(R.id.layout_backBtn);


        novel.setText(text_novel);

        layout_backBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

    }
}