package com.mfpe.vinilos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mfpe.vinilos.data.model.Collector
import com.mfpe.vinilos.databinding.CollectorItemBinding

class CollectorAdapter(private var collectors: List<Collector>) : RecyclerView.Adapter<CollectorAdapter.ViewHolder>(), Filterable {
    private var filteredCollectorList: List<Collector> = collectors

    inner class ViewHolder(val binding: CollectorItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorAdapter.ViewHolder {
        val bind = CollectorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CollectorAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(filteredCollectorList[position]) {
                binding.collectorName.text = this.name
                binding.collectorEmail.text = this.email
                val names = this.name.split(" ")
                val initials = names[0].first().uppercase() + names[names.lastIndex].first().uppercase()
                binding.collectorInitials.text = initials
            }
        }
    }

    override fun getItemCount(): Int = filteredCollectorList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val filteredList = if (query.isEmpty()) {
                    collectors
                } else {
                    collectors.filter {
                        it.name.lowercase().contains(query)
                    }
                }
                val filterResults = FilterResults()
                println(filteredList)
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCollectorList = results?.values as List<Collector>
                notifyDataSetChanged()
            }
        }
    }

    fun updateCollectors(newCollectors: List<Collector>) {
        collectors = newCollectors
        this.filteredCollectorList = collectors
        notifyDataSetChanged()
    }
}