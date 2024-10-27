package com.mfpe.vinilos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mfpe.vinilos.databinding.CommentItemBinding
import com.mfpe.vinilos.data.model.Comment

class CommentAdapter(private var comments: List<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>()  {

    inner class ViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val bind = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(comments[position]) {
                binding.albumComment.text = this.description
                binding.albumRating.rating = this.rating.toFloat()
            }
        }
    }

    override fun getItemCount(): Int = comments.size

}