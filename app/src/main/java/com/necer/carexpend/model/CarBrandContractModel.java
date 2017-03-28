package com.necer.carexpend.model;

import android.text.TextUtils;

import com.necer.carexpend.application.Api;
import com.necer.carexpend.base.BaseModel;
import com.necer.carexpend.base.baserx.RxFunc;
import com.necer.carexpend.base.baserx.RxSchedulers;
import com.necer.carexpend.base.baserx.RxSubscriber;
import com.necer.carexpend.bean.CarBrandBean;
import com.necer.carexpend.bean.CarItem;
import com.necer.carexpend.contract.CarBrandContract;
import com.necer.carexpend.ui.activity.CarBrandActivity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by necer on 2017/3/8.
 */

public class CarBrandContractModel extends BaseModel<CarBrandActivity> implements CarBrandContract.Model {
    @Override
    public void getCarBrandList(String appid) {
        mRxManager.add(Api.getDefaultService().getCarBrandList(appid)
                .map(new RxFunc<List<CarBrandBean>>()).map(new Func1<List<CarBrandBean>, List<CarItem<CarBrandBean>>>() {
                    @Override
                    public List<CarItem<CarBrandBean>> call(List<CarBrandBean> carBrandList) {

                        List<CarItem<CarBrandBean>> list = new ArrayList<>();
                        String lastInitial = "";//上一个首字母
                        for (int i = 0; i < carBrandList.size(); i++) {
                            CarBrandBean carBrandBean = carBrandList.get(i);
                            if (!TextUtils.equals(carBrandBean.getInitial(), lastInitial)) {
                                if (lastInitial.equals("Z")) {
                                    break;
                                }
                                list.add(new CarItem(true, carBrandBean.getInitial(), carBrandBean));
                                lastInitial = carBrandBean.getInitial();
                            }
                            list.add(new CarItem(false, "", carBrandBean));
                        }
                        return list;
                    }
                })
                .compose(RxSchedulers.<List<CarItem<CarBrandBean>>>io_main())
                .subscribe(new RxSubscriber<List<CarItem<CarBrandBean>>>(mView) {
                    @Override
                    protected void call(List<CarItem<CarBrandBean>> carBrandItems) {
                        mView.setCarBrandList(carBrandItems);
                    }
                }));

    }


}


