package com.mfpe.vinilos.ui.artists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.mfpe.vinilos.data.model.Musician
import com.mfpe.vinilos.databinding.ActivityArtistDetailBinding

class MusicianDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityArtistDetailBinding
    private  lateinit var musician: Musician

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistDetailBinding.inflate(layoutInflater)

        intent.getSerializableExtra("musician")?.let {
            val musician = it as Musician
            this.musician = musician
        }
        setContentView(binding.root)

        setupMusicianHeader()

        val adapter = MusicianDetailPagerActivity(this,this.musician.albums)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayoutArtistDetail, binding.viewPager){tab, position->
            tab.text = when (position){
                0 -> "Álbumes"
                else -> null
            }
        }.attach()
    }


    private fun setupMusicianHeader(){
        binding.artistName.text = musician.name
        binding.artistDescription.text = musician.description

        Glide.with(this)
            .load(musician.image)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.artistImage)

        binding.backButton.setOnClickListener{finish()}
    }
}