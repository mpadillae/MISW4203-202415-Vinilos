package com.mfpe.vinilos.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mfpe.vinilos.data.model.Band
import com.mfpe.vinilos.data.model.Musician
import com.mfpe.vinilos.data.model.Performer
import com.mfpe.vinilos.databinding.FavoritePerformerItemBinding
import com.mfpe.vinilos.ui.artists.BandDetailActivity
import com.mfpe.vinilos.ui.artists.MusicianDetailActivity
import java.text.SimpleDateFormat
import java.util.Date

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
                val date: Date?
                var isBand = false
                if (creationDate != null) {
                    date = creationDate
                    isBand = true
                } else {
                    date = birthDate
                }
                binding.favoritePerformerDate.text = date.let { formatter.format(it) }
                Glide.with(binding.performerImage.context)
                    .load(this.image)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .centerCrop()
                    .into(binding.performerImage)

                setupClickListener(binding, this, date, isBand)
            }
        }
    }

    private fun setupClickListener(binding: FavoritePerformerItemBinding, performer: Performer, date: Date, isBand: Boolean) {
        val band = Band(performer.id, performer.name, performer.image, performer.description, emptyList(), emptyList())
        val musician = Musician(performer.id, performer.name, performer.image, performer.description, date, band, emptyList())
        val extraName = if (isBand) "band" else "musician"
        val extra = if (isBand) band else musician
        val intentActivity = if (isBand) BandDetailActivity::class.java else MusicianDetailActivity::class.java
        binding.favoritePerformerCard.setOnClickListener {
            val intent = Intent(binding.root.context, intentActivity)
            intent.putExtra(extraName, extra)
            binding.root.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = performers.size
}