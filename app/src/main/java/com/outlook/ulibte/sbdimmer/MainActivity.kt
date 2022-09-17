package com.outlook.ulibte.sbdimmer

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.outlook.ulibte.sbdimmer.data.SharedPreferenceSBDimmer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var sbDimmerPref: SharedPreferenceSBDimmer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etDimNumber: EditText = findViewById(R.id.etDimNumber)
        val bConfirm: Button = findViewById(R.id.bConfirm)
        val tvShowDimNumber: TextView = findViewById(R.id.tvShowDimNumber)

        tvShowDimNumber.text = sbDimmerPref.getDimAmount().toString()

        bConfirm.setOnClickListener {
            val dimNumber: Float? = etDimNumber.text.toString().toFloatOrNull()

            if(dimNumber != null && dimNumber <= 0.9f){
                sbDimmerPref.setDimAmount(dimNumber)
                Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                tvShowDimNumber.text = dimNumber.toString()
                Log.i("mainTag", sbDimmerPref.getDimAmount().toString() + " dimAmount after editing")
            }
        }
    }
}
