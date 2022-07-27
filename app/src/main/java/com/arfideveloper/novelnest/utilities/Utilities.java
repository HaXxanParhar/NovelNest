package com.arfideveloper.novelnest.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.arfideveloper.novelnest.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utilities {

    static ProgressDialog dialog;
    Context context;
    private static String sharePreferencesName = "NovelNestSharedStorage";
    public Utilities(Context context) {
        this.context = context;
    }

    public static void showProgressDialog(Context ctx, String msg) {
        dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog = null;
        }

    }

    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();

    }

    public static Boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    public static String getString(Context context, String key) {

        SharedPreferences sharedPref = context.getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");

    }

    public static void clearSharedPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();


    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyBoard(View view, Context context) {
        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);

    }

    public static String changeDateFormate(String date, String from, String to) {

        String finalDate = date;
//      SimpleDateFormat date_format = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat date_format = new SimpleDateFormat(from);

        Date datee = null;
        try {
            datee = date_format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
        SimpleDateFormat df = new SimpleDateFormat(to);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datee);
        finalDate = df.format(calendar.getTime());

        return finalDate;

    }

    public static String differenceBetweenDated(String dates_formate, String dateEnd, String dateStr) {

        String days = "";

        SimpleDateFormat sdf = new SimpleDateFormat(dates_formate);

        try {
            Date dateS = sdf.parse(dateStr);
            Date dateE = sdf.parse(dateEnd);

            long diff = dateE.getTime() - dateS.getTime();


            days = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return days;

    }

    public static String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdFormat.format(calendar.getTime());

        return currentDate;

    }

    public static String formatTwoDecimal(Context context, int number) {


        if (number == 0) {

            return String.valueOf("RS 0.00");
        } else {

            String qa = String.valueOf(number);
            String nocomma = qa.replace(",", "");
            Float f = Float.parseFloat(nocomma);
            String.format("%.2f", f);
            DecimalFormat formatter = new DecimalFormat("#,###,###.00#");
            String yourFormattedString = formatter.format(f);
            return String.valueOf("RS " + yourFormattedString);
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static String getCommaSeparatedString(ArrayList<String> list) {
        String result = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            result = String.join(",", list);
        } else {
            StringBuilder csvBuilder = new StringBuilder();
            for (String string : list) {
                csvBuilder.append(string);
                csvBuilder.append(",");
            }
            result = csvBuilder.toString();
        }
        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Uri insertImage(ContentResolver cr, Bitmap source, String title, String description) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        // Add the date meta data to ensure the image is added at the front of the gallery
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        Uri url = null;

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (source != null) {
                try (OutputStream imageOut = cr.openOutputStream(url)) {
                    source.compress(Bitmap.CompressFormat.JPEG, 30, imageOut);
                }
            } else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        return url;
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static String getStringTimeFromPattern(long milliseconds, String pattern) {
        String output = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            output = simpleDateFormat.format(new Date(milliseconds));
        } catch (Exception e) {
            Log.e("YAM", "Exception : " + e.getMessage());
            e.printStackTrace();
        }
        return output;
    }

    public static String getFullDateString(long milliseconds) {
        String output = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy 'at' hh:mm a");
            output = simpleDateFormat.format(new Date(milliseconds));
        } catch (Exception e) {
            Log.e("YAM", "Exception : " + e.getMessage());
            e.printStackTrace();
        }
        return output;
    }

    public static void setWhiteBars(Activity activity) {
        Window window = activity.getWindow();
        View view = window.getDecorView();
        view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        window.setNavigationBarColor(activity.getResources().getColor(android.R.color.white));
        window.setStatusBarColor(activity.getResources().getColor(android.R.color.white));
    }

    public static void setBlackBars(Activity activity) {
        Window window = activity.getWindow();
        View view = window.getDecorView();
        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        window.setNavigationBarColor(activity.getResources().getColor(android.R.color.black));
        window.setStatusBarColor(activity.getResources().getColor(android.R.color.black));
    }

    public static void setCustomStatusAndNavColor(Activity activity, int statusColor, int navColor) {
        Window window = activity.getWindow();
        View view = window.getDecorView();
        view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        window.setNavigationBarColor(activity.getResources().getColor(navColor));
        window.setStatusBarColor(activity.getResources().getColor(statusColor));
    }

    public static void loadThumbnailViaGlide(Context context, String videoUrl, ImageView imageView) {
        if (videoUrl.isEmpty()){
            imageView.setImageResource(R.drawable.ic_image_add_line);
        }
        else {
            try {
                RequestOptions requestOptions = new RequestOptions();

                Glide.with(context).setDefaultRequestOptions(requestOptions).load(videoUrl).diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(false).dontAnimate().into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
