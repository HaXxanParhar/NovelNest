package com.arfideveloper.novelnest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.MessageDataModel;
import com.arfideveloper.novelnest.utilities.HaxxanTime;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    Context context;
    List<MessageDataModel> chatList;

    public static final int MESSAGE_TYPE_RECEIVER = 2;
    public static final int MESSAGE_TYPE_SENDER = 1;

    String userId;


    public ChatAdapter(Context context, List<MessageDataModel> chatModelsList) {
        this.context = context;
        this.chatList = chatModelsList;
        userId = String.valueOf(Utilities.getInt(context, "user_id"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_TYPE_RECEIVER) {
            view = LayoutInflater.from(context).inflate(R.layout.single_item_receiver, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.single_item_sender, parent, false);
        }

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageDataModel chatModel = chatList.get(position);
            holder.txt_time.setText(chatModel.getTime());
            holder.txt_message.setText(chatModel.getText());
            final long milliseconds = Long.parseLong(chatModel.getTime()) * 1000;
            String time = new HaxxanTime().getTime(milliseconds);
            holder.txt_time.setText(time);
        if (holder.getItemViewType() == MESSAGE_TYPE_RECEIVER) {
            Glide.with(context).load(chatModel.getSender_image()).diskCacheStrategy(DiskCacheStrategy.DATA).error(R.drawable.default_place_holder).placeholder(R.drawable.default_place_holder).into(holder.image_messageReceive);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (userId.equals(String.valueOf(chatList.get(position).getSender_id()))) {
            return MESSAGE_TYPE_SENDER;
        } else {
            return MESSAGE_TYPE_RECEIVER;
        }
    }

    public void addItem(MessageDataModel message) {
        chatList.add(message);
        notifyItemInserted(chatList.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_messageReceive;
        TextView txt_message, txt_time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_messageReceive = itemView.findViewById(R.id.image_messageReceive);
            txt_message = itemView.findViewById(R.id.txt_message);
            txt_time = itemView.findViewById(R.id.txt_time);
        }
    }

    public interface ChatAdapterCallBack {
        void onInboxClick(int position);
    }
}
