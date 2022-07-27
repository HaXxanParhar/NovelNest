package com.arfideveloper.novelnest.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

public class ProfileImageActivity extends AppCompatActivity {
    Context context;

    ZoomageView image_view;
    String imageUrl;
    RelativeLayout layout_cross;

    final int PIC_CROP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);
        Utilities.setBlackBars(ProfileImageActivity.this);
        context = ProfileImageActivity.this;

        image_view = findViewById(R.id.image_view);
        layout_cross = findViewById(R.id.layout_cross);

        imageUrl = getIntent().getStringExtra("imageUrl");

        Glide.with(context).load(imageUrl).into(image_view);

        layout_cross.setOnClickListener(view -> finish());

    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}