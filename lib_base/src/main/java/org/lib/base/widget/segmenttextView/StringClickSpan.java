package org.lib.base.widget.segmenttextView;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
/**********************************
 * @Name: StringClickSpan
 * @Copyright： CreYond
 * @CreateDate： 2021/4/27 18:13
 * @author: HuangFeng
 * @Version： 1.0
 * @Describe:
 *
 **********************************/
public class StringClickSpan extends ClickableSpan {
    String string;
    Context context;
    int color;
    boolean is_underline = true;
    ClickCallBack clickCallBack;



    public StringClickSpan(Context context, int color, boolean is_underline, ClickCallBack clickCallBack) {
        this.context = context;
        this.color = color;
        this.is_underline = is_underline;
        this.clickCallBack = clickCallBack;
    }

    public StringClickSpan(String str,Context context, int color, boolean is_underline, ClickCallBack clickCallBack) {
        this.string = str;
        this.context = context;
        this.color = color;
        this.is_underline = is_underline;
        this.clickCallBack = clickCallBack;
    }

    public StringClickSpan(String str, Context context, int color, ClickCallBack clickCallBack) {
        this.string = str;
        this.context = context;
        this.color = color;
        this.clickCallBack = clickCallBack;
    }

    public StringClickSpan(Context context, int color, ClickCallBack clickCallBack) {
        this.context = context;
        this.color = color;
        this.clickCallBack = clickCallBack;
    }

    @Override
    public void onClick(View widget) {
        if (clickCallBack != null) {
            clickCallBack.onStringClick(widget,string);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        if (!is_underline) {
            ds.setUnderlineText(false);
        }
        ds.setColor(color);
    }

    public interface ClickCallBack {
        void onStringClick(View view,String s);
    }

}
