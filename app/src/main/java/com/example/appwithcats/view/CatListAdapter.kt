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
import com.example.appwithcats.domain.CatModel
import com.example.appwithcats.view.cats.viemodel.CatViewModel


class CatListAdapter(
    private val fragmentLifecycleOwner: LifecycleOwner,
    private val onNavigate: (CatModel) -> Unit,

    ) :
    PagingDataAdapter<CatModel, CatListAdapter.CatViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<CatModel>() {
            override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding =
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                this.lifecycleOwner = fragmentLifecycleOwner
            }
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class CatViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(cat: CatModel) {


            binding.viewModel = CatViewModel(onNavigate, cat).apply {

                renderingVote(cat)

                this.vote.observe(fragmentLifecycleOwner) {
                    renderingVote(cat)
                }
                this.fav.observe(fragmentLifecycleOwner) {
                    clickFavorites(cat)
                    binding.favorites.isClickable = false
                }
            }
            binding.apply {
                Glide.with(itemView)
                    .load(cat.url)
                    .placeholder(R.drawable.progress_animation)
                    .into(image)
            }
        }

        private fun clickFavorites(cat: CatModel) {
            when (cat.favorites) {
                false -> {
                    binding.favorites?.setImageResource(R.drawable.favorites_star)
                }
                true -> {
                    binding.favorites?.setImageResource(R.drawable.favorites_click)
                }
            }
        }

        private fun renderingVote(cat: CatModel) {
            when (cat.like) {
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
    }

}
