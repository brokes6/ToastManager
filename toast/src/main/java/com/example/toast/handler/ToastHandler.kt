package com.example.toast.handler

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/10
 * Mender:
 * Modify:
 * Description: Toast处理链
 */
interface ToastHandler<T> {

    var next: ToastHandler<T>?

    fun dispose(data: T) {
        if (!isIntercept()) {
            next?.dispose(data)
        }
    }

    fun isIntercept(): Boolean

}