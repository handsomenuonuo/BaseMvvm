package org.lib.base.widget.segmenttextView;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**********************************
 * @Name: SegmentTextView
 * @Copyright： CreYond
 * @CreateDate： 2021/4/27 18:07
 * @author: HuangFeng
 * @Version： 1.0
 * @Describe:
 *
 **********************************/
public class SegmentTextView extends AppCompatTextView {
    Context context;

    public SegmentTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public SegmentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SegmentTextView(Context context) {
        super(context);
        this.context = context;
    }

    public void setSpecifiedTextsColor(String text, int len, int color, StringClickSpan.ClickCallBack clickCallBack) {
        if (text.length() < len) {
            len = text.length();
        }
        SpannableStringBuilder styledText = new SpannableStringBuilder(text);
        styledText.setSpan(new StringClickSpan(context, color, false, clickCallBack), 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(styledText);
    }

    public void setSpecifiedTextsColor(String text, String[] specifiedTexts, int color) {
        setSpecifiedTextsColor(text, specifiedTexts, color, false, null);
    }

    public void setSpecifiedTextsColor(String text, String[] specifiedTexts, int color, StringClickSpan.ClickCallBack clickCallBack) {
        setSpecifiedTextsColor(text, specifiedTexts, color, true, clickCallBack);
    }

    public void setSpecifiedTextsColor(String text, String[] specifiedTexts, int color, int textColor, StringClickSpan.ClickCallBack clickCallBack) {
        setTextColor(textColor);
        setSpecifiedTextsColor(text, specifiedTexts, color, false, clickCallBack);
    }

    /**
     * 设置指定文字的大小、
     * text：全部文本
     * specifiedTexts：指定的文本
     * specifiedTextSize：指定的文本大小（像素）
     */
    public void setSpecifiedTextsSize(String text, String specifiedTexts, int specifiedTextSize) {
        if (TextUtils.isEmpty(text)) {
            throw new RuntimeException("传入的 text 不能为空");
        }
        List<Integer> sTextsStartList = new ArrayList<>();

        int sTextLength = specifiedTexts.length();
        String temp = text;
        int lengthFront = 0;// 记录被找出后前面的字段的长度
        int start;
        do {
            start = temp.indexOf(specifiedTexts);

            if (start != -1) {
                start = start + lengthFront;
                sTextsStartList.add(start);
                lengthFront = start + sTextLength;
                temp = text.substring(lengthFront);
            }

        } while (start != -1);

        SpannableStringBuilder styledText = new SpannableStringBuilder(text);
        for (Integer i : sTextsStartList) {
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(specifiedTextSize);
            styledText.setSpan(sizeSpan, i, i + sTextLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //[a,b]

        }
        setText(styledText);


    }


    /**
     * 设置指定文字的颜色,指定文字并相应点击事件
     * text：全部文本
     * specifiedTexts：指定的文本
     */
    public void setSpecifiedTextsColor(String text, String[] specifiedTexts, int color, boolean is_underline, StringClickSpan.ClickCallBack clickCallBack) {
        if (TextUtils.isEmpty(text)) {
            throw new RuntimeException("传入的 text 不能为空");
        }
        List<Integer> sTextsStartList = new ArrayList<>();
        Map<Integer,String> lengthMap = new HashMap<>();

        for (String specifiedText:specifiedTexts){
            int sTextLength = specifiedText.length();
            String temp = text;
            int lengthFront = 0;// 记录被找出后前面的字段的长度
            int start;
            do {
                start = temp.indexOf(specifiedText);

                if (start != -1) {
                    start = start + lengthFront;
                    sTextsStartList.add(start);
                    lengthFront = start + sTextLength;
                    temp = text.substring(lengthFront);
                    lengthMap.put(start,specifiedText);
                }

            } while (start != -1);

            if (clickCallBack != null) {
                setMovementMethod(LinkMovementMethod.getInstance()); //不设置 不响应点击事件
                setHighlightColor(Color.TRANSPARENT);//设置点击后的颜色为透明
            }
        }
        SpannableStringBuilder styledText = new SpannableStringBuilder(text);
        for (Integer i : sTextsStartList) {
            if (is_underline) {
                styledText.setSpan(new StringClickSpan(lengthMap.get(i),context, color, clickCallBack), i, i + lengthMap.get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                styledText.setSpan(new StringClickSpan(lengthMap.get(i),context, color, false, clickCallBack), i, i + lengthMap.get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        setText(styledText);
    }


    /**
     * 设置指定文本位置图标
     * text：全部文本
     */
    public void setSpecifiedPositionIcon(String text, int position, int drawableId) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(text);
        CenteredImageSpan imageSpan = new CenteredImageSpan(context, drawableId);
        //CenteredImageSpan imageSpan = new CenteredImageSpan(context, drawableId,30,30);
        styledText.setSpan(imageSpan, position, position + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //[a,b]

        setText(styledText);


    }

    /**
     * 给文本头部设置指定图标
     *
     * @param text
     * @param drawableId
     */
    public void setTextHeadIcon(String text, int drawableId) {
        setSpecifiedPositionIcon("  " + text, 0, drawableId);

    }


    /**
     * 给文本尾部设置指定图标
     *
     * @param text
     * @param drawableId
     */
    public void setTextEndIcon(String text, int drawableId) {
        setSpecifiedPositionIcon(text + "  ", text.length() + 1, drawableId);
    }


}
