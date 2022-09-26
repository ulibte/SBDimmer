package com.outlook.ulibte.sbdimmer

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import com.outlook.ulibte.sbdimmer.data.SharedPreferenceSBDimmer
import com.outlook.ulibte.sbdimmer.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var sbDimmerPref: SharedPreferenceSBDimmer
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvShowDimNumber: TextView = findViewById(R.id.tvShowDimNumber)
        val dimmerSlider: Slider = findViewById(R.id.dimmerSlider)

        viewModel.dimAmount.observe(this){
            val value: Float = it * 111
            if(value > 99.88F){
                tvShowDimNumber.text = "100%"
            }else{
                tvShowDimNumber.text = "${value.toInt()}%"
            }
        }

        dimmerSlider.addOnChangeListener { _, value, _ ->
            viewModel.setDimer(value)
        }

    }
}
