package com.mfpe.vinilos.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mfpe.vinilos.data.model.Album
import com.mfpe.vinilos.databinding.AlbumItemBinding
import com.mfpe.vinilos.ui.albums.AlbumDetailActivity

class AlbumAdapter(private var albums: List<Album>) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>(), Filterable {

    private var filteredAlbumList: List<Album> = albums

    inner class ViewHolder(val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumAdapter.ViewHolder {
        val bind = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: AlbumAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(filteredAlbumList[position]) {
                binding.albumName.text = this.name ?: "Unknown Album"
                binding.albumAuthor.text =
                    if (!this.performers.isNullOrEmpty()) this.performers[0].name else "Unknown Author"
                Glide.with(binding.albumImage.context)
                    .load(this.cover)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .centerCrop()
                    .into(binding.albumImage)
                binding.albumCard.setOnClickListener {
                    val intent = Intent(binding.root.context, AlbumDetailActivity::class.java)
                    intent.putExtra("album", this)
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = filteredAlbumList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val filteredList = if (query.isEmpty()) {
                    albums
                } else {
                    albums.filter {
                        it.name!!.lowercase().contains(query)
                    }
                }
                val filterResults = FilterResults()
                println(filteredList)
                filterResults.values = filteredList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredAlbumList = results?.values as List<Album>
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbums(newAlbums: List<Album>) {
        albums = newAlbums.map { album ->
            album.copy(
                performers = album.performers ?: emptyList()
            )
        }
        this.filteredAlbumList = albums
        notifyDataSetChanged()
    }
}
