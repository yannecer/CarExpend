package com.necer.carexpend.contract;

import com.necer.carexpend.base.BaseModel;
import com.necer.carexpend.base.BaseView;
import com.necer.carexpend.bean.Expend;

import java.util.List;

/**
 * Created by necer on 2016/11/16.
 */

public interface HomeContract {

    interface View  extends BaseView{
        void setFirstList(List<Expend> dataList);

        void setMoreList(List<Expend> dataList);
    }

    interface Model extends BaseModel{
        void getDataList(int page,boolean isShowLoading,int dataSize);
    }


}
