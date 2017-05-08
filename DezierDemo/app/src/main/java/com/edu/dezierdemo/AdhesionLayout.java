package com.edu.dezierdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/2/7.
 */

public class AdhesionLayout extends RelativeLayout {
    /**
     * 头圆
     */
    Circle headCirle = new Circle();
    /**
     * 尾巴圆
     */
    Circle footCirle = new Circle();
    /**
     * 初始化画笔
     */
    Paint paint = new Paint();
    /**
     * 贝塞尔曲线
     */
    Path path = new Path();

    /**
     * 粘连的长度
     */
    float maxAdhesionLength = 1000;

    /**
     * 头圆的最小半径
     */
    float minHeadCircleRadius = 4;
    /**
     * 头圆的半径
     */
    float headCircleRadius ;

    /**
     * 按下的时候，记录此刻尾部圆的圆心x、y
     */
    float mDownFooterCircleX;
    float mDownFooterCircleY;


    /**
     * 是否第一次onSizeChanged
     */
    private boolean isFirst = true;
    /**
     * 是否正在进行动画中
     */
    boolean isAnim = false;
    /**
     * 本View宽度、高度
     */
    private int mWidth;
    private int mHeight;
    /**
     * 是否粘连
     */
    private  boolean isAdherent = true;
    /**
     * 记录点击时的x、y
     */
    float mDownX;
    float mDownY;
    /**
     * 是否允许可以扯断
     */
    private boolean isSnap = true;

    /**
     * 监听粘连是否断掉的监听器
     */
    private OnAdherentListener onAdherentListener;


    public AdhesionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdhesionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    public AdhesionLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        // 透明背景
        setBackgroundColor(Color.TRANSPARENT);
        paint.setColor( Color.rgb(247,82,49));
        paint.setAntiAlias(true);
    }

    /**
     * 当视图组大小发生变化   只调用一次
     * 关于onSizeChanged（）的调用问题，原则是只有视图大小改变的时候调用，但是由于是首次初始化
     * 因此，在视图初始化的时候，会调用一次，即mWidth mHeight只会赋值一次
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isFirst) {
            isFirst = false;
            mWidth = w;
            mHeight = h;
            reset();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制头圆
        canvas.drawCircle(headCirle.x,headCirle.y,headCircleRadius,paint);
        //绘制尾圆
        canvas.drawCircle(footCirle.x,footCirle.y,footCirle.radius,paint);
        if (isAdherent) {
            drawBezier(canvas);

        }


    }
    /**
     * 画贝塞尔曲线
     *
     * 1、atan : 反正切值    因为tan(atanA)=A，互为反函数 所以 atanA = 角度值
     *    double atan(double d)  返回对应角度的反正切值
     * 2、sin : 正弦值
     *    double sin(double d)  返回对应角度的正弦值
     * 3、cos : 余弦值
     *    double cos(double d)  返回对应角度的余弦值
     * @param canvas
     */
    private void drawBezier(Canvas canvas) {
        //先求三角函数
        //求出a角角度 tan为对边/邻边
        float atan = (float) Math.atan((footCirle.y-headCirle.y)/(footCirle.x-headCirle.x));
        //获取sin、cos值
        float sin = (float) Math.sin(atan);
        float cos = (float) Math.cos(atan);

        //对应两个2阶贝塞尔曲线的4个点
          /* 四个点 */
        float headerX1 = headCirle.x - headCircleRadius * sin;
        float headerY1 = headCirle.y + headCircleRadius * cos;

        float headerX2 = headCirle.x + headCircleRadius * sin;
        float headerY2 = headCirle.y - headCircleRadius * cos;

        float footerX1 = footCirle.x - footCirle.radius * sin;
        float footerY1 = footCirle.y + footCirle.radius * cos;

        float footerX2 = footCirle.x + footCirle.radius * sin;
        float footerY2 = footCirle.y - footCirle.radius * cos;

        //中点坐标
        float adhenertX = (headCirle.x +  footCirle.x)/2;
        float adhenertY = (headCirle.y + footCirle.y)/2;

        //绘制贝塞尔曲线
        path.reset();
        path.moveTo(headerX1,headerY1);
        path.quadTo(adhenertX,adhenertY,footerX1,footerY1);
        path.lineTo(footerX2,footerY2);
        path.quadTo(adhenertX,adhenertY,headerX2,headerY2);
        canvas.drawPath(path,paint);

    }

    /**
     * 重置参数
     */

    private void reset() {
        //当拖动完成后,重置本空间大小
        setWidthAndHeight(mWidth,mHeight);
        headCircleRadius = headCirle.radius = footCirle.radius = Math.min(mWidth,mHeight)/2;//圆半径取宽高的最小值
        headCirle.x = footCirle.x = mWidth / 2;  //使得头部圆置于视图组的中点
        headCirle.y = footCirle.y = mHeight / 2; //使得尾部圆置于视图的的中心
        isAnim = false;
    }

    /**
     * 设置本视图的宽高
     * @param mWidth
     * @param mHeight
     */
    private void setWidthAndHeight(int mWidth, int mHeight) {
        ViewGroup.LayoutParams latoutParams = getLayoutParams();
        latoutParams.width = mWidth;
        latoutParams.height = mHeight;
        setLayoutParams(latoutParams);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAnim){
            return true;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                /**
                 * 重新设置布局大小： 填充父布局
                 * 注意：只有在点击以及移动的时候，才会让视图暂时变为填充满父布局，
                 *      普通情况下，视图大小为默认设置的宽高，不会填充父布局，这样避免了其他控件的点击事件被屏蔽
                 */
                setWidthAndHeight(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                /**
                 * 把两个圆的坐标由原来的坐标转化为填充父布局之后的坐标
                 */
                mDownX = event.getX();
                mDownY = event.getY();
                mDownFooterCircleX = footCirle.x;
                mDownFooterCircleY = footCirle.y;

                break;
            case MotionEvent.ACTION_MOVE:
                //对滑动的坐标进行修正
                footCirle.x = mDownFooterCircleX + (event.getX() - mDownX);
                footCirle.y = mDownFooterCircleY + (event.getY() - mDownY);
                doAdhere();



                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isAnim = true;
                if (isAdherent)
                    startAnim();
                else if (onAdherentListener != null) {
                    footCirle.radius = 0;
                    onAdherentListener.onDismiss();
                }

                break;
        }
        invalidate();
        return true;

    }

    private void startAnim() {
        //属性动画x方向
        ValueAnimator xValueAnimator = ValueAnimator.ofFloat(footCirle.x, mWidth / 2);
        xValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                footCirle.x = (float) animation.getAnimatedValue();
                //动画执行的同时不断调用，不断刷新视图
                invalidate();
            }
        });
           /* y方向 */
        ValueAnimator yValueAnimator = ValueAnimator.ofFloat(footCirle.y, mHeight / 2);
        yValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //动画执行的同时不断调用，不断刷新视图
                footCirle.y = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        //动画集合
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(xValueAnimator).with(yValueAnimator);
        animSet.setInterpolator(new BounceInterpolator());
        animSet.setDuration(400);
        animSet.start();
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束的时候，重置所有参数
                reset();
            }
        });


    }

    /**
     * 处理粘连效果逻辑
     *
     * 知识补充：
     *        Math.sqrt(x)   返回x的平方根
     *        Math.pow(x,y)  返回x的y次方
     */
    private void doAdhere() {
        //计算两个圆心之间的距离
        float distance = (float) Math.sqrt(Math.pow(footCirle.x - headCirle.x, 2) + Math.pow(footCirle.y - headCirle.y, 2));
        float scale = 1 - distance / maxAdhesionLength;
        //头圆的大小随着距离而缩小,渐渐变成最小值
        headCircleRadius = Math.max(headCirle.radius * scale, minHeadCircleRadius);
        /**
         * 如果拉扯距离大于设定的最大距离并且此时设置了可以扯断
      */
        if (distance > maxAdhesionLength && isSnap) {

            isAdherent = false;
            //头圆消失
            headCircleRadius = 0;
        }
        else
            isAdherent = true;
    }

    /**
     * 圆类
     */
    private class Circle {
        public float x;
        public float y;
        public float radius;
    }


    /**
     * 对外接口：设置是否可以扯断
     */
    public void setSnapEnable(boolean isSnap) {
        this.isSnap = isSnap;
    }

    /**
     * 对外接口：设置粘连的最大长度
     *
     * @param maxAdherentLength
     */
    public void setMaxAdherentLength(int maxAdherentLength) {
        this.maxAdhesionLength = maxAdherentLength;
    }

    /**
     * 对外接口：设置头部的最小半径
     *
     * @param minHeaderCircleRadius
     */
    public void setMinHeaderCircleRadius(int minHeaderCircleRadius) {
        this.minHeadCircleRadius = minHeaderCircleRadius;
    }

    /**
     * 对外接口：设置监听器
     *
     * @param onAdherentListener
     */
    public void setOnAdherentListener(OnAdherentListener onAdherentListener) {
        this.onAdherentListener = onAdherentListener;
    }
    /**
     * 监听器
     */
    public interface OnAdherentListener {
        public void onDismiss();
    }




}
