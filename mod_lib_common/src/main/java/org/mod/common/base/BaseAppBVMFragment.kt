package org.mod.common.base

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import org.lib.base.base.MyBaseConstants
import org.lib.base.base.arch.BaseBVMActivity
import org.lib.base.base.arch.BaseBVMFragment
import org.lib.base.base.arch.BaseViewModel

/**********************************
 * @Name:         BaseAppBVMFragment
 * @Copyright：  CreYond
 * @CreateDate： 2021/5/7 15:34
 * @author:      HuangFeng
 * @Version：    1.0
 * @Describe:
 *
 **********************************/
abstract class BaseAppBVMFragment <B : ViewDataBinding, VM : BaseViewModel> :
    BaseBVMFragment<B, VM>(){

    override fun showLoadingUI(isShow: Boolean) {
    }

    override fun showEmptyUI(isShow: Boolean) {
    }

    override fun showToast(map: Map<String, *>) {
        if (map[MyBaseConstants.MY_TOAST_KEY_CONTENT_TYPE] == MyBaseConstants.MY_TOAST_CONTENT_TYPE_STR) {
            if (map[MyBaseConstants.MY_TOAST_KEY_DURATION] == null) {
                ToastUtils.showShort(
                    map[MyBaseConstants.MY_TOAST_KEY_CONTENT] as String)
            } else {
                Toast.makeText(
                    activity,
                    map[MyBaseConstants.MY_TOAST_KEY_CONTENT] as String,
                    map[MyBaseConstants.MY_TOAST_KEY_DURATION] as Int
                ).show()
            }
        } else if (map[MyBaseConstants.MY_TOAST_KEY_CONTENT_TYPE] == MyBaseConstants.MY_TOAST_CONTENT_TYPE_RESID) {
            if (map[MyBaseConstants.MY_TOAST_KEY_DURATION] == null) {
                ToastUtils.showShort(
                    map[MyBaseConstants.MY_TOAST_KEY_CONTENT] as Int)
            } else {
                Toast.makeText(
                    activity,
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



    override fun navigate(page: Any) {
    }

    override fun backPress(arg: Any?) {
        activity?.onBackPressed()
    }

    override fun finishPage(arg: Any?) {
        activity?.finish()
    }
}