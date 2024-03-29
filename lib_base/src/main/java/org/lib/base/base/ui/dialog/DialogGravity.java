package org.lib.base.base.ui.dialog;

import android.view.Gravity;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**********************************
 * @Name: DialogGravity
 * @Copyright： CreYond
 * @CreateDate： 2021/4/30 12:34
 * @author: HuangFeng
 * @Version： 1.0
 * @Describe:
 *
 **********************************/
public class DialogGravity {

    @IntDef({Gravity.BOTTOM,Gravity.TOP,Gravity.LEFT,Gravity.RIGHT,Gravity.CENTER_HORIZONTAL,
            Gravity.CENTER,Gravity.CENTER_VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BaseGravity{

    }
}
