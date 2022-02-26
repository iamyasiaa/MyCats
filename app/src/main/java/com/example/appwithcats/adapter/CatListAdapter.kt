package com.example.appwithcats.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.ItemBinding
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.ui.cats.CatViewModel

class CatListAdapter(
    private val fragmentLifecycleOwner: LifecycleOwner,
    private val onNavigate: (CatModel) -> Unit
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
                this.vote.observe(fragmentLifecycleOwner) {
                    with(binding) {
                        it?.let {
                            if (cat.like == true) {
                                dislike.setImageResource(R.drawable.dislike)
                                like.setImageResource(R.drawable.like_click)
                            }
                            if(cat.like == false){
                                dislike.setImageResource(R.drawable.dislike_click)
                                like.setImageResource(R.drawable.like)
                            }
                        } ?: run {
                            like.setImageResource(R.drawable.like)
                            dislike.setImageResource(R.drawable.dislike)
                        }
                    }
                }
            }
            binding.apply {
                Glide.with(itemView)
                    .load(cat.url)
                    .placeholder(R.drawable.progress_animation)
                    .into(image)

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
