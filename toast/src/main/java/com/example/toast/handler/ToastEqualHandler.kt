package com.example.toast.handler

import com.example.toast.model.ToastModel

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/10
 * Mender:
 * Modify:
 * Description: Toast默认拦截者
 */
class ToastEqualHandler : ToastHandler<ToastModel> {

    private var isIntercept: Boolean = false

    override var next: ToastHandler<ToastModel>? = null

    override fun dispose(data: ToastModel) {
        isIntercept = data.isIntercept
        super.dispose(data)
    }

    override fun isIntercept(): Boolean {
        return isIntercept
    }

}