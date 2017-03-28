package com.necer.carexpend.model;

import com.necer.carexpend.application.Api;
import com.necer.carexpend.base.BaseModel;
import com.necer.carexpend.base.baserx.RxFunc;
import com.necer.carexpend.base.baserx.RxSchedulers;
import com.necer.carexpend.base.baserx.RxSubscriber;
import com.necer.carexpend.bean.CarItem;
import com.necer.carexpend.bean.CarItemBean;
import com.necer.carexpend.bean.CarListBean;
import com.necer.carexpend.contract.CarListContract;
import com.necer.carexpend.ui.activity.CarListActivity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by necer on 2017/3/9.
 */

public class CarListModel extends BaseModel<CarListActivity> implements CarListContract.Model {


    @Override
    public void getCarList(String appid, String parentid) {

        mRxManager.add(Api.getDefaultService().getCarList(appid, parentid)
                .map(new RxFunc<List<CarItemBean>>())
                .map(new Func1<List<CarItemBean>, List<CarItem<CarListBean>>>() {
                    @Override
                    public List<CarItem<CarListBean>> call(List<CarItemBean> carListBeen) {
                        List<CarItem<CarListBean>> list = new ArrayList();
                        for (int i = 0; i < carListBeen.size(); i++) {
                            CarItemBean carItemBean = carListBeen.get(i);
                            List<CarListBean> carlist = carItemBean.getCarlist();
                            list.add(new CarItem<CarListBean>(true, carItemBean.getName(), null));
                            for (int j = 0; j < carlist.size(); j++) {
                                list.add(new CarItem<>(false, "", carlist.get(j)));
                            }
                        }
                        return list;
                    }
                }).compose(RxSchedulers.<List<CarItem<CarListBean>>>io_main())
                .subscribe(new RxSubscriber<List<CarItem<CarListBean>>>(mView) {
                    @Override
                    protected void call(List<CarItem<CarListBean>> carItems) {
                        mView.setCarList(carItems);
                    }
                }));

      /*  mRxManager.add(Api.getDefaultService().getCarList2(appid, parentid).compose(RxSchedulers.<String>io_main())
                .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                MyLog.d("throwable::"+throwable);
            }

            @Override
            public void onNext(String s) {

                MyLog.d("sss::"+s);

            }
        }));
*/

    }






}
