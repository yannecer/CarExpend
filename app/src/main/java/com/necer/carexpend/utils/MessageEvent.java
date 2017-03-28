package com.necer.carexpend.utils;

/**
 * Created by necer on 2017/2/4.
 */

public class MessageEvent {

    private boolean isEnable;
    private String action;

    public MessageEvent(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
