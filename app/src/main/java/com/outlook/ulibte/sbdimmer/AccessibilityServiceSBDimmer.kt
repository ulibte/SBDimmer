package com.outlook.ulibte.sbdimmer

import android.accessibilityservice.AccessibilityService
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout
import com.outlook.ulibte.sbdimmer.data.SharedPreferenceSBDimmer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccessibilityServiceSBDimmer: AccessibilityService() {
    @Inject lateinit var sbDimmerPref: SharedPreferenceSBDimmer

    private fun getDimAmount() = sbDimmerPref.getDimAmount()

    override fun onServiceConnected() {
        super.onServiceConnected()

        Log.i("tagBosta", getDimAmount().toString() + " dimAmount no onServiceConnected")

        val wm: WindowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val mLayout = FrameLayout(this)
        val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  // bitwise que adiciona os valores
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
            dimAmount = getDimAmount()
        }

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