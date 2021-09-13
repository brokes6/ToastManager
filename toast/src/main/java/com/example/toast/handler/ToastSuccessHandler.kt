package com.example.toast.handler

import android.util.Log
import com.example.toast.R
import com.example.toast.handler.ToastHandler
import com.example.toast.model.ToastModel
import com.example.toast.view.OrdinaryToast

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/13
 * Mender:
 * Modify:
 * Description:
 */
class ToastSuccessHandler : ToastHandler<ToastModel> {

    private var isIntercept: Boolean = false

    override var next: ToastHandler<ToastModel>? = null

    private var data: ToastModel? = null

    private var toast: OrdinaryToast? = null

    override fun dispose(data: ToastModel) {
        isIntercept = false
        if (data.toastType == ToastModel.TOAST_TYPE.ITEM_SUCCESS) {
            if (data.activity == null || data.handler == null) {
                return
            }
            this.data = data
            data.isIntercept = true
            toast = OrdinaryToast(data.activity, data.handler)
            isIntercept = true
            data.handler.post {
                toast?.let {
                    it.setView(R.layout.toast_success_view)
                    it.setText(data.text)
                    it.show()
                }
            }
        }
        super.dispose(data)
    }

    override fun isIntercept(): Boolean {
        return isIntercept
    }
}