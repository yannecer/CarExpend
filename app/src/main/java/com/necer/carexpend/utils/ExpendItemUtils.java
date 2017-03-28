package com.necer.carexpend.utils;

import android.graphics.Color;

import com.necer.carexpend.application.Constant;
import com.necer.carexpend.bean.ExpendItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by necer on 2017/3/6.
 */

public class ExpendItemUtils {
    public static List<ExpendItem> getExpendItemList() {
        List<ExpendItem> list = new ArrayList<>();
        for (int i = 0; i < Constant.EXPENDITEMS.length; i++) {
            ExpendItem expendItem = new ExpendItem();
            expendItem.setName(Constant.EXPENDITEMS[i]);
            expendItem.setColor(Color.parseColor(Constant.EXPENDCOLOR[i]));
            expendItem.setIcon(Constant.EXPENDICON[i]);
            expendItem.setType(Constant.EXPENDTYPE[i]);
            list.add(expendItem);
        }
        return list;
    }


    public static int getTypeByName(String name) {
        List<String> list = Arrays.asList(Constant.EXPENDITEMS);
        List<Integer> typeList = new ArrayList<>();
        for (int i : Constant.EXPENDTYPE) {
            typeList.add(i);
        }
        int indexOf = list.indexOf(name);
        return typeList.get(indexOf);
    }

    public static String getNameByType(int type) {
        List<String> list = Arrays.asList(Constant.EXPENDITEMS);
        List<Integer> typeList = new ArrayList<>();
        for (int i : Constant.EXPENDTYPE) {
            typeList.add(i);
        }
        int indexOf = typeList.indexOf(type);
        return list.get(indexOf);
    }

    public static int getIconByType(int type) {
        List<ExpendItem> expendItemList = getExpendItemList();
        for (int i = 0; i < expendItemList.size(); i++) {
            ExpendItem expendItem = expendItemList.get(i);
            if (expendItem.getType() == type) {
                return expendItem.getIcon();
            }
        }
        return -1;
    }

}
