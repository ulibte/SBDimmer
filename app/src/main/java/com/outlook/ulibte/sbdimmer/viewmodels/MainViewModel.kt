package com.outlook.ulibte.sbdimmer.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.outlook.ulibte.sbdimmer.data.SharedPreferenceSBDimmer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MainViewModel @Inject constructor(private val sharedPreference: SharedPreferenceSBDimmer): ViewModel() {
    private val _dimAmount = MutableLiveData<Float>(sharedPreference.getDimAmount())
    val dimAmount: LiveData<Float>
        get() = _dimAmount

    fun setDimer(number: Float){
        val invertedNumber: Float = abs(number - 0.9F)
        sharedPreference.setDimAmount(invertedNumber)
        _dimAmount.value = number
    }
}