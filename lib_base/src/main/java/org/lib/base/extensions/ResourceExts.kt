package org.lib.base.extensions

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * @author: HuangFeng
 * @time: 2020/7/3 5:11 PM
 * @description: 资源相关
 * @since: 1.0.0
 */


/**
 * 根据资源id获取颜色
 */
@ColorInt
fun @receiver:ColorRes Int.toColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

/**
 * 根据资源id获取字符串
 */
fun @receiver:StringRes Int.toStr(context: Context): String {
    return context.getString(this)
}

/**
 * 根据资源id获取dimen
 */
fun @receiver:DimenRes Int.toDimen(context: Context): Float {
    return context.resources.getDimension(this)
}

/**
 * 根据资源id获取dimen，单位为px
 */
fun @receiver:DimenRes Int.toDimenPx(context: Context): Int {
    return context.resources.getDimensionPixelSize(this)
}
