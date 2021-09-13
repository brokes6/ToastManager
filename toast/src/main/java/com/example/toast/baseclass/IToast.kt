package com.example.toast.baseclass

import android.R
import android.util.Log
import android.view.View
import android.widget.TextView

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/10
 * Mender:
 * Modify:
 * Description:
 */
interface IToast {

    fun show()

    fun cancel()

    fun setText(text: CharSequence?)

    fun setView(mLayoutId: Int)

    fun getView(): View?

    fun setDuration(duration: Int)

    fun getDuration(): Int

    /**
     * 设置重心偏移
     */
    fun setGravity(gravity: Int, xOffset: Int, yOffset: Int)

//    /**
//     * 获取显示重心
//     */
//    fun getGravity(): Int
//
//    /**
//     * 获取水平偏移
//     */
//    fun getXOffset(): Int
//
//    /**
//     * 获取垂直偏移
//     */
//    fun getYOffset(): Int

    /**
     * 设置屏幕间距
     */
    fun setMargin(horizontalMargin: Float, verticalMargin: Float)

//    /**
//     * 设置水平间距
//     */
//    fun getHorizontalMargin(): Float
//
//    /**
//     * 设置垂直间距
//     */
//    fun getVerticalMargin(): Float

    /**
     * 智能获取用于显示消息的 TextView
     */
    fun findMessageView(view: View?): TextView? {
        if (view is TextView) {
            if (view.getId() == View.NO_ID) {
                view.setId(R.id.message)
            } else require(view.getId() == R.id.message) {
                Log.e("Toast", "出错拉，TextViewID必须是android.R.id.message")
            }
            return view
        }
        if (view?.findViewById<View>(R.id.message) is TextView) {
            return view.findViewById<View>(R.id.message) as? TextView
        }
        Log.e("Toast", "出错拉，TextViewID必须是android.R.id.message")
        return null
    }
}