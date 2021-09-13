package com.example.toastmanager

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.toast.ToastManager
import com.example.toast.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ToastManager.instance.init()
        findViewById<TextView>(R.id.text).setOnClickListener {
            toast("测试啊")
        }
    }
}