package com.hurry.dxpnavigationview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by hurry on 2017/9/1.
 * 自定义底部导航栏
 */

public class CustomBottomNavigationView extends View {

    private final String TAG = "custom_view";

    /**
     * 被选中字体颜色
     */
    private int selectColor;

    /**
     * 默认字体颜色
     */
    private int defColor;

    /**
     * 图标大小
     */
    private int iconHeight;

    /**
     * 菜单图标
     * 默认图标  被选中图标
     */
    private ArrayList<Drawable> defIcons, selIcons;

    /**
     * 菜单文字
     */
    private ArrayList<String> itemTexts;

    /**
     * 单个菜单的宽度
     */
    private float itemWidth;

    private Paint mPaint;
    //文本和图标的大小边界
    private Rect textBounds, iconRect, iconRect2;

    //被选中的导航条目
    private int currentIndex = 0;

    /**
     * 菜单点击监听
     */
    public interface OnMenuClickListener {
        void onMenuClick(int position);
    }

    private OnMenuClickListener mListener;

    /**
     * 设置监听
     */
    public void setOnMenuClickListener(OnMenuClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 设置默认被选中的position
     */
    public void setCurrentIndex(int cIndex){
       this.currentIndex = cIndex;
        if(mListener!=null){
            mListener.onMenuClick(cIndex);
        }
        postInvalidate();
    }

    public CustomBottomNavigationView(Context context) {
        this(context, null);
    }

    public CustomBottomNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        defIcons = new ArrayList<>();
        selIcons = new ArrayList<>();
        itemTexts = new ArrayList<>();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomBottomNavigationView);
        int aCount = array.getIndexCount();

        float textSize = 32f;
        defColor = Color.parseColor("#999999");
        selectColor = Color.parseColor("#3399ff");
        iconHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25f, getResources().getDisplayMetrics());

        for (int i = 0; i < aCount; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.CustomBottomNavigationView_defaltColor) {//默认文本颜色
                defColor = array.getColor(attr, Color.parseColor("#999999"));

            } else if (attr == R.styleable.CustomBottomNavigationView_selectColor) {//被选中文本颜色
                selectColor = array.getColor(attr, Color.parseColor("#3399ff"));

            } else if (attr == R.styleable.CustomBottomNavigationView_iconHeight) {//图标高度
                iconHeight = array.getDimensionPixelOffset(attr,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 23f, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.CustomBottomNavigationView_textSize) {//字体大小
                textSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 12f, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.CustomBottomNavigationView_defIcon1 || attr == R.styleable.CustomBottomNavigationView_defIcon2 || attr == R.styleable.CustomBottomNavigationView_defIcon3 || attr == R.styleable.CustomBottomNavigationView_defIcon4 || attr == R.styleable.CustomBottomNavigationView_defIcon5) {//第一个菜单图标

                //第2个菜单图标

                //第3个菜单图标

                //第4个菜单图标

                //第5个菜单图标
                defIcons.add(array.getDrawable(attr));

            } else if (attr == R.styleable.CustomBottomNavigationView_selIcon1 || attr == R.styleable.CustomBottomNavigationView_selIcon2 || attr == R.styleable.CustomBottomNavigationView_selIcon3 || attr == R.styleable.CustomBottomNavigationView_selIcon4 || attr == R.styleable.CustomBottomNavigationView_selIcon5) {//第一个菜单图标

                //第2个菜单图标

                //第3个菜单图标

                //第4个菜单图

                //第5个菜单图标
                selIcons.add(array.getDrawable(attr));

            } else if (attr == R.styleable.CustomBottomNavigationView_itemText1 || attr == R.styleable.CustomBottomNavigationView_itemText2 || attr == R.styleable.CustomBottomNavigationView_itemText3 || attr == R.styleable.CustomBottomNavigationView_itemText4 || attr == R.styleable.CustomBottomNavigationView_itemText5) {//第一个菜单的文字

                //第2个菜单的文字

                //第3个菜单的文字

                //第4个菜单的文字

                //第5个菜单的文字
                itemTexts.add(array.getString(attr));

            }
        }
        array.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(textSize);
        textBounds = new Rect();
        iconRect = new Rect();
        iconRect2 = new Rect();



    }

    public CustomBottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.w(TAG, "onDraw");
        if (itemTexts.size() == 0) {
            return;
        }
        //item的宽度
        itemWidth = (float)(getWidth() / itemTexts.size());

        float realHeight = getHeight() - getPaddingBottom() - getPaddingTop();

        //item 文字的高度 = 导航栏高度 / 2.5
        float itemTextHeight = realHeight / 2.2f;
        //item icon的高度 = 导航栏高度 - 文字高度
        float itemIconHeight = realHeight - itemTextHeight;

        for (int i = 0; i < defIcons.size(); i++) {
            //画文字
            String str = itemTexts.get(i);
            mPaint.getTextBounds(str, 0, str.length(), textBounds);
            mPaint.setColor(i == currentIndex ? selectColor : defColor);
            canvas.drawText(str, itemWidth / 2 - (float) textBounds.width() / 2f + itemWidth * i,
                    itemTextHeight / 2f + (float) textBounds.height() / 2f + itemIconHeight + getPaddingTop(), mPaint); //

            //画图标
            int iconTop = (int) (itemIconHeight / 2f - iconHeight / 2f + getPaddingTop());
            Bitmap bm = drawableToBitmap(i == currentIndex ? selIcons.get(i) : defIcons.get(i));
            iconRect.set(0, 0, bm.getWidth(), bm.getHeight());
            iconRect2.set((int) (itemWidth / 2f - iconHeight / 2f + itemWidth * i),
                    iconTop,
                    (int) (itemWidth / 2f + iconHeight / 2f + itemWidth * i),
                    iconTop + iconHeight);
                    //(int) (itemIconHeight / 2f + iconHeight * 2f / 3f));
            canvas.drawBitmap(bm, iconRect, iconRect2, mPaint);

        }


    }

    /**
     * drawable转bitmap
     */
    private static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (itemWidth == 0 || mListener == null) {
            return false;
        }
        float clickX = event.getX();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (clickX <= itemWidth) {
                if (currentIndex != 0) {
                    mListener.onMenuClick(0);
                    currentIndex = 0;
                    postInvalidate();
                }

            } else if (clickX > itemWidth && clickX <= itemWidth * 2) {
                if (currentIndex != 1) {
                    mListener.onMenuClick(1);
                    currentIndex = 1;
                    postInvalidate();
                }

            } else if (clickX > itemWidth * 2 && clickX <= itemWidth * 3) {
                if (currentIndex != 2) {
                    mListener.onMenuClick(2);
                    currentIndex = 2;
                    postInvalidate();
                }

            } else if (clickX > itemWidth * 3 && clickX <= itemWidth * 4) {
                if (currentIndex != 3) {
                    mListener.onMenuClick(3);
                    currentIndex = 3;
                    postInvalidate();
                }

            } else {
                if (currentIndex != 4) {
                    mListener.onMenuClick(4);
                    currentIndex = 4;
                    postInvalidate();
                }

            }
        }
        return true;
    }


}
