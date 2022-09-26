package com.outlook.ulibte.sbdimmer.viewmodels

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

    val maxDimmerValue: Float = 0.8F

    private val _dimAmount = MutableLiveData(invertDimValue(sharedPreference.dimAmount))
    val dimAmount: LiveData<Float>
        get() = _dimAmount

    val dimStatus: Boolean
        get() = sharedPreference.runningStatus

    fun setDimer(number: Float){
        val invertedNumber: Float = invertDimValue(number)
        sharedPreference.dimAmount = invertedNumber
        _dimAmount.value = number
    }

    private fun invertDimValue(number: Float) = abs(number - maxDimmerValue)

}