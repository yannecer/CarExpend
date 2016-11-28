package com.necer.carexpend.model;

import android.content.Context;

import com.necer.carexpend.MyLog;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.contract.AddExpendContract;
import com.necer.carexpend.utils.CompressUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by necer on 2016/11/16.
 */

public class AddExpandModel implements AddExpendContract.Model{

    private AddExpendContract.View mView;
    private User user;
    private Context context;

    public AddExpandModel(Context context,User user, AddExpendContract.View view) {
        this.context = context;
        this.user = user;
        this.mView = view;
        MyLog.d("user::"+user.getMobilePhoneNumber());
    }



    @Override
    public void submitExpend( String money, String date, String describe, int type, List<String> imageList) {
        mView.startLoading();
        if (imageList.size() == 0) {
            submit(user,money, date, describe,type, null);
        } else {
            submitPic(money, date, describe,type,imageList);
        }
    }




    /**
     * 先上传图片
     * @param money
     * @param date
     * @param describe
     * @param type
     * @param imageList
     */
    private void submitPic(final String money, final String date, final String describe, final int type, final List<String> imageList) {


        CompressUtil compressUtil = new CompressUtil(context,imageList);

        compressUtil.compress();
        compressUtil.setOnCompressCompleteListener(new CompressUtil.OnCompressCompleteListener() {
            @Override
            public void OnCompressComplete(List<String> pathList) {

                for (int i = 0; i <pathList.size() ; i++) {
                    MyLog.d("pathList::::" + pathList.get(i));
                }



                String[] paths = new String[imageList.size()];
                for (int i = 0; i < imageList.size(); i++) {
                    paths[i] = imageList.get(i);
                }
                BmobFile.uploadBatch(paths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {




                        if (list1.size() == imageList.size()) {
                            submit( user,  money, date,  describe, type,  list1);
                        }
                    }
                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {
                    }

                    @Override
                    public void onError(int i, String s) {
                        mView.onError(s);
                        mView.endLoading();
                    }
                });



            }
        });



    }

    /**
     * 添加
     * @param user
     * @param money
     * @param date
     * @param describe
     * @param type
     * @param imageList
     */
    private void submit(User user, String money, String date, String describe,int type,  List<String> imageList) {
        Expend expend = new Expend();
        expend.setUser(user);
        expend.setDescribe(describe);
        expend.setMoney(Double.parseDouble(money));
        expend.setDate(date);
        expend.setType(type);
        if (imageList != null) {
            expend.setImageUrl(imageList);
        }
        expend.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
               MyLog.d("sssssssss::"+objectId);

                mView.onAddSucceed();
                mView.endLoading();

            }
        });

    }


}
