package com.lucao.theawesomemovieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lucao.theawesomemovieapp.databinding.FragmentMovieItemBinding
import com.lucao.theawesomemovieapp.domain.model.Movie

interface MovieItemListener {
    fun onItemSelected(position: Int)
}

class MyItemRecyclerViewAdapter(
    private val listener: MovieItemListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val values: MutableList<Movie> = ArrayList()

    fun updateData(hqList: List<Movie>) {
        values.clear()
        values.addAll(hqList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)

        Glide.with(holder.root).load(item.image).into(holder.image)
        holder.root.setOnClickListener { listener.onItemSelected(position) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root: View = binding.root
        val image: AppCompatImageView = binding.imageView

        fun bind(item: Movie) {
            binding.movieItem = item
            binding.executePendingBindings()
        }
    }
}
