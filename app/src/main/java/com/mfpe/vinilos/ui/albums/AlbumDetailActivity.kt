package com.mfpe.vinilos.ui.albums

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.mfpe.vinilos.R
import com.mfpe.vinilos.adapters.AlbumDetailPagerAdapter
import com.mfpe.vinilos.databinding.ActivityAlbumDetailBinding
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.viewmodel.AlbumListViewModel

class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumDetailBinding
    private lateinit var album: Album
    private lateinit var albumListViewModel: AlbumListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        albumListViewModel = ViewModelProvider(this)[AlbumListViewModel::class.java]

        intent.getSerializableExtra("album")?.let {
            val album = it as Album
            this.album = album
        }

        if(this.album.tracks == null){
            fetchAlbumDetails()
        }
        else{
            setupUI()
        }
    }

    private fun fetchAlbumDetails() {
        albumListViewModel.fetchAlbumById(album.id)

        // Observe selected Album
        albumListViewModel.selectedAlbum.observe(this) { album ->
            if (album != null) {
                this.album = album
                setupUI()
            }
        }

        // Observe network error
        albumListViewModel.networkError.observe(this) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    private fun setupUI() {
        setupAlbumHeader()


        val adapter = AlbumDetailPagerAdapter(this, this.album)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {

                0 -> getString(R.string.tabInformation)
                1 -> getString(R.string.tabSongs)
                2 -> getString(R.string.tabComments)
                else -> null
            }

        }.attach()
    }

    private fun setupAlbumHeader() {
        binding.albumName.text = album.name
        binding.artistName.text = if (album.performers.isNotEmpty()) album.performers[0].name else ""
        Glide.with(this)
            .load(album.cover)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.albumImageView)

        binding.backButton.setOnClickListener { finish() }

    }

    private fun onNetworkError() {
        // Handle network error, such as showing a message to the user
        println("Network error occurred while fetching albums details")
    }

}