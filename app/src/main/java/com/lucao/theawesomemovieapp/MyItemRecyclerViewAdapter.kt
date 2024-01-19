package com.lucao.theawesomemovieapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lucao.theawesomemovieapp.placeholder.PlaceholderContent.PlaceholderItem
import com.lucao.theawesomemovieapp.databinding.FragmentMovieItemBinding

interface MovieItemListener {
    fun onItemSelected(position: Int)
}

class MyItemRecyclerViewAdapter(
    private val listener: MovieItemListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val values: MutableList<PlaceholderItem> = ArrayList()

    fun updateData(hqList: List<PlaceholderItem>) {
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
        holder.bindItem(item)

        holder.view.setOnClickListener {
            listener.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view = binding.root
        fun bindItem(item: PlaceholderItem) {
            binding.movieItem = item
            binding.executePendingBindings()
        }
    }
}
