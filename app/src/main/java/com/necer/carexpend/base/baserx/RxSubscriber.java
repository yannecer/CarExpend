package com.necer.carexpend.base.baserx;

import com.google.gson.JsonSyntaxException;
import com.necer.carexpend.MyLog;
import com.necer.carexpend.base.BaseView;
import com.necer.carexpend.exception.ApiException;

import java.io.EOFException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * 对数据请求做统一处理
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {


    private BaseView mView;


    public RxSubscriber(BaseView view) {
        this.mView = view;
    }



    @Override
    public void onCompleted() {
        mView.endLoading();
    }

    @Override
    public void onError(Throwable e) {
        mView.endLoading();

        if (e instanceof EOFException || e instanceof ConnectException || e instanceof SocketException || e instanceof BindException || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            mView.onError("网络异常");
        } else if (e instanceof ApiException) {
            mView.onError(e.getMessage());
        } else if (e instanceof JsonSyntaxException) {
            mView.onError("数据异常");
        } else {
            mView.onError("出错了，请稍后重试");
            MyLog.d("eee::" + e.toString());
        }


    }
    @Override
    public void onNext(T t) {
        call(t);
    }

    @Override
    public void onStart() {
        super.onStart();
        mView.startLoading();
    }

    //返回给view层处理
    protected abstract void call(T t);









}
