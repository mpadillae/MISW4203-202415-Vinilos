package com.mfpe.vinilos.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mfpe.vinilos.R
import com.mfpe.vinilos.data.model.CollectorAlbum
import com.mfpe.vinilos.databinding.CollectorAlbumItemBinding
import com.mfpe.vinilos.ui.albums.AlbumDetailActivity


class CollectorAlbumAdapter(private var albums: List<CollectorAlbum>) : RecyclerView.Adapter<CollectorAlbumAdapter.ViewHolder>()  {

    inner class ViewHolder(val binding: CollectorAlbumItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorAlbumAdapter.ViewHolder {
        val bind = CollectorAlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: CollectorAlbumAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(albums[position]) {
                binding.collectorAlbumName.text = this.album.name
                binding.statusText.text = this.status
                val isActive = this.status.lowercase() == "active"
                val statusBackground = if (isActive) R.drawable.status_background_green else R.drawable.status_background_red
                val statusIcon = if (isActive) R.drawable.ic_check else R.drawable.ic_close
                binding.statusContainer.setBackgroundResource(statusBackground)
                binding.statusIcon.setImageResource(statusIcon)
                binding.collectorAlbumPrice.text = "$ ${this.price}"
                Glide.with(binding.collectorAlbumImage.context)
                    .load(this.album.cover)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .centerCrop()
                    .into(binding.collectorAlbumImage)
                binding.collectorAlbumCard.setOnClickListener {
                    val intent = Intent(binding.root.context, AlbumDetailActivity::class.java)
                    intent.putExtra("album", this.album)
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = albums.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCollectorAlbums(newAlbums: List<CollectorAlbum>) {
        albums = newAlbums
        notifyDataSetChanged()
    }
}