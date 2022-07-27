package com.arfideveloper.novelnest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.utilities.HaxxanTime;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import javax.security.auth.callback.Callback;

public class FavouriteNovelAdapter extends RecyclerView.Adapter<FavouriteNovelAdapter.ViewHolder> {
    Context context;
    List<NovelsModel> novelsModelList;
    FavouritesNovelCallBacks callBack;

    public FavouriteNovelAdapter(Context context, List<NovelsModel> novelsModelList, FavouritesNovelCallBacks callBack) {
        this.context = context;
        this.novelsModelList = novelsModelList;
        this.callBack = callBack;

    }

    @NonNull
    @Override
    public FavouriteNovelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_single_profile_novels, parent, false);
        return new FavouriteNovelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteNovelAdapter.ViewHolder holder, int position) {
        NovelsModel novel = novelsModelList.get(position);
        holder.text_novelName.setText(novel.getNovel_title());
        holder.text_time.setText(novel.getCreated_time());

        String time = new HaxxanTime().getTime(novel.getCreated_at());
        holder.text_time.setText(time);

        holder.layout_novel.setOnClickListener(view -> callBack.onNovelItemClicked(position, novel));

        Glide.with(context).load(novel.getNovel_banner()).diskCacheStrategy(DiskCacheStrategy.DATA).error(R.drawable.default_place_holder).placeholder(R.drawable.default_place_holder).into(holder.image_novels);
        if (novel.getIs_favourite().equals("true")) {
            holder.image_likes.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.image_likes.setImageResource(R.drawable.ic_heart_outline);
        }

//        holder.layout_likes.setOnClickListener(view -> {
//            if (!novel.getIs_favourite().equals("true")) {
//                novel.setIs_favourite("true");
//                holder.image_likes.setImageResource(R.drawable.ic_heart_filled);
//            } else {
//                novel.setIs_favourite("false");
//                holder.image_likes.setImageResource(R.drawable.ic_heart_outline);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return novelsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView image_novels;
        TextView text_novelName, text_time;
        ImageView image_likes;
        RelativeLayout layout_likes, layout_novel;
        int user_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_novels = itemView.findViewById(R.id.image_novels);
            text_novelName = itemView.findViewById(R.id.text_novelName);
            text_time = itemView.findViewById(R.id.text_time);
            image_likes = itemView.findViewById(R.id.image_likes);
            layout_likes = itemView.findViewById(R.id.layout_likes);
            layout_novel = itemView.findViewById(R.id.layout_novel);
            user_id = Utilities.getInt(itemView.getContext(), "user_id");
        }
    }
    public interface FavouritesNovelCallBacks{
        void onNovelItemClicked(int position, NovelsModel novelsModel);
    }
}
