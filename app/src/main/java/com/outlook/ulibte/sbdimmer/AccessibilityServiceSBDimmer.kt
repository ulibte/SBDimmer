package com.outlook.ulibte.sbdimmer

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout

class AccessibilityServiceSBDimmer: AccessibilityService() {
    private lateinit var mSharedPreferenceNBDimmer: SharedPreferences

    private fun getDimAmount(): Float {
        return mSharedPreferenceNBDimmer.getFloat("alpha", 0.5f)
    }
    //tentar contexto de outro lugar, testar da onde vem o contexto

    override fun onServiceConnected() {
        super.onServiceConnected()

        mSharedPreferenceNBDimmer = applicationContext.getSharedPreferences("SBDIMMER_PREF", Context.MODE_PRIVATE)
        Log.i("tagBosta", getDimAmount().toString() + " Alpha no onServiceConnected")
        Log.i("tagBosta", "onServiceConékitedi")

        val wm: WindowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val mLayout = FrameLayout(this)
        val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams()
        layoutParams.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // bitwise que adiciona os valores
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.TOP
        layoutParams.dimAmount = getDimAmount()
        //layoutParams.alpha = 0.5f

        val inflater: LayoutInflater = LayoutInflater.from(this)
        inflater.inflate(R.layout.overlay_filter, mLayout)
        wm.addView(mLayout, layoutParams)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i("tagBosta", event.toString())
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }
}