package com.mfpe.vinilos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mfpe.vinilos.databinding.TrackItemBinding
import com.mfpe.vinilos.models.Track

class TrackAdapter(private val albumCover: String, private var tracks: List<Track>) : RecyclerView.Adapter<TrackAdapter.ViewHolder>()  {

    inner class ViewHolder(val binding: TrackItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackAdapter.ViewHolder {
        val bind = TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: TrackAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(tracks[position]) {
                binding.trackId.text = (position + 1).toString()
                binding.trackName.text = this.name
                Glide.with(binding.trackImage.context)
                    .load(albumCover)
                    .centerCrop()
                    .into(binding.trackImage)
            }
        }
    }

    override fun getItemCount(): Int = tracks.size

}