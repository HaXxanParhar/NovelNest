package com.arfideveloper.novelnest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MyNovelAdapter extends RecyclerView.Adapter<MyNovelAdapter.ViewHolder> {
    Context context;
    List<NovelsModel> novelsModelList;


    public MyNovelAdapter() {
    }
    public MyNovelAdapter(Context context, List<NovelsModel> novelsModelList) {
        this.context = context;
        this.novelsModelList = novelsModelList;
    }

    @NonNull
    @Override
    public MyNovelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_my_novel_single_item, parent, false);
        return new MyNovelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNovelAdapter.ViewHolder holder, int position) {
        NovelsModel novel = novelsModelList.get(position);

        holder.text_novelName.setText(novel.getNovel_title());
        holder.text_writerName.setText(novel.getCreated_at());


    }

    @Override
    public int getItemCount() {
        return novelsModelList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView image_novels;
        TextView text_novelName, text_writerName, image_likes, text_likesCounting;
        RelativeLayout layout_editNovel, layout_deleteNovel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_editNovel = itemView.findViewById(R.id.layout_editNovel);
            layout_deleteNovel = itemView.findViewById(R.id.layout_deleteNovel);
            image_novels = itemView.findViewById(R.id.image_novels);
            text_novelName = itemView.findViewById(R.id.text_novelName);
            text_writerName = itemView.findViewById(R.id.text_writerName);
            image_likes = itemView.findViewById(R.id.image_likes);
            text_likesCounting = itemView.findViewById(R.id.text_likesCounting);

        }
    }

}
