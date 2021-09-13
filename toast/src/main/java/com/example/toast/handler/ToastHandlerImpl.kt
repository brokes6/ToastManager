package com.example.studynode.toast.toasthandler.handler

import com.example.toast.handler.ToastHandler

/**
 * Author: 付鑫博
 * Version: 1.16.0
 * Date: 2021/9/6
 * Mender:
 * Modify:
 * Description: 处理链的实现
 */
class ToastHandlerImpl<T> {

    private var _interceptFirst: ToastHandler<T>? = null

    fun add(interceptChain: ToastHandler<T>) {
        if (_interceptFirst == null) {
            _interceptFirst = interceptChain
            return
        }
        var node = _interceptFirst
        while (true) {
            if (node?.next == null) {
                node?.next = interceptChain
                break
            }
            node = node.next!!
        }
    }

    fun intercept(data: T) {
        _interceptFirst?.dispose(data)
    }
}