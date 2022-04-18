package com.example.appwithcats.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.ItemFavoritesBinding
import com.example.appwithcats.domain.FavoritesModel
import com.example.appwithcats.view.favorites.ItemFavViewModel


class FavoritesAdapter(
private val fragmentLifecycleOwner: LifecycleOwner,
private val onNavigate: (FavoritesModel.Image) -> Unit,

) :
ListAdapter<FavoritesModel, FavoritesAdapter.FavoritesViewHolder>(COMPARATOR)  {

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
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                this.lifecycleOwner = fragmentLifecycleOwner
            }
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem.image)
        }
    }

    inner class FavoritesViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(fav: FavoritesModel.Image) {

            binding.viewModel = ItemFavViewModel(fav).apply {
                this.deleteFavoriteLiveData.observe(fragmentLifecycleOwner) {
                    clickFavorites(fav)
                }
            }

//            binding.viewModel = FavoritesViewModel(fav, application = Application()).apply {
//                renderingVote(fav)
//                this.vote.observe(fragmentLifecycleOwner) {
//                    renderingVote(fav)
//                }
//            }

            binding.apply {
                Glide.with(itemView)
                    .load(fav.url)
                    .placeholder(R.drawable.progress_animation)
                    .into(image3)
            }
        }

        private fun clickFavorites(fav: FavoritesModel.Image) {
            when (fav.favorites) {
                false -> {
                    binding.favorites?.setImageResource(R.drawable.favorites_click)
                }
                true -> {
                    binding.favorites?.setImageResource(R.drawable.favorites_star)
                }
            }
        }


//        private fun renderingVote(fav: FavoritesModel.Image) {
//            when (fav.like) {
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



