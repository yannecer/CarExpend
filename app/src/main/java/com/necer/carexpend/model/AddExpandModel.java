package com.necer.carexpend.model;

import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.bean.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by necer on 2016/11/16.
 */

public class AddExpandModel {

    public void submitExpand(User user, String money, String date, String describe, List<String> imageList) {

        if (imageList.size() == 0) {
            submitWithoutPic(user, money, date, describe);
        } else {
            submitPic(user, money, date, describe,imageList);
        }

    }




    private void submitPic(final User user, final String money, final String date, final String describe, final List<String> imageList) {

        String[] paths = new String[imageList.size()];
        for (int i = 0; i < imageList.size(); i++) {
            paths[i] = imageList.get(i);
        }

        BmobFile.uploadBatch(paths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if (list1.size() == imageList.size()) {

                    submitWithPic( user,  money,  date,  describe, list1);
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });




    }

    private void submitWithPic(User user, String money, String date, String describe, List<String> imageList) {


        Expend expend = new Expend();
        expend.setUser(user);
        expend.setDescribe(describe);
        expend.setMoney(Double.parseDouble(money));
        expend.setDate(date);
        expend.setImageUrl(imageList);

        expend.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });




    }


    private void submitWithoutPic(User user, String money, String date, String describe) {

        Expend expend = new Expend();
        expend.setUser(user);
        expend.setDescribe(describe);
        expend.setMoney(Double.parseDouble(money));
        expend.setDate(date);
        expend.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {


            }
        });

    }


}
