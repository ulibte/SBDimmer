package com.outlook.ulibte.sbdimmer.viewmodels

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.outlook.ulibte.sbdimmer.data.MySharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreference: MySharedPreference
): ViewModel() {
    companion object{
        private const val TAG = "MyMainViewModel"
        const val ACCESSIBILITY_SERVICE_ID = "com.outlook.ulibte.sbdimmer/.data.AccessibilityServiceSBDimmer"
    }

    val maxDimmerValue: Float = 0.8F

    private val _dimAmount = MutableLiveData(invertDimValue(sharedPreference.dimAmount))
    val dimAmount: LiveData<Float>
        get() = _dimAmount

    fun setDimer(number: Float){
        val invertedNumber: Float = invertDimValue(number)
        sharedPreference.dimAmount = invertedNumber
        _dimAmount.value = number
    }

    private fun invertDimValue(number: Float) = abs(number - maxDimmerValue)

    fun accessibilityIsRunning(context: Context, id: String): Boolean{
        val accessibilityManager: AccessibilityManager = context.getSystemService(AppCompatActivity.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledAccessibility: List<AccessibilityServiceInfo> = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        if(enabledAccessibility.map { it.id }.contains(id)){
            return true
        }
        return false
    }
}