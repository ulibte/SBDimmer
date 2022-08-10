package com.outlook.ulibte.sbdimmer

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.outlook.ulibte.sbdimmer.data.SharedPreferenceSBDimmer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccessibilityServiceSBDimmer: AccessibilityService() {
    @Inject lateinit var sbDimmerPref: SharedPreferenceSBDimmer

    private val windowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }
    private val layoutParams by lazy {
        WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            //format = PixelFormat.TRANSLUCENT
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE  // bitwise que adiciona os valores
            dimAmount = getDimAmount()
        }
    }
    private val inflater by lazy {
        LayoutInflater.from(this)
    }
    private lateinit var theInflatedView: View


    private fun getDimAmount() = sbDimmerPref.getDimAmount()

    override fun onServiceConnected() {
        super.onServiceConnected()

        Log.i("tagBosta", getDimAmount().toString() + " dimAmount no onServiceConnected")

        theInflatedView = inflater.inflate(R.layout.overlay_filter, null)
        windowManager.addView(theInflatedView, layoutParams)

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i("tagEventoBosta", event.toString())
        if(event?.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED){
            Log.i("tagEventoMerda", "CLICOU NA TELA SEU BOSTA")
        }
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        windowManager.removeView(theInflatedView)
        return super.onUnbind(intent)
    }
}