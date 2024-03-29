package org.lib.base.utils

import android.R.attr
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


/**
 * @author: HuangFeng
 * @time: 2020/11/10 6:31 PM
 * @description: 权限管理工具类封装
 * @since: 1.0.0
 */
class PermissionUtils {

    companion object {

        /**
         * 检查权限
         */
        @JvmStatic
        fun hasPermissions(context: Context, vararg perms: String): Boolean {
            return EasyPermissions.hasPermissions(context, *perms)
        }

        /**
         * 是否选择了Never Ask
         */
        @JvmStatic
        fun permissionNeverAsk(host: Activity, perm: String): Boolean {
            return EasyPermissions.permissionPermanentlyDenied(host, perm)
        }

        @JvmStatic
        fun permissionNeverAsk(host: Fragment, perm: String): Boolean {
            return EasyPermissions.permissionPermanentlyDenied(host, perm)
        }

        @JvmStatic
        fun permissionsNeverAsk(host: Activity, perms: MutableList<String>): Boolean {
            return EasyPermissions.somePermissionPermanentlyDenied(host, perms)
        }

        @JvmStatic
        fun permissionsNeverAsk(host: Fragment, perms: MutableList<String>): Boolean {
            return EasyPermissions.somePermissionPermanentlyDenied(host, perms)
        }

        @JvmStatic
        fun permissionsDenied(host: Activity, vararg perms: String): Boolean {
            return EasyPermissions.somePermissionDenied(host, *perms)
        }

        @JvmStatic
        fun permissionsDenied(host: Fragment, vararg perms: String): Boolean {
            return EasyPermissions.somePermissionDenied(host, *perms)
        }

        @SuppressLint("RestrictedApi")
        @JvmStatic
        fun applyPermissions(
                host: Activity,
                tip: String? = null, // 弹框提示
                positiveButtonText: String? = null, // 弹框确定按钮文字
                negativeButtonText: String? = null, // 弹框取消按钮文字
                theme: Int? = null,
                requestCode: Int,
                vararg perms: String
        ) {
            (tip.isNullOrBlank()).trueLet {
                val request: PermissionRequest =  PermissionRequest.Builder(host, requestCode, *perms).build()
                request.helper?.directRequestPermissions(requestCode, *perms);
            }.elseLet {
                val builder = PermissionRequest.Builder(host, requestCode, *perms)
                        .also {
                            tip?.run { it.setRationale(this) }
                            positiveButtonText?.run { it.setPositiveButtonText(this) }
                            negativeButtonText?.run { it.setNegativeButtonText(this) }
                            theme?.run { it.setTheme(this) }
                        }
                EasyPermissions.requestPermissions(builder.build())
            }

        }


        @SuppressLint("RestrictedApi")
        @JvmStatic
        fun applyPermissions(
                host: Fragment,
                tip: String? = null, // 弹框提示
                positiveButtonText: String? = null, // 弹框确定按钮文字
                negativeButtonText: String? = null, // 弹框取消按钮文字
                theme: Int? = null,
                requestCode: Int,
                vararg perms: String
        ) {
            (tip.isNullOrBlank()).trueLet {
                val request: PermissionRequest =  PermissionRequest.Builder(host, requestCode, *perms).build()
                request.helper?.directRequestPermissions(requestCode, *perms);
            }.elseLet {
                val builder = PermissionRequest.Builder(host, requestCode, *perms)
                        .also {
                            tip?.run { it.setRationale(this) }
                            positiveButtonText?.run { it.setPositiveButtonText(this) }
                            negativeButtonText?.run { it.setNegativeButtonText(this) }
                            theme?.run { it.setTheme(this) }
                        }
                EasyPermissions.requestPermissions(builder.build())
            }

        }

        fun onRequestPermissionsResult(
                requestCode: Int,
                perms: Array<out String>,
                grantResults: IntArray,
                vararg receivers: PermissionCallbacks
        ) {
            EasyPermissions.onRequestPermissionsResult(requestCode, perms, grantResults, *receivers)
        }
    }

    interface PermissionCallbacks : EasyPermissions.PermissionCallbacks
}