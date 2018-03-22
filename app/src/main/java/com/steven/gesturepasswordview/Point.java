package com.steven.gesturepasswordview;

/**
 * Description:
 * Data：3/20/2018-11:21 AM
 *
 * @author: yanzhiwen
 */
public class Point {
    //点的圆心的x，y的位置
    public float centerX;
    public float centerY;
    //每个点的索引
    private int index;
    //正常的状态
    private int normalState = 0;
    //按下的状态
    private int pressState = 1;
    //错误的状态
    private int errorState = 2;
    private int state = normalState;

    public Point(float centerX, float centerY, int index) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.index = index;
    }

    public void setErrorState() {
        this.state = errorState;
    }

    public void setPressState() {
        this.state = pressState;
    }

    public void setNormalState() {
        this.state = normalState;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean stateIsPress() {
        return state == pressState;
    }

    public boolean stateIsNormal() {
        return state == normalState;
    }

    public boolean stateIsError() {
        return state == errorState;
    }
}


