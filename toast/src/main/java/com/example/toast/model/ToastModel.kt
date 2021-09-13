package com.example.toast.model

import android.app.Activity
import androidx.annotation.IntDef

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/10
 * Mender:
 * Modify:
 * Description: Toast实体类
 */
data class ToastModel(
    val activity: Activity? = null,
    val handler: android.os.Handler? = null,
    var text: String = "",
    var toastType: Int,
    var isIntercept: Boolean = false
) {
    @IntDef(TOAST_TYPE.ITEM_NORMAL, TOAST_TYPE.ITEM_SUCCESS)
    @Retention(AnnotationRetention.SOURCE)
    annotation class TOAST_TYPE {
        companion object {
            const val ITEM_NORMAL = 0
            const val ITEM_SUCCESS = 1
        }
    }
}