package com.outlook.ulibte.sbdimmer.data

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PixelFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.outlook.ulibte.sbdimmer.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


const val TAG = "MyAccessibilityService"
@AndroidEntryPoint
class AccessibilityServiceSBDimmer: AccessibilityService() {
    @Inject lateinit var sbDimmerPref: SharedPreferenceSBDimmer

    private val windowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }

    private val inflater by lazy {
        LayoutInflater.from(this)
    }
    private lateinit var theInflatedView: View

    @SuppressLint("InflateParams")
    override fun onServiceConnected() {
        super.onServiceConnected()

        Log.i(TAG, sbDimmerPref.getDimAmount().toString() + " dimAmount on onServiceConnected")

        theInflatedView = inflater.inflate(R.layout.overlay_filter, null)
        windowManager.addView(theInflatedView, getLayoutParamsWithDimmer(sbDimmerPref.getDimAmount()))
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
            windowManager.updateViewLayout(theInflatedView, getLayoutParamsWithDimmer(sbDimmerPref.getDimAmount()))
        }
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        windowManager.removeView(theInflatedView)
        return super.onUnbind(intent)
    }

    private fun getLayoutParamsWithDimmer(dimmer: Float): WindowManager.LayoutParams{
        return WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            //format = PixelFormat.TRANSLUCENT
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE  // bitwise que adiciona os valores
            dimAmount = sbDimmerPref.getDimAmount()
        }
    }
}
