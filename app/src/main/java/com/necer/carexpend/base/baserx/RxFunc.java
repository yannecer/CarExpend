package com.necer.carexpend.base.baserx;


import com.necer.carexpend.bean.HttpResult;
import com.necer.carexpend.exception.ApiException;

import rx.functions.Func1;

/**
 * Created by necer on 2016/10/31.
 * 统一处理错误信息
 */

public class RxFunc<T> implements Func1<HttpResult<T>, T> {
    @Override
    public T call(HttpResult<T> httpResult) {


        if (httpResult.getStatus() != 0) {
            throw new ApiException(httpResult.getMsg());
        }

        return httpResult.getResult();
    }

}
