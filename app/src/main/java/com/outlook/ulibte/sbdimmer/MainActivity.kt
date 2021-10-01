package com.outlook.ulibte.sbdimmer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sbDimmerPref = applicationContext.getSharedPreferences("SBDIMMER_PREF", Context.MODE_PRIVATE)
        val prefEditor = sbDimmerPref.edit()

        val etDimNumber: EditText = findViewById(R.id.etDimNumber)
        val bConfirm: Button = findViewById(R.id.bConfirm)
        val tvShowDimNumber: TextView = findViewById(R.id.tvShowDimNumber)

        tvShowDimNumber.text = sbDimmerPref.getFloat("dimAmount", 0.5f).toString()

        bConfirm.setOnClickListener {
            val dimNumber: Float? = etDimNumber.text.toString().toFloatOrNull()

            if(dimNumber != null && dimNumber <= 0.9f){
                prefEditor.putFloat("dimAmount", dimNumber)
                prefEditor.apply()
                Toast.makeText(this, "Confirmou", Toast.LENGTH_SHORT).show()
                tvShowDimNumber.text = dimNumber.toString()
                Log.i("tagBosta", sbDimmerPref.getFloat("dimAmount", 0.5f).toString() + " dimAmount after editing")
            }
        }
    }
}