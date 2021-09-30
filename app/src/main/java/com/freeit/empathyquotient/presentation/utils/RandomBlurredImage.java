package com.freeit.empathyquotient.presentation.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.freeit.empathyquotient.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBlurredImage {
    public final Context ctx;
    public final float bitmapScale;
    public final float blurRadius;

    private static final List<Integer> imageResources = new ArrayList();

    static {
        imageResources.add(R.drawable.bg_1);
        imageResources.add(R.drawable.bg_2);
        imageResources.add(R.drawable.bg_3);
        imageResources.add(R.drawable.bg_4);
    }

    public RandomBlurredImage(Context ctx) {
        this(ctx, 0.4f, 7.5f);
    }

    public RandomBlurredImage(Context ctx, float bitmapScale, float blurRadius) {
        this.ctx = ctx;
        this.bitmapScale = bitmapScale;
        this.blurRadius = blurRadius;
    }

    final Random random = new Random();

    public Bitmap bitmap() {
        final int randomResourceId = imageResources.get(random.nextInt(imageResources.size()));
        final Bitmap image = BitmapFactory.decodeResource(ctx.getResources(), randomResourceId);

        int width = Math.round(image.getWidth() * bitmapScale);
        int height = Math.round(image.getHeight() * bitmapScale);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(ctx);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(blurRadius);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

}
