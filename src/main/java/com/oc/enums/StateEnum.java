package com.oc.enums;

/**
 * @author wyj
 * @date 2018/12/4
 */
public enum StateEnum {
    /**
     * 待办事件两种状态
     * pending/resolved（未解决/已解决）
     */
    PENDING("pending"),
    RESOLVED("resolved");

    public String getStateName() {
        return stateName;
    }

    private String stateName;

    StateEnum(String stateName) {
        this.stateName = stateName;
    }
}
