package com.outlook.ulibte.sbdimmer

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceSBDimmer(context: Context){
    private var sharedPreferenceNBDimmer: SharedPreferences =
        context.applicationContext.getSharedPreferences("SBDIMMER_PREF", Context.MODE_PRIVATE)

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