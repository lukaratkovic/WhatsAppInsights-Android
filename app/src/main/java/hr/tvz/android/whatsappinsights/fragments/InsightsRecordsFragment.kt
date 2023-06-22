package hr.tvz.android.whatsappinsights.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.tvz.android.whatsappinsights.R
import hr.tvz.android.whatsappinsights.databinding.FragmentInsightsRecordsBinding

class InsightsRecordsFragment : Fragment() {
    private lateinit var binding: FragmentInsightsRecordsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsightsRecordsBinding.inflate(inflater, container, false)

        return binding.root
    }
}