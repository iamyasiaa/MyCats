package com.example.appwithcats.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.ItemBinding
import com.example.appwithcats.domain.FavoritesModel



class FavoritesAdapter(
private val fragmentLifecycleOwner: LifecycleOwner,
private val onNavigate: (FavoritesModel) -> Unit,

) :
PagingDataAdapter<FavoritesModel, FavoritesAdapter.FavoritesViewHolder>(COMPARATOR)  {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<FavoritesModel>() {
            override fun areItemsTheSame(oldItem: FavoritesModel, newItem: FavoritesModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FavoritesModel, newItem: FavoritesModel): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding =
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                this.lifecycleOwner = fragmentLifecycleOwner
            }
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class FavoritesViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(fav: FavoritesModel) {

//            binding.viewModel = CatViewModel(onNavigate, cat).apply {
//                renderingVote(cat)
//                this.vote.observe(fragmentLifecycleOwner) {
//                    renderingVote(cat)
//                }
//            }
            binding.apply {
                Glide.with(itemView)
                    .load(fav.url)
                    .placeholder(R.drawable.progress_animation)
                    .into(image)
            }
        }

//        private fun renderingVote(cat: CatModel) {
//            when (cat.like) {
//                true -> {
//                    binding.dislike.setImageResource(R.drawable.dislike)
//                    binding.like.setImageResource(R.drawable.like_click)
//                }
//                false -> {
//                    binding.dislike.setImageResource(R.drawable.dislike_click)
//                    binding.like.setImageResource(R.drawable.like)
//                }
//                else -> {
//                    binding.dislike.setImageResource(R.drawable.dislike)
//                    binding.like.setImageResource(R.drawable.like)
//                }
//            }
//        }
    }
}



