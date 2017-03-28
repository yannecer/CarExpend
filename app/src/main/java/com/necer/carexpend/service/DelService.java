package com.necer.carexpend.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.necer.carexpend.MyLog;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.base.baserx.RxManager;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.utils.MessageEvent;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.necer.carexpend.service.UpService.EXPEND;

/**
 * Created by necer on 2017/3/6.
 */

public class DelService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private Expend expend;
    private RxManager mRxManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mRxManager = new RxManager();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        expend = (Expend) intent.getSerializableExtra(EXPEND);
        mRxManager.post(Constant.LOADING,true);

        expend.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    //删除成功
                    MessageEvent messageEvent = new MessageEvent(false);
                    messageEvent.setAction("del");
                    mRxManager.post(Constant.LOADING,false);
                    DelService.this.stopSelf();
                } else {
                    Toast.makeText(DelService.this,"删除失败："+e.getMessage(),Toast.LENGTH_SHORT).show();

                    MyLog.d("删除失败删除失败。。"+e.toString());
                }
            }
        });
    }
}
