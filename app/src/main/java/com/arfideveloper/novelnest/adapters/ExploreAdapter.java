package com.arfideveloper.novelnest.adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.utilities.HaxxanTime;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {
    Context context;
    List<NovelsModel> exploreModelList;
    ExploreAdapterCallBack callBack;

    public ExploreAdapter(Context context, List<NovelsModel> exploreModelList, ExploreAdapterCallBack callBack) {
        this.context = context;
        this.exploreModelList = exploreModelList;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ExploreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item_explore, parent, false);
        return new ExploreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.ViewHolder holder, int position) {
        NovelsModel novel = exploreModelList.get(position);
        holder.txt_novelName.setText(novel.getNovel_title());
        holder.txt_time.setText(novel.getCreated_at());
        String time = new HaxxanTime().getTime(novel.getCreated_at());
        holder.txt_time.setText(time);

        holder.layout_novelBg.setBackgroundColor(Color.parseColor(novel.getBackground_color()));

        Glide.with(context).load(novel.getNovel_banner()).diskCacheStrategy(DiskCacheStrategy.DATA).error(R.drawable.default_place_holder).placeholder(R.drawable.default_place_holder).into(holder.
                image_novel);
        holder.layout_novelBg.setOnClickListener(view -> {
            callBack.onNovelItemClicked(position,novel);

        });

        callBack.likeNovel(String.valueOf(novel.getId()));
    }

    @Override
    public int getItemCount() {
        return exploreModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout layout_novelBg;
        RoundedImageView image_novel;
        TextView txt_novelName, txt_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_novelBg = itemView.findViewById(R.id.layout_novelBg);
            image_novel = itemView.findViewById(R.id.image_novel);
            txt_novelName = itemView.findViewById(R.id.txt_novelName);
            txt_time = itemView.findViewById(R.id.txt_time);
        }
    }

    public interface ExploreAdapterCallBack{
            void onNovelItemClicked(int position, NovelsModel novelsModel);
            void likeNovel(String novelId);

    }
}
