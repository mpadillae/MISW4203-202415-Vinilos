package com.mfpe.vinilos.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mfpe.vinilos.data.model.Band
import com.mfpe.vinilos.databinding.ArtistItemBinding

class BandAdapter(private var bands: List<Band>) : RecyclerView.Adapter<BandAdapter.ViewHolder>(),Filterable {

    inner class ViewHolder(val binding: ArtistItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandAdapter.ViewHolder {
        val bind = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun getItemCount(): Int = bands.size

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BandAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(bands[position]){
                binding.artistName.text = this.name
                com.bumptech.glide.Glide.with(binding.artistImage.context)
                    .load(this.image)
                    .centerCrop()
                    .into(binding.artistImage)
        }}
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateBands(newBands: List<Band>) {
        bands = newBands
        notifyDataSetChanged()
    }



}