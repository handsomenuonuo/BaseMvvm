package org.lib.base.http

/**
 * @author: HuangFeng
 * @time: 2020/8/14 7:06 PM
 * @description: 网络请求返回基础模型
 * @since: 1.0.0
 */
abstract class HttpResponse<T>(val code: Int, val msg: String, val data: T?) {
    abstract fun isSuccess(): Boolean
}

