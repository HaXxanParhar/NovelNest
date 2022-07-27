package com.arfideveloper.novelnest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.auth.LoginActivity;
import com.arfideveloper.novelnest.adapters.ViewPagerFragmentAdapter;
import com.arfideveloper.novelnest.fragments.ExploreFragment;
import com.arfideveloper.novelnest.fragments.HomeFragment;
import com.arfideveloper.novelnest.fragments.InboxFragment;
import com.arfideveloper.novelnest.fragments.SettingsFragment;
import com.arfideveloper.novelnest.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class BottomNavActivity extends AppCompatActivity {
    Context context;

    public static boolean showAddedToast;
    public static String addedToastTitle = "Story";//it can be story, post, collection
    public static String addedToastMessage = "Story";//it can be story, post, collection

    ViewPager2 viewPager2;
    RelativeLayout layout_home, layout_explore, layout_inbox, layout_settings;
    ImageView home_ic, explore_ic, inbox_ic, menu_ic;
    LinearLayout bottomBar;
    List<Fragment> fragmentsList;
    ViewPagerFragmentAdapter myAdapter;

    int position;
    boolean isPressedOnce;

    public static Fragment homeFragment, exploreFragment, inboxFragment, settingsFragment;
    public static boolean touchEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bottom);

        context = BottomNavActivity.this;
        Utilities.setCustomStatusAndNavColor(BottomNavActivity.this, R.color.AppColor, R.color.white);

        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            startActivity(new Intent(BottomNavActivity.this, LoginActivity.class));
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        }

        viewPager2 = findViewById(R.id.viewPager2);
        layout_home = findViewById(R.id.layout_home);
        home_ic = findViewById(R.id.home_ic);
        layout_explore = findViewById(R.id.layout_explore);
        explore_ic = findViewById(R.id.explore_ic);
        layout_inbox = findViewById(R.id.layout_inbox);
        inbox_ic = findViewById(R.id.inbox_ic);
        layout_settings = findViewById(R.id.layout_settings);
        menu_ic = findViewById(R.id.menu_ic);
        bottomBar = findViewById(R.id.bottomBar);


        fragmentsList = new ArrayList<>();
        homeFragment = new HomeFragment();
        exploreFragment = new ExploreFragment();
        inboxFragment = new InboxFragment();
        settingsFragment = new SettingsFragment();


        fragmentsList.add(homeFragment);
        fragmentsList.add(exploreFragment);
        fragmentsList.add(inboxFragment);
        fragmentsList.add(settingsFragment);

        myAdapter = new ViewPagerFragmentAdapter(BottomNavActivity.this, fragmentsList);
        viewPager2.setAdapter(myAdapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(4);

        setDrawable(0);
        position = 0;

        layout_home.setOnClickListener(v -> {
            touchEnabled = true;
            viewPager2.setCurrentItem(0, false);
            setDrawable(0);
            position = 0;
        });

        layout_explore.setOnClickListener(v -> {
                viewPager2.setCurrentItem(1, false);
                position = 1;
                setDrawable(1);
        });

        layout_inbox.setOnClickListener(v -> {
                viewPager2.setCurrentItem(2, false);
                position = 2;
                setDrawable(2);
        });

        layout_settings.setOnClickListener(v -> {
                viewPager2.setCurrentItem(  3, false);
                position =   3;
                setDrawable(3);

        });

    }
    public void onBackPressed() {
        if (position == 0) {
            if (isPressedOnce) {
                super.onBackPressed();
            } else {
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            isPressedOnce = true;
            new Handler(Looper.getMainLooper()).postDelayed(() -> isPressedOnce = false, 1500);
        } else {
            viewPager2.setCurrentItem(0, false);
            setDrawable(0);
            position = 0;
        }
    }

    public void setDrawable(int selected) {

        if (selected == 0) {
            home_ic.setColorFilter(ContextCompat.getColor(BottomNavActivity.this, R.color.AppColor));
        } else {
            home_ic.setColorFilter(ContextCompat.getColor(BottomNavActivity.this, R.color.black));
        }

        if (selected == 1) {
            explore_ic.setColorFilter(ContextCompat.getColor(BottomNavActivity.this, R.color.AppColor));
        } else {
            explore_ic.setColorFilter(ContextCompat.getColor(BottomNavActivity.this, R.color.black));
        }
        if (selected == 2) {
            inbox_ic.setColorFilter(ContextCompat.getColor(BottomNavActivity.this, R.color.AppColor));
        } else {
            inbox_ic.setColorFilter(ContextCompat.getColor(BottomNavActivity.this, R.color.black));
        }
        if (selected == 3) {
            menu_ic.setColorFilter(ContextCompat.getColor(BottomNavActivity.this, R.color.AppColor));
        } else {
            menu_ic.setColorFilter(ContextCompat.getColor(BottomNavActivity.this, R.color.black));
        }

    }
}