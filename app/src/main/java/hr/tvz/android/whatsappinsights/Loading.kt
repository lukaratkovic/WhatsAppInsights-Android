package hr.tvz.android.whatsappinsights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.tvz.android.whatsappinsights.databinding.ActivityLoadingBinding

class Loading : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}