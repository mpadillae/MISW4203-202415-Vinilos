package com.mfpe.vinilos.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mfpe.vinilos.data.model.Performer
import com.mfpe.vinilos.databinding.FavoritePerformerItemBinding
import java.text.SimpleDateFormat

class CollectorFavoritePerformerAdapter(private var performers: List<Performer>) : RecyclerView.Adapter<CollectorFavoritePerformerAdapter.ViewHolder>()  {

    inner class ViewHolder(val binding: FavoritePerformerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorFavoritePerformerAdapter.ViewHolder {
        val bind = FavoritePerformerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: CollectorFavoritePerformerAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(performers[position]) {
                binding.favoritePerformerName.text = this.name
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val date = this.creationDate ?: this.birthDate
                binding.favoritePerformerDate.text = formatter.format(date)
                Glide.with(binding.performerImage.context)
                    .load(this.image)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .centerCrop()
                    .into(binding.performerImage)
            }
        }
    }

    override fun getItemCount(): Int = performers.size
}