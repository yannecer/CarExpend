package com.necer.carexpend.contract;

import com.necer.carexpend.base.BaseView;
import com.necer.carexpend.bean.CarItem;

import java.util.List;

/**
 * Created by necer on 2017/3/9.
 */

public interface CarListContract {

    interface View<T> extends BaseView {
        void setCarList(List<CarItem<T>> carList);
    }

    interface Model {
        void getCarList(String appid,String parentid);
    }




}
