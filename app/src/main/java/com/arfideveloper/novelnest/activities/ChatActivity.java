package com.arfideveloper.novelnest.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.adapters.ChatAdapter;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.apimodels.MessageDataModel;
import com.arfideveloper.novelnest.apimodels.UserMessagesResponseModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    String user_id;
    String group_id;
    String chat_group_name;

    RecyclerView recyclerView_messages;
    List<MessageDataModel> messagesList;
    ChatAdapter chatAdapter;

    EditText edit_message;
    RelativeLayout btn_send, layout_backBtn, layout_optionBtn;
    ImageView image_gallery, back_ic;
    TextView txt_profileName;

    boolean isAlreadySet = false;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        context = ChatActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        group_id = getIntent().getStringExtra("group_id");
        chat_group_name = getIntent().getStringExtra("chat_group_name");
        user_id = String.valueOf(Utilities.getInt(context, "user_id"));

        recyclerView_messages = findViewById(R.id.recyclerView_messages);


        edit_message = findViewById(R.id.edit_message);
        btn_send = findViewById(R.id.btn_send);
        image_gallery = findViewById(R.id.image_gallery);
        back_ic = findViewById(R.id.back_ic);
        layout_backBtn = findViewById(R.id.layout_backBtn);
        txt_profileName = findViewById(R.id.txt_profileName);
        layout_optionBtn = findViewById(R.id.layout_optionBtn);

        txt_profileName.setText(chat_group_name);


        messagesList = new ArrayList<>();

        shadow_View.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        getMessagesList();


        layout_backBtn.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

        layout_optionBtn.setOnClickListener(view -> {
            bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
            View sheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_chat_option, findViewById(R.id.bottom_sheet_chat_option));

            LinearLayout btn_cancel = sheetView.findViewById(R.id.btn_cancel);
            LinearLayout btn_leaveGroup = sheetView.findViewById(R.id.btn_leaveGroup);
            LinearLayout btn_muteNotification = sheetView.findViewById(R.id.btn_muteNotification);

            btn_cancel.setOnClickListener(view1 -> bottomSheetDialog.dismiss());

            bottomSheetDialog.setDismissWithAnimation(true);
            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();
        });

        btn_send.setOnClickListener(view -> {
            String message = edit_message.getText().toString();
            if (!message.isEmpty()) {
                sendOfflineMessage(message, group_id);
                sendMessage(user_id, group_id, message);
                edit_message.setText("");
            } else {
                Toast.makeText(context, "Please enter some message", Toast.LENGTH_SHORT).show();
            }
        });

        edit_message.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
                recyclerView_messages.scrollToPosition(messagesList.size() - 1));
    }


    public void getMessagesList() {
        Call<UserMessagesResponseModel> call = service.getUserMessages(user_id, group_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserMessagesResponseModel> call, @NonNull Response<UserMessagesResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            messagesList = response.body().getMessagesList();
                            showChatRecyclerview();

                            //Only set the pusher once
                            if (!isAlreadySet) {
                                setMessagePusher();
                                isAlreadySet = true;
                            }
                        } else {
                            CustomCookieToast.showFailureToast(ChatActivity.this, response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(ChatActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(ChatActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserMessagesResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(ChatActivity.this, t.getMessage());
            }
        });
    }

    private void showChatRecyclerview() {
        recyclerView_messages.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_messages.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(context, messagesList);
        recyclerView_messages.setAdapter(chatAdapter);
        recyclerView_messages.scrollToPosition(this.messagesList.size() - 1);
    }


    public void sendMessage(String user_id, String group_id, String message) {
        Call<MainResponseModel> call = service.sendMessage(user_id, group_id, message);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (!status) {
                        CustomCookieToast.showFailureToast(ChatActivity.this, response.body().getMessage());
                    }

                } else {
                    CustomCookieToast.showFailureToast(ChatActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void sendOfflineMessage(String text, String group_id) {

        //creating the message model to add in the list
        MessageDataModel messageModel = new MessageDataModel();
        messageModel.setText(text);
        messageModel.setTime((System.currentTimeMillis() / 1000) + "");//seconds
        messageModel.setSender_id(Integer.parseInt(user_id));
        messageModel.setSender_name("Muhammad Shahid");
        messageModel.setChat_group_id(group_id);
        addNewMessage(messageModel);
    }


    public void setMessagePusher() {
        // pusher
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("ea1bf1fa38b2c91ec57f", options);

        Channel channel = pusher.subscribe("chat." + user_id + ".messages");

        channel.bind("messages", event -> {
            Gson gson = new Gson();
            ArrayList<MessageDataModel> messages = gson.fromJson(event.getData(), new TypeToken<ArrayList<MessageDataModel>>() {
            }.getType());

            if (messages != null && !messages.isEmpty()) {
                final MessageDataModel message = messages.get(0);
                if (!user_id.equals(String.valueOf(message.getSender_id()))) {
                    runOnUiThread(() -> addNewMessage(message));
                }
            }
        });
        pusher.connect();
    }

    private void addNewMessage(MessageDataModel message) {
        if (chatAdapter == null) {
            messagesList = new ArrayList<>();
            messagesList.add(message);
            showChatRecyclerview();
            setMessagePusher();
        } else {
            chatAdapter.addItem(message);
            recyclerView_messages.scrollToPosition(messagesList.size() - 1);
        }
    }
}
