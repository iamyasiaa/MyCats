package com.example.appwithcats.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
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
    androidx.recyclerview.widget.ListAdapter<CatModel, CatListAdapter.CatViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding =
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                this.lifecycleOwner = fragmentLifecycleOwner
            }
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CatViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(cat: CatModel) {

            binding.viewModel = CatViewModel(onNavigate, cat).apply {
                renderingVote(cat)
                this.vote.observe(fragmentLifecycleOwner) {
                    renderingVote(cat)
                }
            }
            binding.apply {
                Glide.with(itemView)
                    .load(cat.url)
                    .placeholder(R.drawable.progress_animation)
                    .into(image)
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

    class CatDiffCallback : DiffUtil.ItemCallback<CatModel>() {
        override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel) =
            oldItem == newItem
    }
}
