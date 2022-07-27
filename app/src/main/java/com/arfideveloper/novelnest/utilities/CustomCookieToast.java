package com.arfideveloper.novelnest.utilities;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arfideveloper.novelnest.R;

import org.aviran.cookiebar2.CookieBar;

public class CustomCookieToast {
    public static final int DURATION = 3000;

    public static void showSuccessToast(Activity context, String msg) {

        CookieBar.build(context).
                setCustomView(R.layout.custom_banner)
                .setCustomViewInitializer(view -> {
                    LinearLayout cookie = view.findViewById(R.id.ll_cookie);
                    ImageView ivIcon = view.findViewById(R.id.iv_icon);
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    TextView tvMessage = view.findViewById(R.id.tv_message);
                    tvTitle.setText("Success!");
                    tvMessage.setText(msg);
                    ivIcon.setImageResource(R.drawable.ic_baseline_sentiment_satisfied);
                    cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp);
                })
                .setCookiePosition(CookieBar.TOP)
                .setDuration(DURATION)
                .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
                .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
                .show();
    }

    public static void showFailureToast(Activity context, String msg) {

        CookieBar.build(context).
                setCustomView(R.layout.custom_banner)
                .setCustomViewInitializer(view -> {
                    LinearLayout cookie = view.findViewById(R.id.ll_cookie);
                    ImageView ivIcon = view.findViewById(R.id.iv_icon);
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    TextView tvMessage = view.findViewById(R.id.tv_message);
                    tvTitle.setText("Failed!");
                    tvMessage.setText(msg);
                    ivIcon.setImageResource(R.drawable.ic_baseline_warning_24);
                    cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp);
                })
                .setCookiePosition(CookieBar.TOP)
                .setDuration(DURATION)
                .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
                .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
                .show();
    }

    public static void showSuccessToast(Activity context, String title, String msg) {

        CookieBar.build(context).
                setCustomView(R.layout.custom_banner)
                .setCustomViewInitializer(view -> {

                    LinearLayout cookie = view.findViewById(R.id.ll_cookie);
                    ImageView ivIcon = view.findViewById(R.id.iv_icon);
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    TextView tvMessage = view.findViewById(R.id.tv_message);

                    tvTitle.setText(title);
                    tvMessage.setText(msg);

                    ivIcon.setImageResource(R.drawable.ic_baseline_sentiment_satisfied);
                    cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp);
                })
                .setCookiePosition(CookieBar.TOP)
                .setDuration(DURATION)
                .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
                .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
                .show();
    }

    public static void showFailureToast(Activity context, String title, String msg) {

        CookieBar.build(context).
                setCustomView(R.layout.custom_banner)
                .setCustomViewInitializer(view -> {

                    LinearLayout cookie = view.findViewById(R.id.ll_cookie);
                    ImageView ivIcon = view.findViewById(R.id.iv_icon);
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    TextView tvMessage = view.findViewById(R.id.tv_message);

                    tvTitle.setText(title);
                    tvMessage.setText(msg);
                    ivIcon.setImageResource(R.drawable.ic_warning_sign);
                    cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp);
                })
                .setCookiePosition(CookieBar.TOP)
                .setDuration(DURATION)
                .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
                .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
                .show();

    }

    public static void showRequiredToast(Activity context, String pleaseEnter) {

        CookieBar.build(context).
                setCustomView(R.layout.custom_banner)
                .setCustomViewInitializer(view -> {

                    LinearLayout cookie = view.findViewById(R.id.ll_cookie);
                    ImageView ivIcon = view.findViewById(R.id.iv_icon);
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    TextView tvMessage = view.findViewById(R.id.tv_message);

                    tvTitle.setText("Required");
                    tvMessage.setText(pleaseEnter);
                    ivIcon.setImageResource(R.drawable.ic_warning_sign);
                    cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp);
                })
                .setCookiePosition(CookieBar.TOP)
                .setDuration(DURATION)
                .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
                .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
                .show();
    }

    public static void showSelectToast(Activity context, String pleaseSelect) {

        CookieBar.build(context).
                setCustomView(R.layout.custom_banner)
                .setCustomViewInitializer(view -> {

                    LinearLayout cookie = view.findViewById(R.id.ll_cookie);
                    ImageView ivIcon = view.findViewById(R.id.iv_icon);
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    TextView tvMessage = view.findViewById(R.id.tv_message);

                    tvTitle.setText("Required");
                    tvMessage.setText("Please select the " + pleaseSelect);
                    ivIcon.setImageResource(R.drawable.ic_warning_sign);
                    cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp);
                })
                .setCookiePosition(CookieBar.TOP)
                .setDuration(DURATION)
                .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
                .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
                .show();
    }

    public static void showNoInternetToast(Activity context) {
        CookieBar.build(context)
                .setTitle("Can not connect to Internet!")
                .setMessage("Make sure you have internet connection and Try again!")
                .setIcon(R.drawable.ic_baseline_wifi_off_24)
                .setCookiePosition(CookieBar.TOP)
                .setDuration(3000)
                .setBackgroundColor(R.color.red_Color)
                .show();
    }

    public static void showExceptionToast(Activity context, Throwable t) {

        CookieBar.build(context).
                setCustomView(R.layout.custom_banner)
                .setCustomViewInitializer(view -> {

                    LinearLayout cookie = view.findViewById(R.id.ll_cookie);
                    ImageView ivIcon = view.findViewById(R.id.iv_icon);
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    TextView tvMessage = view.findViewById(R.id.tv_message);

                    tvTitle.setText("Exception!");
                    tvMessage.setText(t.getMessage());
                    ivIcon.setImageResource(R.drawable.ic_warning_sign);
                    cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp);
                })
                .setCookiePosition(CookieBar.TOP)
                .setDuration(DURATION + 1000)
                .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
                .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
                .show();
        t.printStackTrace();
    }



}
