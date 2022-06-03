package com.example.appwithcats.view.favorites.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.ItemFavoritesBinding
import com.example.appwithcats.domain.favorites.FavoritesModel
import com.example.appwithcats.view.favorites.viewmodel.ItemFavViewModel


class FavoritesAdapter(
    private val fragmentLifecycleOwner: LifecycleOwner,
    private val onNavigate: (FavoritesModel) -> Unit,

    ) :
    ListAdapter<FavoritesModel, FavoritesAdapter.FavoritesViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<FavoritesModel>() {
            override fun areItemsTheSame(
                oldItem: FavoritesModel,
                newItem: FavoritesModel
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: FavoritesModel,
                newItem: FavoritesModel
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding =
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
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

    open inner class FavoritesViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fav: FavoritesModel) {
            binding.viewModel = ItemFavViewModel(onNavigate, fav).apply {
                renderingVote(fav)
                this.deleteFavoriteLiveData.observe(fragmentLifecycleOwner) {
                    clickFavorites(fav)
                    removeItem(position)

                }
                this.vote.observe(fragmentLifecycleOwner) {
                    renderingVote(fav)
                }
            }

            binding.apply {
                Glide.with(itemView)
                    .load(fav.image.url)
                    .placeholder(R.drawable.progress_animation)
                    .into(image3)
            }
        }


        private fun clickFavorites(fav: FavoritesModel) {
            when (fav.favorites) {
                true -> {
                    binding.favorites?.setImageResource(R.drawable.favorites_star)

                }
                false -> {
                    binding.favorites?.setImageResource(R.drawable.favorites_click)
                }
            }
        }

        private fun renderingVote(fav: FavoritesModel) {
            when (fav.image.like) {
                true -> {
                    binding.dislike.setImageResource(R.drawable.dislike)
                    binding.like.setImageResource(R.drawable.like_click)
                }
                false -> {
                    binding.dislike.setImageResource(R.drawable.dislike_click)
                    binding.like.setImageResource(R.drawable.like)
                }
                else -> {
                    binding.dislike.setImageResource(R.drawable.dislike)
                    binding.like.setImageResource(R.drawable.like)
                }
            }
        }
        fun removeItem(position: Int){
            val currentList = currentList.toMutableList()
            currentList.removeAt(position)
            submitList(currentList)

        }
    }
}



