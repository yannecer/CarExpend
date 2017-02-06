package com.necer.carexpend.utils;

import android.content.Context;
import android.graphics.Color;

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
                        .setLabel(Constant.EXPENDITEMS[0])
                        .setResId(Constant.EXPENDICON[0])
                        .setLabelColor(Color.parseColor("#16B180"))
                //.setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel(Constant.EXPENDITEMS[1])
                        .setResId(Constant.EXPENDICON[1])
                        .setLabelColor(Color.parseColor("#B1AF16"))
                //.setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel(Constant.EXPENDITEMS[2])
                        .setResId(Constant.EXPENDICON[2])
                        .setLabelColor(Color.parseColor("#B1AF16"))
                //.setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel(Constant.EXPENDITEMS[3])
                        .setResId(Constant.EXPENDICON[3])
                        .setLabelColor(Color.parseColor("#019B79"))
                //.setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel(Constant.EXPENDITEMS[4])
                        .setResId(Constant.EXPENDICON[4])
                        .setLabelColor(Color.parseColor("#019B79"))
                //.setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel(Constant.EXPENDITEMS[5])
                        .setResId(Constant.EXPENDICON[5])
                        .setLabelColor(Color.parseColor("#CE0505"))
                // .setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel(Constant.EXPENDITEMS[6])
                        .setResId(Constant.EXPENDICON[6])
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
