package com.necer.carexpend.contract;

import com.necer.carexpend.base.BaseModel;
import com.necer.carexpend.base.BaseView;

import java.util.List;

/**
 * Created by necer on 2016/11/17.
 */

public interface AddExpendContract {


    interface View extends BaseView {
        void onAddSucceed();
    }


    interface Model extends BaseModel {
        void submitExpend( String money, String date, String describe,int type,  List<String> imageList);
    }

}

