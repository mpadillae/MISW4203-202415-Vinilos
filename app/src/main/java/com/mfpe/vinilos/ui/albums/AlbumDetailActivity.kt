package com.mfpe.vinilos.ui.albums

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.mfpe.vinilos.adapters.AlbumDetailPagerAdapter
import com.mfpe.vinilos.databinding.ActivityAlbumDetailBinding
import com.mfpe.vinilos.data.model.Album

class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumDetailBinding
    private lateinit var album: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        intent.getSerializableExtra("album")?.let {
            val album = it as Album
            this.album = album
        }
        setContentView(binding.root)

        setupAlbumHeader()

        val adapter = AlbumDetailPagerAdapter(this, this.album)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Información"
                1 -> "Canciones"
                2 -> "Comentarios"
                else -> null
            }
        }.attach()

    }

    private fun setupAlbumHeader() {
        binding.albumName.text = album.name
        binding.artistName.text = album.performers?.getOrNull(0)?.name ?: "Autor desconocido"
        Glide.with(this)
            .load(album.cover)
            .into(binding.albumImageView)

        binding.backButton.setOnClickListener { finish() }
    }

}