package necer.npicker.widget.adapters;


import necer.npicker.widget.WheelView;

/**
 * Created by necer on 2016/9/13.
 * 停止滑动后监听
 */
public interface OnWheelSelectListener {
    /**
     * 选择
     * @param wheel
     * @param index
     */
    void onItemSeclet(WheelView wheel, int index);
}
