package com.example.toast.view

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.example.toast.baseclass.WindowLifecycle
import com.example.toast.ToastManager
import com.example.toast.baseclass.IToast
import com.example.toast.constants.ToastConstants

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/10
 * Mender:
 * Modify:
 * Description: Toast默认样式
 */
class OrdinaryToast(var activity: Activity?, val handler: Handler?) : IToast {

    private var mView: View? = null

    /** Toast 消息 View  */
    private var mMessageView: TextView? = null

    /** Toast 显示重心  */
    private var mGravity = 0

    /** Toast 显示时长  */
    private var mDuration = 0

    /** 水平偏移  */
    private var mXOffset = 0

    /** 垂直偏移  */
    private var mYOffset = 0

    /** 水平间距  */
    private var mHorizontalMargin = 0f

    /** 垂直间距  */
    private var mVerticalMargin = 0f

    private var mWindowLifecycle: WindowLifecycle? = null

    private var windowManager : WindowManager? = null

    init {
        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowLifecycle = WindowLifecycle.instance
    }

    override fun show() {
        handler?.removeCallbacks(mShowRunnable)
        handler?.post(mShowRunnable)
    }

    /**
     * 展示任务
     */
    private val mShowRunnable = Runnable {
        if (activity == null || activity?.isFinishing == true) {
            return@Runnable
        }

        val params = WindowManager.LayoutParams()
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.format = PixelFormat.TRANSLUCENT
        params.windowAnimations = R.style.Animation_Toast
        params.flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        params.packageName = activity?.packageName
        params.gravity = mGravity
        params.x = mXOffset
        params.y = mYOffset
        params.verticalMargin = mVerticalMargin
        params.horizontalMargin = mHorizontalMargin

        try {
            windowManager?.addView(mView, params)
            // 添加一个移除吐司的任务
            handler?.postDelayed(
                {
                    cancel()
                },
                (if (mDuration == Toast.LENGTH_LONG) ToastConstants.LONG_DURATION_TIMEOUT else ToastConstants.SHORT_DURATION_TIMEOUT).toLong()
            )

            mWindowLifecycle?.register(activity, this)

        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
    }

    override fun cancel() {
        handler?.removeCallbacks(mCancelRunnable)
        handler?.post(mCancelRunnable)
    }

    /**
     * 隐藏任务
     */
    private val mCancelRunnable = Runnable {
        if (activity == null) {
            return@Runnable
        }

        try {
            windowManager?.removeViewImmediate(mView)
            activity = null
            mView = null
            mMessageView = null
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } finally {
            ToastManager.instance.clearToast()
            mWindowLifecycle?.unregister()
        }
    }

    override fun setText(text: CharSequence?) {
        if (mMessageView == null || text?.isEmpty() == true) {
            return
        }
        mMessageView?.text = text
    }

    override fun setView(mLayoutId: Int) {
        mView = LayoutInflater.from(activity).inflate(mLayoutId, null)
        if (mView == null) {
            mMessageView = null
            return
        }
        mMessageView = findMessageView(mView)
    }

    override fun getView(): View? {
        return mView
    }

    override fun setDuration(duration: Int) {
        mDuration = duration
    }

    override fun getDuration(): Int {
        return mDuration
    }

    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) {
        mGravity = gravity
        mXOffset = xOffset
        mYOffset = yOffset
    }

    override fun setMargin(horizontalMargin: Float, verticalMargin: Float) {
        mHorizontalMargin = horizontalMargin
        mVerticalMargin = verticalMargin
    }
}