package com.arfideveloper.novelnest.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.NovelDetailActivity;
import com.arfideveloper.novelnest.activities.SearchActivity;
import com.arfideveloper.novelnest.adapters.ExploreAdapter;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.apimodels.SearchResponseModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment implements ExploreAdapter.ExploreAdapterCallBack {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    RecyclerView exploreRecyclerView;
    List<NovelsModel> exploreModelList;
    ExploreAdapter exploreAdapter;

    SwipeRefreshLayout layout_swipe;

    String user_id;
    RelativeLayout layout_searchNovel;

    int openedDetailPosition;
    @SuppressLint("NotifyDataSetChanged")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        context = requireActivity();

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = rootView.findViewById(R.id.shadow_View);
        loading =rootView.findViewById(R.id.loading);
        user_id = String.valueOf(Utilities.getInt(context, "user_id"));

        layout_searchNovel = rootView.findViewById(R.id.layout_searchNovel);
        exploreRecyclerView = rootView.findViewById(R.id.exploreRecyclerView);
        layout_swipe = rootView.findViewById(R.id.layout_swipe);


        layout_swipe.setOnRefreshListener(() -> {
            String category_id = String.valueOf(1);
            getExploreList(user_id);
            layout_swipe.setRefreshing(false);
        });

        layout_searchNovel.setOnClickListener(view -> {
            Intent intent = new Intent(context, SearchActivity.class);
            context.startActivity(intent);
            requireActivity().overridePendingTransition(0, 0);
        });

        exploreModelList = new ArrayList<>();
        String category_id = String.valueOf(1);
        getExploreList(user_id);

        return rootView;

    }

    public void getExploreList (String user_id){
        Call<SearchResponseModel> call = service.exploreNovelList(user_id);
        call.enqueue(new Callback<>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SearchResponseModel> call, @NonNull Response<SearchResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        //get data and show

                        exploreModelList = response.body().getNovelsModelList();
                        setExploreRecyclerView(exploreRecyclerView, exploreModelList);


                    } else {
                        CustomCookieToast.showFailureToast(requireActivity(), response.body().getMessage());
                    }

                } else {
                    CustomCookieToast.showFailureToast(requireActivity(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(requireActivity(), t.getMessage());

            }
        });
    }
    private void setExploreRecyclerView(RecyclerView exploreRecyclerView, List<NovelsModel>exploreModelList) {
        exploreRecyclerView.setHasFixedSize(true);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 2);
        exploreRecyclerView.setLayoutManager(linearLayoutManager);
        exploreAdapter = new ExploreAdapter(context, exploreModelList, this);
        exploreRecyclerView.setAdapter(exploreAdapter);
    }

    @Override
    public void onNovelItemClicked(int position, NovelsModel novelsModel) {
        openedDetailPosition = position;
        Intent intent = new Intent(context, NovelDetailActivity.class);
        intent.putExtra("novel_id", novelsModel.getId());
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
    }

    @Override
    public void likeNovel(String novelId) {

    }

//    @SuppressLint("NotifyDataSetChanged")
//    ActivityResultLauncher<Intent> onChatGroupJoinedLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if (result.getResultCode() == Activity.RESULT_OK) {
//            Intent intent = result.getData();
//            if (intent != null){
//                boolean isChatGroupJoined = intent.getBooleanExtra("isChatGroupJoined", false);
//                exploreModelList.get(openedDetailPosition).setChat_group_joined(isChatGroupJoined);
//                exploreAdapter.notifyDataSetChanged();
//            }
//
//        }
//    });

}