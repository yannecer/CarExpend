package com.necer.carexpend.utils;

import android.content.Context;

import com.necer.carexpend.MyLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by necer on 2016/11/17.
 */

public class CompressUtil {

    private List<String> pathList;
    private Context mContext;
    private List<String> compressList;

    private int index;

    public CompressUtil(Context context, List<String> pathList) {
        this.mContext = context;
        this.pathList = pathList;
        compressList = new ArrayList<>();
    }

    public CompressUtil compress() {

        String path = pathList.get(index);
        MyLog.d("path::"+path);
        File file = new File(path);
        Luban.get(mContext)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onSuccess(File file) {
                        compressList.add(file.getAbsolutePath());
                        index++;
                        if (index == pathList.size()) {
                            if (listener != null) {
                                listener.OnCompressComplete(compressList);
                            }
                        } else {
                            compress();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                    }
                }).launch();    //启动压缩

        return this;

    }




    public interface OnCompressCompleteListener {
        void OnCompressComplete(List<String> pathList);

    }


    private OnCompressCompleteListener listener;

    public void setOnCompressCompleteListener(OnCompressCompleteListener listener) {
        this.listener = listener;
    }






}
