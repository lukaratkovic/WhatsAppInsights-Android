package hr.tvz.android.whatsappinsights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.tvz.android.whatsappinsights.databinding.ActivityInsightsBinding

class Insights : AppCompatActivity() {
    private lateinit var binding: ActivityInsightsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}