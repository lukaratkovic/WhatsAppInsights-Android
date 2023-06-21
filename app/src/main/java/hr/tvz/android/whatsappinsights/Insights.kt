package hr.tvz.android.whatsappinsights

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import hr.tvz.android.whatsappinsights.controller.IInsightController
import hr.tvz.android.whatsappinsights.controller.InsightController
import hr.tvz.android.whatsappinsights.databinding.ActivityInsightsBinding
import hr.tvz.android.whatsappinsights.fragments.InsightsSummaryFragment
import hr.tvz.android.whatsappinsights.model.MessageDatabase
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.IInsightView

class Insights : AppCompatActivity(), IInsightView {
    private lateinit var binding: ActivityInsightsBinding
    private val database by lazy { MessageDatabase.getDatabase(this) }
    private val repository by lazy { MessageRepository(database.messageDao()) }
    private lateinit var insightController: IInsightController

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
        viewPagerAdapter.addFragment(InsightsSummaryFragment(), "Summary")
        viewPagerAdapter.addFragment(InsightsSummaryFragment(), "Test")
        viewPagerAdapter.addFragment(InsightsSummaryFragment(), "Test")
        viewPager.adapter = viewPagerAdapter

        insightController = InsightController(this)
        changeTitle(intent.getStringExtra("TITLE") ?: "Unknown title")
    }

    override fun changeTitle(title: String) {
        this.title = title
    }


}