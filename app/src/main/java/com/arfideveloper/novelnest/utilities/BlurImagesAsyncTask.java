package com.arfideveloper.novelnest.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BlurImagesAsyncTask extends AsyncTask<String, Void, Bitmap> {

    Context context;
    ImageView iv;
    int radius;

    public BlurImagesAsyncTask(Context context, ImageView iv, int radius) {
        this.context = context;
        this.iv = iv;
        this.radius = radius;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }

        try {
            if (url != null) {
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {
            Bitmap bm = CreateBlurredImage(result, radius);
            iv.setImageBitmap(bm);
        }
    }

    private Bitmap CreateBlurredImage(final Bitmap bm, int radius) {
        Bitmap blurredBitmap;
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation input;
        input = Allocation.createFromBitmap(rs, bm, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SCRIPT);
        script.setRadius(radius);
        script.setInput(input);
        blurredBitmap = Bitmap.createBitmap(bm.getWidth(),
                bm.getHeight(), Bitmap.Config.ARGB_8888);
        Allocation output;
        output = Allocation.createTyped(rs, input.getType());
        script.forEach(output);
        output.copyTo(blurredBitmap);
        script.destroy();
        return blurredBitmap;
    }
}