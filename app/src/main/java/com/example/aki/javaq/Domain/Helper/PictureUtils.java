package com.example.aki.javaq.Domain.Helper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by MinaFujisawa on 2017/07/11.
 */

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path){
        // read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();

        assert options.inJustDecodeBounds = false;
        BitmapFactory.decodeFile(path, options);

//        float photoViewWidthDp = photoViewWidthPixel / res.getDisplayMetrics().density;
//        int inSampleSize = Math.round(photoViewWidthDp);
        int inSampleSize = 2;
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        //Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }
}
