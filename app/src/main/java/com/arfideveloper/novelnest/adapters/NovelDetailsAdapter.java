package com.arfideveloper.novelnest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.MyNovelsActivity;
import com.arfideveloper.novelnest.models.NovelDetailsModel;

import java.util.List;

public class NovelDetailsAdapter extends RecyclerView.Adapter<NovelDetailsAdapter.ViewHolder>{
    Context context;
    List<NovelDetailsModel> novelDetailsModelList;

    public NovelDetailsAdapter() {
    }
    public NovelDetailsAdapter(Context context, List<NovelDetailsModel> novelDetailsModelList) {
        this.context = context;
        this.novelDetailsModelList = novelDetailsModelList;
    }

    @NonNull
    @Override
    public NovelDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_single_profile_novels, parent, false);
        return new NovelDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelDetailsAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MyNovelsActivity.class);
            context.startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });
    }

    @Override
    public int getItemCount() {
        return novelDetailsModelList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
