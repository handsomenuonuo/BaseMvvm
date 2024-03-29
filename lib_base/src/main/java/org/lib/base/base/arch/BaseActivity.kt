package org.lib.base.base.arch

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import org.lib.base.utils.PermissionUtils

/**
 * @author: HuangFeng
 * @time: 2020/6/20 12:08 PM
 * @description: Activity的封装
 * @since: 1.0.0
 */
abstract class BaseActivity : AppCompatActivity(), PermissionUtils.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initialize(savedInstanceState)
    }

    protected open fun initContentView() {
        setContentView(getLayoutId())
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int


    /**
     *  初始化操作
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)


    /**============================================================
     *  权限相关
     **===========================================================*/

    /**
     * 检查是否有权限
     */
    fun hasPermissions(vararg perms: String): Boolean {
        return PermissionUtils.hasPermissions(this, *perms)
    }

    /**
     * 申请权限
     */
    fun applyPermissions(
        tip: String? = null, // 弹框提示
        positiveButtonText: String? = null, // 弹框确定按钮文字
        negativeButtonText: String? = null, // 弹框取消按钮文字
        theme: Int? = null,
        requestCode: Int,
        vararg perms: String
    ) {

        PermissionUtils.applyPermissions(
            this,
            tip,
            positiveButtonText,
            negativeButtonText,
            theme,
            requestCode,
            *perms
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    /**
     * 申请权限失败
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (!perms.isNullOrEmpty()) {
            val refusedPerms = ArrayList<String>()
            val neverAskPerms = ArrayList<String>()
            for (item in perms) {
                if (PermissionUtils.permissionNeverAsk(this, item)) {
                    neverAskPerms.add(item)
                } else {
                    refusedPerms.add(item)
                }
            }
            if(refusedPerms.size > 0){
                onPermissionRefused(requestCode, refusedPerms)
            }
            if(neverAskPerms.size > 0){
                onPermissionNeverAsk(requestCode, neverAskPerms)
            }
        }
    }

    /**
     * 申请权限成功
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    open fun onPermissionRefused(requestCode: Int, perms: MutableList<String>) {

    }

    open fun onPermissionNeverAsk(requestCode: Int, perms: MutableList<String>) {

    }
}