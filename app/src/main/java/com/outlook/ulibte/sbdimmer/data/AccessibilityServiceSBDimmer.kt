package com.outlook.ulibte.sbdimmer.data

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.outlook.ulibte.sbdimmer.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val TAG = "MyAccessibilityService"
@AndroidEntryPoint
class AccessibilityServiceSBDimmer: AccessibilityService() {
    @Inject lateinit var mySharedPref: MySharedPreference

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

        Log.i(TAG, mySharedPref.dimAmount.toString() + " dimAmount on onServiceConnected")

        theInflatedView = inflater.inflate(R.layout.overlay_filter, null)
        windowManager.addView(theInflatedView, getLayoutParamsWithDimmer(mySharedPref.dimAmount))

        mySharedPref.runningStatus = true
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
            windowManager.updateViewLayout(theInflatedView, getLayoutParamsWithDimmer(mySharedPref.dimAmount))
        }
    }

    override fun onInterrupt() {}

    override fun onDestroy() {
        windowManager.removeView(theInflatedView)
        mySharedPref.runningStatus = false
        super.onDestroy()
    }

    private fun getLayoutParamsWithDimmer(dimmer: Float): WindowManager.LayoutParams{
        return WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            //format = PixelFormat.TRANSLUCENT
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE  // bitwise que adiciona os valores
            dimAmount = dimmer
        }
    }
}
