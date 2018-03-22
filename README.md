# GesturePasswordView  


**效果图**  
![2018-03-22_12_06_12.gif](https://upload-images.jianshu.io/upload_images/1472453-025aa25fef9c0187.gif?imageMogr2/auto-orient/strip)


**手指触摸时绘制两个点之间的连线了。如下图**

![CIJ6_4AYF8Y6YEI3PUOHS7B.png](https://upload-images.jianshu.io/upload_images/1472453-fc72b71f0f2f46dd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**两个点之间连线是内圆之外与内圆之外的连线，怎么计算这两个点的位置呢？如下图**  

![m9.png](https://upload-images.jianshu.io/upload_images/1472453-f7634b4bd92a9fdd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  

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
