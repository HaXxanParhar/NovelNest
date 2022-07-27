package com.arfideveloper.novelnest.fragments;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.NovelDetailActivity;
import com.arfideveloper.novelnest.activities.auth.LoginActivity;
import com.arfideveloper.novelnest.activities.auth.SignUpActivity;
import com.arfideveloper.novelnest.activities.auth.SplashActivity;
import com.arfideveloper.novelnest.adapters.HomeNovelAdapter;
import com.arfideveloper.novelnest.adapters.NovelImagesAdapter;
import com.arfideveloper.novelnest.apimodels.CategoriesModel;
import com.arfideveloper.novelnest.apimodels.CategoryResponseModel;
import com.arfideveloper.novelnest.apimodels.HomeResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelBannersModel;
import com.arfideveloper.novelnest.apimodels.NovelsModel;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelsPaginationDataModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.PaginationScrollListener;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements HomeNovelAdapter.HomeNovelAdapterCallBacks {
    Context context;
    GetDataService service;

    String user_id, home_loading_from;

    public static List<CategoriesModel> categoriesModelList;
    TabLayout home_tabLayout;
    TabLayout tabDots;

    ViewPager trendingNovelViewPager;
    List<NovelBannersModel> novelBannersModelList;
    NovelImagesAdapter novelImagesAdapter;

    RecyclerView homeRecyclerView;
    List<NovelsModel> novelsModelList;
    List<NovelsModel> newNovelsModelList;
    HomeNovelAdapter homeNovelAdapter;
    LinearLayout layout_notFound;
    RelativeLayout layout_viewPager;

    private boolean isLoading = false;
    private int currentPage = 1;
    private int totalPages = 0;
    private int perPageCount = 0;

    boolean isTabClicked;

    String categoryId = "1";

    Timer timer;
    int NUM_PAGES = 0;
    int viewPagerCurrentPage = 0;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = requireActivity();

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        user_id = String.valueOf(Utilities.getInt(context, "user_id"));
        home_loading_from = Utilities.getString(context, "home_loading_from");

        trendingNovelViewPager = view.findViewById(R.id.trendingNovelViewPager);
        home_tabLayout = view.findViewById(R.id.home_tabLayout);
        homeRecyclerView = view.findViewById(R.id.recyclerView_homeNovel);
        layout_notFound = view.findViewById(R.id.layout_notFound);
        tabDots = view.findViewById(R.id.tabDots);
        layout_viewPager = view.findViewById(R.id.layout_viewPager);

        categoriesModelList = new ArrayList<>();
        novelBannersModelList = new ArrayList<>();
        novelsModelList = new ArrayList<>();


        switch (home_loading_from) {
            case "splash":
                novelsModelList = SplashActivity.novelsModelList;
                novelBannersModelList = SplashActivity.novelBannersModelList;
                categoriesModelList = SplashActivity.categoriesModelList;

                setTrendingNovelViewPager(trendingNovelViewPager, novelBannersModelList);
                setRecyclerView(homeRecyclerView, novelsModelList);
                setTabLayout(categoriesModelList);

                totalPages = SplashActivity.totalPages;
                if (currentPage < totalPages) {
                    homeNovelAdapter.addLoadingFooter();
                } else {
                    homeNovelAdapter.removeLoadingFooter();
                }
                homeNovelAdapter.notifyDataSetChanged();

                Utilities.saveString(context, "home_loading_from", "false");
                break;
            case "login":
                novelsModelList = LoginActivity.novelsModelList;
                novelBannersModelList = LoginActivity.novelBannersModelList;
                categoriesModelList = LoginActivity.categoriesModelList;

                setTrendingNovelViewPager(trendingNovelViewPager, novelBannersModelList);
                setRecyclerView(homeRecyclerView, novelsModelList);
                setTabLayout(categoriesModelList);

                totalPages = LoginActivity.totalPages;
                if (currentPage < totalPages) {
                    homeNovelAdapter.addLoadingFooter();
                } else {
                    homeNovelAdapter.removeLoadingFooter();
                }
                homeNovelAdapter.notifyDataSetChanged();

                Utilities.saveString(context, "home_loading_from", "false");
                break;
            case "signup":
                novelsModelList = SignUpActivity.novelsModelList;
                novelBannersModelList = SignUpActivity.novelBannersModelList;
                categoriesModelList = SignUpActivity.categoriesModelList;

                setTrendingNovelViewPager(trendingNovelViewPager, novelBannersModelList);
                setRecyclerView(homeRecyclerView, novelsModelList);
                setTabLayout(categoriesModelList);

                totalPages = SignUpActivity.totalPages;
                if (currentPage < totalPages) {
                    homeNovelAdapter.addLoadingFooter();
                } else {
                    homeNovelAdapter.removeLoadingFooter();
                }
                homeNovelAdapter.notifyDataSetChanged();

                Utilities.saveString(context, "home_loading_from", "false");
                break;
            case "false":
                requestForHomeScreenData(user_id);
                break;
        }

        home_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPage = 1;
                isTabClicked = true;
                categoryId = String.valueOf(categoriesModelList.get(tab.getPosition()).getId());
                requestForNovelsOnCategoryBases(user_id, categoryId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    public void requestForHomeScreenData(String user_id) {
        Call<HomeResponseModel> call = service.getHomeScreen(user_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HomeResponseModel> call, @NonNull Response<HomeResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        //get data and show
                        novelBannersModelList = new ArrayList<>();
                        novelsModelList = new ArrayList<>();
                        newNovelsModelList = new ArrayList<>();
                        categoriesModelList = new ArrayList<>();

                        novelBannersModelList = response.body().getNovelBannersModelList();
                        categoriesModelList = response.body().getCategoriesModelList();

                        NovelsPaginationDataModel novelsPaginationDataModel = response.body().getNovelsPaginationDataModel();

                        perPageCount = novelsPaginationDataModel.getPer_page();
                        totalPages = novelsPaginationDataModel.getLast_page();

                        novelsModelList = novelsPaginationDataModel.getNovelsModel();


                        setTrendingNovelViewPager(trendingNovelViewPager, novelBannersModelList);
                        setRecyclerView(homeRecyclerView, novelsModelList);
                        setTabLayout(categoriesModelList);

                        if (currentPage < totalPages) {
                            homeNovelAdapter.addLoadingFooter();
                        }

                    } else {
                        CustomCookieToast.showFailureToast(requireActivity(), response.body().getMessage());
                    }

                } else {
                    CustomCookieToast.showFailureToast(requireActivity(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeResponseModel> call, @NonNull Throwable t) {
                CustomCookieToast.showFailureToast(requireActivity(), t.getMessage());
            }
        });

    }

    private void setTabLayout(List<CategoriesModel> listItem) {
        for (CategoriesModel categoriesModel : listItem) {
            home_tabLayout.addTab(home_tabLayout.newTab().setText(categoriesModel.getCategory_name()));
        }
    }

    private void setTrendingNovelViewPager(ViewPager viewPager, List<NovelBannersModel> novelBannersModelList) {
        if (novelBannersModelList.size() > 0){
            NUM_PAGES = novelBannersModelList.size();
            layout_viewPager.setVisibility(View.VISIBLE);

            novelImagesAdapter = new NovelImagesAdapter(context, novelBannersModelList);
            viewPager.setAdapter(novelImagesAdapter);
            tabDots.setupWithViewPager(viewPager, true);

            timer = new Timer();
            timer.scheduleAtFixedRate(new MyTimerTask(viewPager, context),3000,3000);
        }else {
            layout_viewPager.setVisibility(View.GONE);
        }

    }

    private void setRecyclerView(RecyclerView homeRecyclerView, List<NovelsModel> novelsModelList) {
        if (novelsModelList != null && novelsModelList.size() > 0) {
            layout_notFound.setVisibility(View.GONE);
            homeRecyclerView.setVisibility(View.VISIBLE);
            homeRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            homeRecyclerView.setLayoutManager(linearLayoutManager);
            homeNovelAdapter = new HomeNovelAdapter(context, novelsModelList, this);
            homeRecyclerView.setAdapter(homeNovelAdapter);

            homeRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    currentPage++;
                    new Handler(Looper.getMainLooper()).postDelayed(() -> loadMoreNovelsForHomePagination(homeNovelAdapter), 600);
                }

                @Override
                public int getPerPageCount() {
                    return perPageCount;
                }

                @Override
                public boolean isLastPage() {
                    return currentPage == totalPages;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }

            });
        } else {
            layout_notFound.setVisibility(View.VISIBLE);
            homeRecyclerView.setVisibility(View.GONE);
        }
    }

    private void loadMoreNovelsForHomePagination(HomeNovelAdapter homeNovelAdapter) {
        if (!isTabClicked) {
            Call<HomeResponseModel> call = service.getHomeScreenWithPagination(user_id, String.valueOf(currentPage));
            call.enqueue(new Callback<>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NotNull Call<HomeResponseModel> call, @NotNull Response<HomeResponseModel> response) {
                    isLoading = false;
                    homeNovelAdapter.removeLoadingFooter();
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        boolean status = response.body().isStatus();
                        if (status) {
                            newNovelsModelList = new ArrayList<>();
                            // Working with response and saving data in Shared Preferences
                            NovelsPaginationDataModel novelsPaginationDataModel = response.body().getNovelsPaginationDataModel();
                            newNovelsModelList = novelsPaginationDataModel.getNovelsModel();
                            novelsModelList.addAll(newNovelsModelList);

                            if (currentPage < totalPages) {
                                homeNovelAdapter.addLoadingFooter();
                            }
                            homeNovelAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HomeResponseModel> call, @NotNull Throwable t) {
                    isLoading = false;
                    homeNovelAdapter.removeLoadingFooter();
                }
            });
        } else {
            Call<CategoryResponseModel> call = service.categoryNovelsListWithPagination(user_id, categoryId, String.valueOf(currentPage));
            call.enqueue(new Callback<>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NotNull Call<CategoryResponseModel> call, @NotNull Response<CategoryResponseModel> response) {
                    isLoading = false;
                    homeNovelAdapter.removeLoadingFooter();
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        boolean status = response.body().isStatus();
                        if (status) {
                            newNovelsModelList = new ArrayList<>();
                            // Working with response and saving data in Shared Preferences
                            NovelsPaginationDataModel novelsPaginationDataModel = response.body().getNovelsPaginationDataModel();
                            newNovelsModelList = novelsPaginationDataModel.getNovelsModel();
                            novelsModelList.addAll(newNovelsModelList);

                            if (currentPage < totalPages) {
                                homeNovelAdapter.addLoadingFooter();
                            }
                            homeNovelAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CategoryResponseModel> call, @NotNull Throwable t) {
                    isLoading = false;
                    homeNovelAdapter.removeLoadingFooter();
                }
            });
        }

    }

    public void requestForNovelsOnCategoryBases(String user_id, String category_id) {
        Call<CategoryResponseModel> call = service.categoryNovelsList(user_id, category_id);
        call.enqueue(new Callback<>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<CategoryResponseModel> call, @NonNull Response<CategoryResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        novelsModelList = new ArrayList<>();
                        newNovelsModelList = new ArrayList<>();

                        novelsModelList = response.body().getNovelsPaginationDataModel().getNovelsModel();
                        perPageCount = response.body().getNovelsPaginationDataModel().getPer_page();
                        totalPages = response.body().getNovelsPaginationDataModel().getLast_page();

                        setRecyclerView(homeRecyclerView, novelsModelList);

                        if (currentPage < totalPages) {
                            homeNovelAdapter.addLoadingFooter();
                        }
                        homeNovelAdapter.notifyDataSetChanged();
                    } else {
                        CustomCookieToast.showFailureToast(requireActivity(), response.body().getMessage());
                    }

                } else {
                    CustomCookieToast.showFailureToast(requireActivity(), response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponseModel> call, @NonNull Throwable t) {
                CustomCookieToast.showFailureToast(requireActivity(), t.getMessage());
            }
        });
    }


    @Override
    public void onNovelItemClicked(NovelsModel novelsModel) {
        Intent intent = new Intent(context, NovelDetailActivity.class);
        intent.putExtra("novel_id", novelsModel.getId());
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
    }

    @Override
    public void likeNovel(String novelId) {
        Call<MainResponseModel> call = service.likeNovel(user_id, novelId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (!status) {
                        CustomCookieToast.showFailureToast(requireActivity(), response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {
                CustomCookieToast.showFailureToast(requireActivity(), t.getMessage());
            }
        });
    }

    public class MyTimerTask extends TimerTask {
        ViewPager viewPager;
        Context context;

        public MyTimerTask(ViewPager viewPager, Context context) {
            this.viewPager = viewPager;
            this.context = context;
        }

        @Override
        public void run() {
            // here you check the value of getActivity() and break up if needed
            if(context == null)
                return;
            ((Activity)context).runOnUiThread(() -> {
                if (viewPagerCurrentPage == NUM_PAGES-1) {
                    viewPagerCurrentPage = 0;
                }
                else {
                    viewPagerCurrentPage++;
                }
                viewPager.setCurrentItem(viewPagerCurrentPage, true);
            });
        }
    }
}