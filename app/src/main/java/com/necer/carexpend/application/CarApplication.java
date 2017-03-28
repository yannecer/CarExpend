package com.necer.carexpend.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lzy.ninegrid.NineGridView;
import com.necer.carexpend.R;

import cn.bmob.v3.Bmob;

/**
 * Created by necer on 2016/11/16.
 */

public class CarApplication extends MultiDexApplication {

    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

        NineGridView.setImageLoader(new GlideImageLoader());
        Bmob.initialize(this, Constant.BMOBAPPKEY);
    }


    private class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, final ImageView imageView, String url) {
            Glide.with(context).load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(resource);
                        }
                    });
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {

        return mAppContext;
    }
}
