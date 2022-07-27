package com.arfideveloper.novelnest.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.ChatActivity;
import com.arfideveloper.novelnest.adapters.InboxAdapter;
import com.arfideveloper.novelnest.apimodels.InboxDataModel;
import com.arfideveloper.novelnest.apimodels.InboxResponseModel;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InboxFragment extends Fragment implements InboxAdapter.InboxAdapterCallBacks {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    String user_id;
    SwipeRefreshLayout layout_swipe;

    RecyclerView recyclerView_inbox;
    List<InboxDataModel> inboxModelList;
    InboxAdapter inboxAdapter;

    boolean isAlreadySet = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        context = requireActivity();

        recyclerView_inbox = view.findViewById(R.id.recyclerView_inbox);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = view.findViewById(R.id.shadow_View);
        loading = view.findViewById(R.id.loading);
        user_id = String.valueOf(Utilities.getInt(context, "user_id"));
        layout_swipe = view.findViewById(R.id.layout_swipe);

        inboxModelList = new ArrayList<>();

        shadow_View.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        getInbox(user_id);

        layout_swipe.setOnRefreshListener(() -> {
            getInbox(user_id);
            layout_swipe.setRefreshing(false);
        });


        return view;
    }

    public void getInbox(String user_id) {
        Call<InboxResponseModel> call = service.getInboxList(user_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<InboxResponseModel> call, @NonNull Response<InboxResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            inboxModelList = response.body().getInboxDataModel();
                            showInboxRecyclerView(recyclerView_inbox, inboxModelList);

                            if (!isAlreadySet) {
                                setPusher();
                                isAlreadySet = true;
                            }


                        } else {
                            CustomCookieToast.showFailureToast(requireActivity(), response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(requireActivity(), response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(requireActivity(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<InboxResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(requireActivity(), t.getMessage());
            }
        });
    }

    @Override
    public void onInboxClick(int position) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("group_id", inboxModelList.get(position).getChat_group_id());
        intent.putExtra("text", inboxModelList.get(position).getChat_group_name());
        intent.putExtra("chat_group_name", inboxModelList.get(position).getChat_group_name());
        context.startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
    }

    private void showInboxRecyclerView(RecyclerView recyclerView_inbox, List<InboxDataModel> inboxModelList) {
        recyclerView_inbox.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView_inbox.setLayoutManager(linearLayoutManager);
        inboxAdapter = new InboxAdapter(context, inboxModelList, this);
        recyclerView_inbox.setAdapter(inboxAdapter);
    }

    public void setPusher() {
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("ea1bf1fa38b2c91ec57f", options);
        Channel channel = pusher.subscribe("chat." + user_id + ".messanger");

        channel.bind("messanger", event -> {
            Gson gson = new Gson();
            MainResponseModel inboxResponse = gson.fromJson(event.getData(), MainResponseModel.class);
            if (inboxResponse.isStatus()) {
                requireActivity().runOnUiThread(() -> {
                    getInbox(user_id);
                });
            } else {
                Log.e("Pusher", "Error : " + inboxResponse.getMessage());
            }
        });
        pusher.connect();
    }
}