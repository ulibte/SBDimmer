package com.outlook.ulibte.sbdimmer

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider
import com.outlook.ulibte.sbdimmer.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val tvShowDimNumber: TextView by lazy {
        findViewById(R.id.tvShowDimNumber)
    }
    private val dimmerSlider: Slider by lazy {
        findViewById(R.id.dimmerSlider)
    }
    private val buttonOn: Button by lazy {
        findViewById(R.id.buttonOn)
    }
    private val buttonOff: Button by lazy {
        findViewById(R.id.buttonOff)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonOn.setOnClickListener { goToAccessibilitySettings() }
        buttonOff.setOnClickListener { goToAccessibilitySettings() }

        viewModel.dimAmount.observe(this){
            // max_dimmer is 0.8 and to make it pretty I convert to 100
            val value: Float = it * 125 // 0.8 * 125 == 100%
            tvShowDimNumber.text = "${value.toInt()}%"
        }

        dimmerSlider.valueTo = viewModel.maxDimmerValue
        dimmerSlider.value = viewModel.dimAmount.value ?: (viewModel.maxDimmerValue / 2F)
        dimmerSlider.addOnChangeListener { _, value, _ ->
            viewModel.setDimer(value)
        }
    }

    override fun onResume() {
        super.onResume()
        setButtonsStatus(viewModel.dimStatus, buttonOn, buttonOff)
    }

    private fun goToAccessibilitySettings(){
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    private fun setButtonsStatus(status: Boolean,
                                 buttonOn: Button,
                                 buttonOff: Button){
        when(status){
            true -> {
                buttonOn.isEnabled = false
                buttonOn.backgroundTintList = ContextCompat.getColorStateList(this, R.color.grey)
                buttonOff.isEnabled = true
                buttonOff.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red)
            }
            else -> {
                buttonOn.isEnabled = true
                buttonOn.backgroundTintList = ContextCompat.getColorStateList(this, R.color.green)
                buttonOff.isEnabled = false
                buttonOff.backgroundTintList = ContextCompat.getColorStateList(this, R.color.grey)
            }
        }
    }
}
