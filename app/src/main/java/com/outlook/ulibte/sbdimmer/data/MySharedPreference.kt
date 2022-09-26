package com.outlook.ulibte.sbdimmer.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject

const val DIM_AMOUNT = "DIM_AMOUNT"
const val RUNNING_STATUS = "RUNNING_STATUS"

class MySharedPreference @Inject constructor(@ApplicationContext appContext: Context){
    private var mySharedPreference: SharedPreferences =
        appContext.getSharedPreferences("SBDIMMER_PREF", Context.MODE_PRIVATE)

    var dimAmount: Float
        get() = mySharedPreference.getFloat(DIM_AMOUNT, 0.4f)
        set(value) = mySharedPreference.edit().putFloat(DIM_AMOUNT, value).apply()

    var runningStatus: Boolean
        get() = mySharedPreference.getBoolean(RUNNING_STATUS, false)
        set(value) = mySharedPreference.edit().putBoolean(RUNNING_STATUS, value).apply()

}