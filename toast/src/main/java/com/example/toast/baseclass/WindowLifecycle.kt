package com.example.toast.baseclass

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Build
import android.os.Bundle
import com.example.toast.ToastManager
import com.example.toast.view.OrdinaryToast

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/10
 * Mender:
 * Modify:
 * Description: Toast监听Activity生命周期
 */
class WindowLifecycle : ActivityLifecycleCallbacks {

    companion object {
        val instance: WindowLifecycle by lazy { WindowLifecycle() }
    }

    private var mActivity: Activity? = null

    private var toast: OrdinaryToast? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {
        ToastManager.instance.clearAll()
        toast?.cancel()
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    /**
     * 注册
     */
    fun register(activity: Activity?, impl: OrdinaryToast) {
        toast = impl
        if (mActivity != activity) {
            mActivity = activity
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mActivity?.registerActivityLifecycleCallbacks(this)
        } else {
            mActivity?.application?.registerActivityLifecycleCallbacks(this)
        }
    }

    /**
     * 反注册
     */
    fun unregister() {
        toast = null
        mActivity = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mActivity?.unregisterActivityLifecycleCallbacks(this)
        } else {
            mActivity?.application?.unregisterActivityLifecycleCallbacks(this)
        }
    }
}