package hr.tvz.android.whatsappinsights

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import hr.tvz.android.whatsappinsights.databinding.ActivityInsightsBinding
import hr.tvz.android.whatsappinsights.fragments.InsightsBreakdownFragment
import hr.tvz.android.whatsappinsights.fragments.InsightsRecordsFragment
import hr.tvz.android.whatsappinsights.fragments.InsightsSummaryFragment
import hr.tvz.android.whatsappinsights.view.IInsightView

class Insights : AppCompatActivity(), IInsightView {
    private lateinit var binding: ActivityInsightsBinding

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        tabLayout.setupWithViewPager(viewPager)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(InsightsSummaryFragment(),
            "Summary")
        viewPagerAdapter.addFragment(InsightsBreakdownFragment(),
            "Breakdown")
        viewPagerAdapter.addFragment(InsightsRecordsFragment(),
            "Records")
        viewPager.adapter = viewPagerAdapter

        changeTitle(intent.getStringExtra("TITLE") ?: "Unknown title")
    }

    override fun changeTitle(title: String) {
        this.title = title
    }


}