package necer.npicker;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;


/**
 * Created by zhuodao on 2016/9/12.
 */
public abstract class BasePicker {
    protected Picker picker;
    protected Context mContext;
    public BasePicker(Activity activity) {
        mContext = activity;
        View pickView = activity.getLayoutInflater().inflate(getLayoutId(), null);

        picker = new Picker(activity);
        picker.setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
        picker.setCancelable(true);//按返回键取消窗体
        picker.setContentView(pickView);




        TextView sure = (TextView) pickView.findViewById(R.id.tv_sure);
        TextView cancle = (TextView) pickView.findViewById(R.id.tv_cancle);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSure();
                picker.dismiss();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.dismiss();
            }
        });
        initView(pickView);
    }


    /**
     * 初始化控件
     */
    protected abstract void initView(View pickView);

    protected abstract int getLayoutId();

    public void show() {
        picker.show();
    }

    protected abstract void onSure();


}
