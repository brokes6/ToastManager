package com.example.toast.handler

import android.util.Log
import com.example.toast.R
import com.example.toast.model.ToastModel
import com.example.toast.view.OrdinaryToast

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/10
 * Mender:
 * Modify:
 * Description: Toast默认样式拦截者
 */
class ToastOrdinaryHandler : ToastHandler<ToastModel> {

    private var isIntercept: Boolean = false

    override var next: ToastHandler<ToastModel>? = null

    private var data: ToastModel? = null

    private var toast: OrdinaryToast? = null

    override fun dispose(data: ToastModel) {
        isIntercept = true
        if (data.activity == null || data.handler == null) {
            isIntercept = false
            return
        }
        this.data = data
        data.isIntercept = true
        toast = OrdinaryToast(data.activity, data.handler)
        data.handler.post {
            toast?.let {
                it.setView(R.layout.toast_custom_view)
                it.setText(data.text)
                it.show()
            }
        }
        super.dispose(data)
    }

    override fun isIntercept(): Boolean {
        return isIntercept
    }
}