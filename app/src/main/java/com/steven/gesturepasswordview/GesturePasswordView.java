package com.steven.gesturepasswordview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:GesturePasswordView
 * Data：3/20/2018-2:16 PM
 *
 * @author: yanzhiwen
 */
public class GesturePasswordView extends View {
    //3x3的解锁View
    private static final int mRow = 3;
    private static final int mColumn = 3;
    //颜色
    private int mNormalColor = Color.GRAY;
    private int mPressedColor = Color.BLUE;
    private int mErrorColor = Color.RED;
    //画笔
    private Paint mNormalPaint;
    private Paint mPressPaint;
    private Paint mErrorPaint;
    private Paint mLinePaint;
    //保存Point的二位数组
    private Point[][] mPoints = new Point[3][3];
    private float mDotRadius;
    //被选中的点
    private List<Point> mSelectPoints = new ArrayList<>();
    private boolean mInitOnce;
    private boolean mIsTouchPoint;
    private boolean mIsErrorStatus;
    private GesturePasswordViewListener mGesturePasswordViewListener;

    public GesturePasswordView(Context context) {
        this(context, null);
    }

    public GesturePasswordView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GesturePasswordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        mNormalPaint = getPaint();
        mNormalPaint.setColor(mNormalColor);
        mPressPaint = getPaint();
        mPressPaint.setColor(mPressedColor);
        mErrorPaint = getPaint();
        mErrorPaint.setColor(mErrorColor);
        mLinePaint = getPaint();
        mLinePaint.setColor(mPressedColor);

    }

    /**
     * 画笔
     *
     * @return
     */
    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mDotRadius / 9);
        return paint;
    }

    /**
     * 设置监听器
     * @param gesturePasswordViewListener
     */
    public void setGesturePasswordViewListener(GesturePasswordViewListener gesturePasswordViewListener) {
        this.mGesturePasswordViewListener = gesturePasswordViewListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mInitOnce) {
            initDot();
            initPaint();
            mInitOnce = true;
        }
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                Point point = mPoints[i][j];
                if (point.stateIsNormal()) {
                    //先绘制外圆
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius, mNormalPaint);
                    //后绘制内圆
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius / 6, mNormalPaint);
                } else if (point.stateIsPress()) {
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius, mPressPaint);
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius / 6, mPressPaint);
                } else if (point.stateIsError()) {
                    //设置下线条画笔的颜色
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius, mErrorPaint);
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius / 6, mErrorPaint);
                }
            }
        }
        //绘制两个点之间的连线
        drawLineToCanvas(canvas);
    }


    private void drawLineToCanvas(Canvas canvas) {
        if (mSelectPoints.size() >= 1) {
            Point lastPoint = mSelectPoints.get(0);
            for (int i = 1; i < mSelectPoints.size(); i++) {
                drawLine(canvas, lastPoint, mSelectPoints.get(i));
                lastPoint = mSelectPoints.get(i);
            }
            //触摸的时候绘制
            if (mIsTouchPoint) {
                drawLine(canvas, lastPoint, new Point(mMovingX, mMovingY, -1));
            }
        }
    }

    /**
     * 绘制两个点之间的连线
     *
     * @param canvas
     * @param start
     * @param end
     */
    private void drawLine(Canvas canvas, Point start, Point end) {
        //两点之间的距离
        double pointDistance = MathUtil.distance(end.centerX, end.centerY, start.centerX, start.centerY);
        float dx = end.centerX - start.centerX;
        float dy = end.centerY - start.centerY;
        float rx = (float) ((dx / pointDistance) * (mDotRadius / 6));
        float ry = (float) ((dy / pointDistance) * (mDotRadius / 6));
        canvas.drawLine(start.centerX + rx, start.centerY + ry,
                end.centerX - rx, end.centerY - ry, mLinePaint);
    }

    /**
     * 计算每个点的位置，保存到mPoints二位数组中
     */
    private void initDot() {
        int width = this.getWidth();
        int height = this.getHeight();
        int offsetX = 0;
        int offsetY = 0;
        //兼容下横竖屏
        if (height > width) {
            offsetY = (height - width) / 2;
        } else {
            offsetX = (width - height) / 2;
        }
        int squareWidth = width / 3;
        //外圆的半径
        mDotRadius = width / 12;
        //计算每个点的位置，保存到mPoints二位数组中
//        mPoints[0][0] = new Point(offsetX + squareWidth / 2, offsetY + squareWidth / 2, 0);
//        mPoints[0][1] = new Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth / 2, 1);
//        mPoints[0][2] = new Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth / 2, 2);
//        mPoints[1][0] = new Point(offsetX + squareWidth / 2, offsetY + squareWidth * 3 / 2, 3);
//        mPoints[1][1] = new Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth * 3 / 2, 4);
//        mPoints[1][2] = new Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth * 3 / 2, 5);
//        mPoints[2][0] = new Point(offsetX + squareWidth / 2, offsetY + squareWidth * 5 / 2, 6);
//        mPoints[2][1] = new Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth * 5 / 2, 7);
//        mPoints[2][2] = new Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth * 5 / 2, 8);
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                mPoints[i][j] = new Point(offsetX + squareWidth * (j * 2 + 1) / 2,
                        offsetY + squareWidth * (i * 2 + 1) / 2, i * mPoints.length + j);
            }
        }
    }

    float mMovingX;
    float mMovingY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //显示错误时有个时间，是错误的状态是手指触摸是不能绘制的
        if (mIsErrorStatus) {
            return false;
        }
        mMovingX = event.getX();
        mMovingY = event.getY();
        Point point = getPressPoint();
        switch (event.getAction()) {
            //手指按下
            case MotionEvent.ACTION_DOWN:
                if (point != null) {
                    mIsTouchPoint = true;
                    mSelectPoints.add(point);
                    point.setPressState();
                }
                break;
            //手指移动
            case MotionEvent.ACTION_MOVE:
                if (point != null) {
                    if (!mSelectPoints.contains(point)) {
                        mSelectPoints.add(point);
                        point.setPressState();
                    }
                }
                break;
            //手指抬起
            case MotionEvent.ACTION_UP:
                mIsTouchPoint = false;
                if (mGesturePasswordViewListener != null) {
                    if (mSelectPoints.size() < 4) {
                        showSelectError();
                        mGesturePasswordViewListener.onError();
                    } else {
                        mGesturePasswordViewListener.onSuccess();
                        clearSelectPoints();
                    }
                }
                break;

        }
        invalidate();
        return true;
    }

    /**
     * 显示错误
     */
    private void showSelectError() {
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                Point point = mPoints[i][j];
                //把所有选中的点的状态设置为Error
                if (mSelectPoints.contains(point)) {
                    point.setErrorState();
                    mIsErrorStatus = true;
                    mLinePaint.setColor(mErrorColor);
                }
            }
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                clearSelectPoints();
                mIsErrorStatus = false;
                invalidate();
                mLinePaint.setColor(mPressedColor);
            }
        }, 1000);

    }

    /**
     * 清空所有选中的点
     */
    private void clearSelectPoints() {
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                Point point = mPoints[i][j];
                //把所有选中的点的状态设置为Normal
                if (mSelectPoints.contains(point)) {
                    point.setNormalState();
                }
            }
        }
        mSelectPoints.clear();
    }

    /**
     * 获取手指触摸的是哪个点
     *
     * @return
     */
    private Point getPressPoint() {
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                Point point = mPoints[i][j];
                if (point != null) {
                    if (MathUtil.checkInRound(point.centerX, point.centerY, mDotRadius, mMovingX, mMovingY)) {
                        return point;
                    }
                }
            }
        }
        return null;
    }
}
