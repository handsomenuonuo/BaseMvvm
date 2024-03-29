package org.lib.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.EventLog;
import android.widget.FrameLayout;

import org.lib.base.R;

/**
 * @author: HuangFeng
 * @time: 2020/10/19 5:10 PM
 * @description: 带阴影的控件
 * @since: 1.0.0
 */
public class ShadowLayout extends FrameLayout {
    private static final String SHADOW_COLOR = "#14000000";
    private static final int SHADOW_RADIUS = 12;
    private static final int CORNER_RADIUS = 12;
    public static final int ALL = 0x1111;

    public static final int LEFT = 0x0001;

    public static final int TOP = 0x0010;

    public static final int RIGHT = 0x0100;

    public static final int BOTTOM = 0x1000;

    private int mShadowColor; // 阴影颜色
    private float mShadowRadius; // 阴影半径
    private float mCornerRadius; // 阴影四角的弧度
    private float mDx; // 阴影的横向偏移距离
    private float mDy; // 阴影的纵向偏移距离

    /**
     * 阴影显示的边界
     */
    private int mShadowSide = ALL;

    private boolean mInvalidateShadowOnSizeChanged = true;
    private boolean mForceInvalidateShadow = false;

    public ShadowLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);
        int lPadding = (int) (mShadowRadius + Math.abs(mDx));
        int tPadding =  (int) (mShadowRadius + Math.abs(mDy));
                int rPadding = (int) (mShadowRadius + Math.abs(mDx));
        int bPadding =  (int) (mShadowRadius + Math.abs(mDy));
//        int lPadding = 0;
//        if ((mShadowSide & LEFT) == LEFT) {
//            lPadding = (int) (mShadowRadius + Math.abs(mDx));
//        }else {
//            lPadding = 0;
//        }
//        int rPadding = 0;
//        if ((mShadowSide & RIGHT) == RIGHT) {
//            rPadding = (int) (mShadowRadius + Math.abs(mDx));
//        }else {
//            rPadding = 0;
//        }
//        int bPadding = 0;
//        if ((mShadowSide & BOTTOM) == BOTTOM) {
//            bPadding = (int) (mShadowRadius + Math.abs(mDy));
//        }else {
//            bPadding = (int) (mShadowRadius + Math.abs(mDy));
//        }
//        int tPadding = 0;
//        if ((mShadowSide & TOP) == TOP) {
//            tPadding = (int) (mShadowRadius + Math.abs(mDy));
//        }else {
//            tPadding = 0;
//        }
        setPadding(lPadding, tPadding, rPadding, bPadding);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = getTypedArray(context, attrs, R.styleable.my_ShadowLayout);
        if (attr == null) {
            return;
        }
        try {
            mShadowColor = attr.getColor(R.styleable.my_ShadowLayout_sl_shadowColor, Color.parseColor(SHADOW_COLOR));
            mShadowRadius = attr.getDimension(R.styleable.my_ShadowLayout_sl_shadowRadius, CORNER_RADIUS);
            mCornerRadius = attr.getDimension(R.styleable.my_ShadowLayout_sl_cornerRadius, SHADOW_RADIUS);
            mDx = attr.getDimension(R.styleable.my_ShadowLayout_sl_dx, 0);
            mDy = attr.getDimension(R.styleable.my_ShadowLayout_sl_dy, 0);
            mShadowSide = attr.getInteger(R.styleable.my_ShadowLayout_sl_shadowSide, ALL);
        } finally {
            attr.recycle();
        }
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 0;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && (getBackground() == null || mInvalidateShadowOnSizeChanged || mForceInvalidateShadow)) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(w, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mForceInvalidateShadow) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(right - left, bottom - top);
        }
    }

    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadowRadius, mDx, mDy, mShadowColor, Color.TRANSPARENT);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }

    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                0,
                0,
                shadowWidth,
                shadowHeight);

        if ((mShadowSide & LEFT) == LEFT) {
            if (dx > 0) {
                shadowRect.left = shadowRadius-dx;
            } else if (dx < 0) {
                shadowRect.left = shadowRadius-Math.abs(dx);
            }
        }else {
            if (dx > 0) {
                shadowRect.left = shadowRadius+dx;
            } else if (dx < 0) {
                shadowRect.left = shadowRadius+Math.abs(dx);
            }
        }

        if ((mShadowSide & RIGHT) == RIGHT) {
            if (dx > 0) {
                shadowRect.right = shadowRect.right - shadowRadius - dx;
            } else if (dx < 0) {
                shadowRect.right = shadowRect.right - Math.abs(dx);
            }
        }else {
            if (dx > 0) {
                shadowRect.right = shadowWidth- shadowRadius*2-dx*2;
            } else if (dx < 0) {
                shadowRect.right = shadowWidth- shadowRadius*2- Math.abs(dx)*2;
            }
        }
        if ((mShadowSide & TOP) == TOP) {
            if (dy > 0) {
                shadowRect.top = shadowRadius - dy;
            } else if (dy < 0) {
                shadowRect.top = shadowRadius -Math.abs(dy);
            }
        }else {
            if (dy > 0) {
                shadowRect.top = shadowRadius+dy;
            } else if (dy < 0) {
                shadowRect.top = shadowRadius+Math.abs(dy);
            }
        }
//
        if ((mShadowSide & BOTTOM) == BOTTOM) {
            if (dy > 0) {
                shadowRect.bottom = shadowRect.bottom - shadowRadius - dy;
            } else if (dy < 0) {
                shadowRect.bottom = shadowRect.bottom - shadowRadius -Math.abs(dy);
            }
        }else {
            if (dy > 0) {
                shadowRect.bottom = shadowHeight - shadowRadius*2-dy*2;
            } else if (dy < 0) {
                shadowRect.bottom = shadowHeight -  shadowRadius*2- Math.abs(dy)*2;
            }
        }

        Paint shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(fillColor);
        shadowPaint.setStyle(Paint.Style.FILL);

        if (!isInEditMode()) {
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }

        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        return output;
    }

    public void setInvalidateShadowOnSizeChanged(boolean invalidateShadowOnSizeChanged) {
        mInvalidateShadowOnSizeChanged = invalidateShadowOnSizeChanged;
    }

    /**
     * 刷新阴影
     */
    public void invalidateShadow() {
        mForceInvalidateShadow = true;
        requestLayout();
        invalidate();
    }

    public void setShadowColor(int color) {
        this.mShadowColor = color;
    }

    public void setShadowRadius(int radius) {
        this.mShadowRadius = radius;
    }

    public void setCornerRadius(int radius) {
        this.mCornerRadius = radius;
    }

    public void setXOffset(int dx) {
        this.mDx = dx;
    }

    public void setYOffset(int dy) {
        this.mDy = dy;
    }
}

