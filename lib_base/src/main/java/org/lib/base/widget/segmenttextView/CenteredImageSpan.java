package org.lib.base.widget.segmenttextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

/**********************************
 * @Name: CenteredImageSpan
 * @Copyright： CreYond
 * @CreateDate： 2021/4/27 18:14
 * @author: HuangFeng
 * @Version： 1.0
 * @Describe:
 *
 **********************************/
public class CenteredImageSpan extends ImageSpan {

    private int drawableSize, drawableWidth, drawableHeight;

    public CenteredImageSpan(Context context, final int drawableRes) {
        super(context, drawableRes);
    }


    public CenteredImageSpan(Context context, final int drawableRes, int drawableSize) {
        super(context, drawableRes);
        this.drawableSize = drawableSize;
    }

    public CenteredImageSpan(Context context, final int drawableRes, int drawableWidth, int drawableHeight) {
        super(context, drawableRes);
        this.drawableWidth = drawableWidth;
        this.drawableHeight = drawableHeight;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        // image to draw
        Drawable b = getDrawable();
        //这是drawable的宽和高
        if (drawableSize != 0) {
            b.setBounds(0, 0, drawableSize, drawableSize);
        } else if (drawableWidth != 0 && drawableHeight != 0) {
            b.setBounds(0, 0, drawableWidth, drawableHeight);
        }


        // font metrics of text to be replaced
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;

        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
