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

import java.util.List;

public class HomeNovelAdapter extends RecyclerView.Adapter<HomeNovelAdapter.ViewHolder> {
    Context context;
    List<NovelsModel> novelsModelList;
    HomeNovelAdapterCallBacks callBack;

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_LOADING = 1;
    boolean isLoadingAdded = false;

    public HomeNovelAdapter(Context context, List<NovelsModel> homeNovelDetailsAdapterList, HomeNovelAdapterCallBacks callBack) {
        this.context = context;
        this.novelsModelList = homeNovelDetailsAdapterList;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(context).inflate(R.layout.single_item_loading, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.layout_home_novel_single_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.getItemViewType() != VIEW_TYPE_LOADING){
            NovelsModel novel = novelsModelList.get(position);
            if (novel != null) {
                Glide.with(context).load(novel.getNovel_banner()).diskCacheStrategy(DiskCacheStrategy.DATA).error(R.drawable.default_place_holder).placeholder(R.drawable.default_place_holder).into(holder.novelImage);
                holder.txt_novelName.setText(novel.getNovel_title());

                if (novel.getIs_favourite().equals("true")) {
                    holder.image_heart.setImageResource(R.drawable.ic_heart_filled);
                } else {
                    holder.image_heart.setImageResource(R.drawable.ic_heart_outline);
                }
                holder.txt_time.setText(novel.getCreated_at());
                String time = new HaxxanTime().getTime(novel.getCreated_at());
                holder.txt_time.setText(time);

                holder.layout_homeNovel.setOnClickListener(view -> callBack.onNovelItemClicked(novel));

                holder.image_heart.setOnClickListener(view -> {
                    if (!novel.getIs_favourite().equals("true")) {
                        novel.setIs_favourite("true");
                        holder.image_heart.setImageResource(R.drawable.ic_heart_filled);
                    } else {
                        novel.setIs_favourite("false");
                        holder.image_heart.setImageResource(R.drawable.ic_heart_outline);
                    }

                    callBack.likeNovel(String.valueOf(novel.getId()));
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return novelsModelList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == novelsModelList.size() - 1 && isLoadingAdded) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    public void addLoadingFooter() {
        if (novelsModelList.size() >= 10){
            isLoadingAdded = true;
            novelsModelList.add(new NovelsModel());
        }
    }

    public void removeLoadingFooter() {
        if (novelsModelList.size() >= 10){
            isLoadingAdded = false;
            int position = novelsModelList.size() - 1;
            NovelsModel result = novelsModelList.get(position);
            if (result != null ) {
                //remove the LOADING item
                novelsModelList.remove(position);
                //notify the removed item
                notifyItemRemoved(position);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView novelImage, image_heart;
        TextView txt_novelName, txt_time;
        RelativeLayout layout_homeNovel;
        int user_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            novelImage = itemView.findViewById(R.id.image_novel);
            txt_novelName = itemView.findViewById(R.id.txt_novelName);
            txt_time = itemView.findViewById(R.id.txt_time);
            image_heart = itemView.findViewById(R.id.image_heart);
            layout_homeNovel = itemView.findViewById(R.id.layout_homeNovel);
            user_id = Utilities.getInt(itemView.getContext(), "user_id");
        }
    }


    public interface HomeNovelAdapterCallBacks{
        void onNovelItemClicked(NovelsModel novelsModel);
        void likeNovel(String novelId);
    }

}
