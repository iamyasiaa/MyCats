package com.example.appwithcats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.databinding.ItemBinding
import com.example.appwithcats.ui.cats.CatsFragmentDirections


class CatListAdapter: androidx.recyclerview.widget.ListAdapter<CatModel, CatListAdapter.CatViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class CatViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: CatModel) {
            binding.apply {
                Glide.with(itemView).load(cat.url).into(image)
            }
            binding.image.apply {
                setOnClickListener {
                    val action = CatsFragmentDirections.actionCatsFragmentToCatFragment(urlCat = cat.url)
                    Navigation.findNavController(itemView).navigate(action)
                }
            }
        }
    }

    class CatDiffCallback : DiffUtil.ItemCallback<CatModel>() {
        override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel) =
            oldItem == newItem
    }
}
