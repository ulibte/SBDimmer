package com.outlook.ulibte.sbdimmer.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject

class SharedPreferenceSBDimmer @Inject constructor(@ApplicationContext appContext: Context){
    private var sharedPreferenceNBDimmer: SharedPreferences =
        appContext.getSharedPreferences("SBDIMMER_PREF", Context.MODE_PRIVATE)


    private fun getValue(prop: String, def: Float): Float {
        return sharedPreferenceNBDimmer.getFloat(prop, def)
    }

    private fun setValue(key:String, value: Float){
        sharedPreferenceNBDimmer.edit().putFloat(key, value).apply()
    }

    fun getDimAmount(): Float {
        return getValue("dimAmount", 0.5f)
    }

    fun setDimAmount(value: Float){
        setValue("dimAmount", value)
    }


}