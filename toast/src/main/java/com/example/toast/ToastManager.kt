package com.example.toast

import android.app.Activity
import android.os.Handler
import android.util.Log
import com.example.studynode.toast.toasthandler.handler.ToastHandlerImpl
import com.example.toast.handler.ToastEqualHandler
import com.example.toast.handler.ToastOrdinaryHandler
import com.example.toast.handler.ToastSuccessHandler
import com.example.toast.model.ToastModel


/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/10
 * Mender:
 * Modify:
 * Description: Toast管理类
 */
class ToastManager {

    companion object {
        val instance: ToastManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ToastManager() }
    }

    private var TAG = "TAG-${ToastManager::class.java.simpleName}"

    private val intercept by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ToastHandlerImpl<ToastModel>() }

    private lateinit var mToastQueue: ArrayList<ToastModel>

    private lateinit var handler: Handler

    @Volatile
    private var isInit: Boolean = false

    /**
     * 初始化
     *
     */
    fun init() {
        if (!isInit) {
            intercept.add(ToastEqualHandler())
            intercept.add(ToastSuccessHandler())
            intercept.add(ToastOrdinaryHandler())
            handler = Handler(android.os.Looper.getMainLooper())
            mToastQueue = ArrayList()
            isInit = true
        }
    }

    fun toast(
        activity: Activity,
        value: String,
        toastType: Int = ToastModel.TOAST_TYPE.ITEM_NORMAL
    ) {
        synchronized(mToastQueue) {
            val data = ToastModel(
                activity,
                handler,
                value,
                toastType
            )
            var index = indexOfToastQueue(data)
            if (index != -1) {
                mToastQueue[index].text = value
                mToastQueue[index].toastType = toastType
            } else {
                mToastQueue.add(data)
                index = mToastQueue.size - 1
            }
            if (index == 0) {
                intercept.intercept(mToastQueue[0])
            }
        }
    }

    fun clearToast() {
        if (mToastQueue.isNotEmpty()) {
            mToastQueue.removeAt(0)
        }
        // 如果Toast队列不为空，则继续展示
        if (mToastQueue.isNotEmpty()) {
            intercept.intercept(mToastQueue[0])
        }
    }

    fun clearAll() {
        mToastQueue.clear()
    }

    private fun indexOfToastQueue(data: ToastModel): Int {
        for (i in 0 until mToastQueue.size) {
            if (data.text == mToastQueue[i].text && data.toastType == mToastQueue[i].toastType) {
                return i
            }
        }
        return -1
    }
}

fun Activity.toast(value: String, toastType: Int = ToastModel.TOAST_TYPE.ITEM_NORMAL) {
    ToastManager.instance.toast(this, value, toastType)
}