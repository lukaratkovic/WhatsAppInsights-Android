package hr.tvz.android.whatsappinsights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.tvz.android.whatsappinsights.databinding.ActivityInstructionsBinding

class Instructions : AppCompatActivity() {
    private lateinit var binding: ActivityInstructionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}