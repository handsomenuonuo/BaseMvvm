package org.mod.common.base

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.BarUtils
import org.lib.base.base.MyBaseConstants
import org.lib.base.base.arch.BaseBindingActivity

/**
 * @author: Albert Li
 * @contact: albertlii@163.com
 * @time: 2020/6/12 7:37 PM
 * @description: --
 * @since: 1.0.0
 */
abstract class BaseAppBindingActivity<B : ViewDataBinding> : BaseBindingActivity<B>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
        getTopPaddingView()?.setPadding(0,BarUtils.getStatusBarHeight(),0,0)
    }

    override fun showLoadingUI(isShow: Boolean) {

    }

    override fun showEmptyUI(isShow: Boolean) {

    }


    /***
     * 返回顶部空出statusbar的高度的view
     */
    abstract fun getTopPaddingView() : View?

    override fun navigate(page: Any) {
        startActivity(Intent(this, page as Class<*>))
    }

    override fun showToast(map: Map<String, *>) {
        if (map[MyBaseConstants.MY_TOAST_KEY_CONTENT_TYPE] == MyBaseConstants.MY_TOAST_CONTENT_TYPE_STR) {
            if (map[MyBaseConstants.MY_TOAST_KEY_DURATION] == null) {
                Toast.makeText(
                    this,
                    map[MyBaseConstants.MY_TOAST_KEY_CONTENT] as String,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    map[MyBaseConstants.MY_TOAST_KEY_CONTENT] as String,
                    map[MyBaseConstants.MY_TOAST_KEY_DURATION] as Int
                ).show()
            }
        } else if (map[MyBaseConstants.MY_TOAST_KEY_CONTENT_TYPE] == MyBaseConstants.MY_TOAST_CONTENT_TYPE_RESID) {
            if (map[MyBaseConstants.MY_TOAST_KEY_DURATION] == null) {
                Toast.makeText(
                    this,
                    map[MyBaseConstants.MY_TOAST_KEY_CONTENT] as Int,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    map[MyBaseConstants.MY_TOAST_KEY_CONTENT] as Int,
                    map[MyBaseConstants.MY_TOAST_KEY_DURATION] as Int
                ).show()
            }
        }
    }

    protected fun showToast(str: String) {
        showToast(str, null)
    }

    protected fun showToast(str: String, duration: Int?) {
        val map = HashMap<String, Any>().apply {
            put(
                MyBaseConstants.MY_TOAST_KEY_CONTENT_TYPE,
                MyBaseConstants.MY_TOAST_CONTENT_TYPE_STR
            )
            put(MyBaseConstants.MY_TOAST_KEY_CONTENT, str)
            if (duration != null) {
                put(MyBaseConstants.MY_TOAST_KEY_DURATION, duration)
            }
        }
        showToast(map)
    }

    protected fun showToast(@StringRes resId: Int) {
        showToast(resId, null)
    }

    protected fun showToast(@StringRes resId: Int, duration: Int?) {
        val map = HashMap<String, Any>().apply {
            put(
                MyBaseConstants.MY_TOAST_KEY_CONTENT_TYPE,
                MyBaseConstants.MY_TOAST_CONTENT_TYPE_RESID
            )
            put(MyBaseConstants.MY_TOAST_KEY_CONTENT, resId)
            if (duration != null) {
                put(MyBaseConstants.MY_TOAST_KEY_DURATION, duration)
            }
        }
        showToast(map)
    }

    override fun backPress(arg: Any?) {
        onBackPressed()
    }

    override fun finishPage(arg: Any?) {
        finish()
    }
}