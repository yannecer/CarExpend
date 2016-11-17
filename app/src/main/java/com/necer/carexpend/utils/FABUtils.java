package com.necer.carexpend.utils;

import android.content.Context;
import android.graphics.Color;

import com.necer.carexpend.R;
import com.necer.carexpend.application.Constant;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necer on 2016/11/16.
 */

public class FABUtils {

    public static RapidFloatingActionHelper buildFAB(Context mContext, RapidFloatingActionContentLabelList rfaContent, RapidFloatingActionLayout rfaLayout, RapidFloatingActionButton rfaButton) {


        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel(Constant.expendItems[0])
                .setResId(R.mipmap.car_fix)
                .setLabelColor(Color.parseColor("#16B180"))
                //.setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel(Constant.expendItems[1])
                .setResId(R.mipmap.car_insurance)
                .setLabelColor(Color.parseColor("#B1AF16"))
                //.setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel(Constant.expendItems[2])
                .setResId(R.mipmap.car_maintenance)
                .setLabelColor(Color.parseColor("#B1AF16"))
                //.setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel(Constant.expendItems[3])
                .setResId(R.mipmap.car_parking_fee)
                .setLabelColor(Color.parseColor("#019B79"))
                //.setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel(Constant.expendItems[4])
                .setResId(R.mipmap.car_road_fee)
                .setLabelColor(Color.parseColor("#019B79"))
                //.setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel(Constant.expendItems[5])
                .setResId(R.mipmap.car_violation)
                .setLabelColor(Color.parseColor("#CE0505"))
               // .setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel(Constant.expendItems[6])
                .setResId(R.mipmap.car_washing)
                .setLabelColor(Color.parseColor("#057BCE"))
               // .setWrapper(3)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(mContext, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(mContext, 5))
        ;

        RapidFloatingActionHelper rfabHelper = new RapidFloatingActionHelper(
                mContext,
                rfaLayout,
                rfaButton,
                rfaContent).build();
        return rfabHelper;

    }


}
