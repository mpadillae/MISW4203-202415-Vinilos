package com.mfpe.vinilos.ui.collectors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.mfpe.vinilos.R
import com.mfpe.vinilos.adapters.CollectorDetailPagerAdapter
import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.databinding.ActivityCollectorDetailBinding

class CollectorDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollectorDetailBinding
    private lateinit var collector: Collector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectorDetailBinding.inflate(layoutInflater)
        intent.getSerializableExtra("collector")?.let {
            val collector = it as Collector
            this.collector = collector
        }
        setContentView(binding.root)

        setupCollectorHeader()

        val adapter = CollectorDetailPagerAdapter(this, this.collector)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tabFavoritePerformers)
                1 -> getString(R.string.tabAlbums)
                2 -> getString(R.string.tabComments)
                else -> null
            }
        }.attach()
    }

    private fun setupCollectorHeader() {
        binding.collectorName.text = collector.name
        binding.collectorEmail.text = collector.email
        binding.collectorTelephone.text = collector.telephone

        binding.backButton.setOnClickListener { finish() }

    }
}