package com.necer.carexpend.contract;

import com.necer.carexpend.base.BaseView;
import com.necer.carexpend.bean.CarItem;

import java.util.List;

/**
 * Created by necer on 2017/3/8.
 */

public interface CarBrandContract {

    interface  View <T> extends BaseView {
        void setCarBrandList(List<CarItem<T>> carBrandItemList);
    }

    interface Model {
       void getCarBrandList(String appid);
    }
}
