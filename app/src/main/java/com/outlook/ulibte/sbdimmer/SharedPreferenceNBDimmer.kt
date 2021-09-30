package com.outlook.ulibte.sbdimmer

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceNBDimmer(context: Context) {
    private var mSharedPreferenceNBDimmer: SharedPreferences =
        context.getSharedPreferences("SBDIMMER_PREF", Context.MODE_PRIVATE)

    fun getValue(prop: String, def: Float): Float {
        return mSharedPreferenceNBDimmer.getFloat(prop, def)
    }

    fun getAlpha(): Float {
        return getValue("alpha", 0.5f)
    }

    fun setValue(key:String, value: Float){
        mSharedPreferenceNBDimmer.edit().putFloat(key, value).commit()
    }

    fun setAlpha(value: Float){
        setValue("alpha", value)
    }


}