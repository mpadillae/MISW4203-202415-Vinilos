package com.mfpe.vinilos.ui.artists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.mfpe.vinilos.data.model.Band
import com.mfpe.vinilos.databinding.ActivityArtistDetailBinding

class BandDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityArtistDetailBinding
    private lateinit var band: Band

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistDetailBinding.inflate(layoutInflater)

        intent.getSerializableExtra("band")?.let {
            val band = it as Band
            this.band = band
        }
        setContentView(binding.root)

        setupBandHeader()

        val adapter = BandDetailPagerAdapter(this,this.band.musicians,this.band.albums)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayoutArtistDetail, binding.viewPager){tab, position->
            tab.text = when (position){
                0 -> "Integrantes"
                1 -> "Álbumes"
                else -> null
            }
        }.attach()
    }

    private fun setupBandHeader(){
        binding.artistName.text = band.name
        binding.artistDescription.text = band.description

        Glide.with(this)
            .load(band.image)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.artistImage)

        binding.backButton.setOnClickListener{finish()}
    }
}