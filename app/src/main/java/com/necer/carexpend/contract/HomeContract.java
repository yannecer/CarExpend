package com.necer.carexpend.contract;

import com.necer.carexpend.bean.Expend;

import java.util.List;

/**
 * Created by necer on 2016/11/16.
 */

public interface HomeContract {

    interface View  {
        void setDataList(List<Expend> dataList);
        /**
         * 开始请求
         */
        void start();

        /**
         * 结束请求
         */
        void end();
    }

    interface Model{
        void getDataList();
    }


}
