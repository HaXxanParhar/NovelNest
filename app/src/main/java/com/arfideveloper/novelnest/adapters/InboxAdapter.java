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
import com.arfideveloper.novelnest.apimodels.InboxDataModel;
import com.arfideveloper.novelnest.utilities.HaxxanTime;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {
    Context context;
    List<InboxDataModel> inboxModelList;
    InboxAdapterCallBacks callBack;


    public InboxAdapter(Context context, List<InboxDataModel> inboxModelList, InboxAdapterCallBacks callBack) {
        this.context = context;
        this.inboxModelList = inboxModelList;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item_inbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InboxDataModel model = inboxModelList.get(position);

        holder.txt_time.setText(model.getLast_message_time());
        holder.txt_novelName.setText(model.getChat_group_name());
        holder.txt_description.setText(model.getLast_message());
        Glide.with(context).load(model.getNovel_image()).diskCacheStrategy(DiskCacheStrategy.DATA).error(R.drawable.default_place_holder).placeholder(R.drawable.default_place_holder).into(holder.image_novel);


        holder.itemView.setOnClickListener(view -> callBack.onInboxClick(position));
    }

    @Override
    public int getItemCount() {
        return inboxModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView image_novel;
        TextView  txt_novelName, txt_description, txt_time;
        RelativeLayout layout_inbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_novel = itemView.findViewById(R.id.image_novel);
            txt_novelName = itemView.findViewById(R.id.txt_novelName);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_time = itemView.findViewById(R.id.txt_time);
            layout_inbox = itemView.findViewById(R.id.layout_inbox);
        }
    }

    public interface InboxAdapterCallBacks{
        void onInboxClick(int position);
    }
}
