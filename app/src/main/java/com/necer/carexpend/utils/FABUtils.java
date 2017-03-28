package com.necer.carexpend.utils;

import android.content.Context;

import com.necer.carexpend.bean.ExpendItem;
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

        List<ExpendItem> expendItemList = ExpendItemUtils.getExpendItemList();

        for (int i = 0; i < expendItemList.size(); i++) {
            ExpendItem expendItem = expendItemList.get(i);
            items.add(new RFACLabelItem<Integer>()
                            .setLabel(expendItem.getName())
                            .setResId(expendItem.getIcon())
                            .setLabelColor(expendItem.getColor())
            );
        }
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
