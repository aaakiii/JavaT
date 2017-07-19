package com.example.aki.javaq.Domain.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by MinaFujisawa on 2017/07/11.
 */

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, int photoViewWidth, int photoViewHeight){
        // read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();

        assert options.inJustDecodeBounds = false;
        BitmapFactory.decodeFile(path, options);

        //figure out how much to scale down by
        int inSampleSize = Math.round(photoViewHeight > photoViewWidth ? photoViewHeight : photoViewWidth);; //2なら画像の縦横のピクセル数を1/2にしたサイズ。3なら1/3にしたサイズで読み込まれる。
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        //Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }
}
