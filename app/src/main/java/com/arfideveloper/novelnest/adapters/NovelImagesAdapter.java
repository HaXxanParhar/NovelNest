package com.arfideveloper.novelnest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.MyNovelsActivity;
import com.arfideveloper.novelnest.activities.NovelDetailActivity;
import com.arfideveloper.novelnest.apimodels.NovelBannersModel;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class NovelImagesAdapter extends PagerAdapter {
    Context context;
    List<NovelBannersModel> list;

    public NovelImagesAdapter(Context context, List<NovelBannersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_novel_pic_single_item, container, false);

        RoundedImageView ivCover = view.findViewById(R.id.image_novel);
        Glide.with(context).load(list.get(position).getNovel_banner()).error(R.drawable.default_place_holder).placeholder(R.drawable.default_place_holder).into(ivCover);

        NovelBannersModel model = list.get(position);

        ivCover.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, NovelDetailActivity.class);
            intent.putExtra("novel_id", model.getId());
            context.startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

