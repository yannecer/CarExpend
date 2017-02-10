package com.necer.carexpend.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.NineGridView;

import cn.bmob.v3.Bmob;

/**
 * Created by necer on 2016/11/16.
 */

public class CarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NineGridView.setImageLoader(new PicassoImageLoader());
        Bmob.initialize(this, "9416b1587b7bf3347599bcfc848a62f9");
    }

    /** Picasso 加载 */
    private class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
           /* Picasso.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_image)//
                    .error(R.drawable.ic_default_image)//
                    .into(imageView);*/


            Glide.with(context).load(url).into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
