package com.necer.carexpend.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.necer.carexpend.application.Constant;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.utils.CompressUtil;
import com.necer.carexpend.utils.MessageEvent;
import com.necer.carexpend.utils.SharePrefUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by necer on 2017/2/4.
 */

public class UpService extends Service {

    private Expend expend;
    public static final String EXPEND = "expend";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        expend = (Expend) intent.getSerializableExtra(EXPEND);

        EventBus.getDefault().post(new MessageEvent(true));

        if (expend.getImageUrl().size() != 0) {
            new CompressUtil(this, expend.getImageUrl()).compress().setOnCompressCompleteListener(new CompressUtil.OnCompressCompleteListener() {
                @Override
                public void OnCompressComplete(List<String> pathList) {
                    expend.setImageUrl(pathList);
                    submit(expend);
                }
            });

        } else {
            submit(expend);
        }
    }

    private void submit(final Expend expend) {
        final List<String> imageUrl = expend.getImageUrl();
        if (imageUrl.size() != 0) {
            String[] array = imageUrl.toArray(new String[imageUrl.size()]);
            BmobFile.uploadBatch(array, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    if (list1.size() == imageUrl.size()) {
                        expend.setImageUrl(list1);
                        save(expend);
                    }
                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {
                }

                @Override
                public void onError(int i, String s) {
                }
            });
        } else {
            save(expend);
        }
    }

    private void save(Expend expend) {
        expend.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //成功,清除
                    SharePrefUtil.remove(UpService.this, Constant.WAITFORUPLOAD);
                    EventBus.getDefault().post(new MessageEvent(false));
                    UpService.this.stopSelf();
                }
            }
        });
    }
}
