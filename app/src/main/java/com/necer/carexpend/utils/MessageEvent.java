package com.necer.carexpend.utils;

/**
 * Created by necer on 2017/2/4.
 */

public class MessageEvent {

    private boolean isEnable;

    public MessageEvent(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
