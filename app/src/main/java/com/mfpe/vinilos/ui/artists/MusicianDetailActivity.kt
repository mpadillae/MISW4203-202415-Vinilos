package com.mfpe.vinilos.ui.artists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.mfpe.vinilos.data.model.Musician
import com.mfpe.vinilos.databinding.ActivityArtistDetailBinding
import com.mfpe.vinilos.viewmodel.ArtistListViewModel

class MusicianDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityArtistDetailBinding
    private  lateinit var musician: Musician
    private lateinit var artistViewModel: ArtistListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        artistViewModel = ViewModelProvider(this)[ArtistListViewModel::class.java]


        intent.getSerializableExtra("musician")?.let {
            val musician = it as Musician
            this.musician = musician
        }

        if (this.musician.albums == null || this.musician.albums.isEmpty()) {
            fetchMusicianDetails()
        } else {
            setupUI()
        }

    }

    private fun fetchMusicianDetails() {
        artistViewModel.fetchMusicianById(musician.id)

        // Observe selected musician
        artistViewModel.selectedMusician.observe(this) { musician ->
            if (musician != null) {
                this.musician = musician
                setupUI()
            }
        }

        // Observe network error
        artistViewModel.networkError.observe(this) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    private fun setupUI() {
        setupMusicianHeader()

        val adapter = MusicianDetailPagerActivity(this, this.musician.albums)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayoutArtistDetail, binding.viewPager) { tab, position ->
            tab.text = when (position) {
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

    private fun onNetworkError() {
        // Handle network error, such as showing a message to the user
        println("Network error occurred while fetching musician details")
    }
}