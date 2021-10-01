package com.outlook.ulibte.sbdimmer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.content.SharedPreferences




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sbDimmerPref = applicationContext.getSharedPreferences("SBDIMMER_PREF", Context.MODE_PRIVATE)
        val prefEditor = sbDimmerPref.edit()

        val etAlphaNumber: EditText = findViewById(R.id.etFilterAlphaNumber)
        etAlphaNumber.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val alphaInput: Float? = s.toString().toFloatOrNull()
                if(alphaInput == null){
                    Log.i("tagBosta", "Alpha input == null")
                }else if (alphaInput <= 1.0){
                    prefEditor.putFloat("alpha", alphaInput)
                    prefEditor.apply()
                    Log.i("tagBosta", sbDimmerPref.getFloat("alpha", 0.5f).toString() + " Alpha depois de editar")
                }
            }
        })
    }
}