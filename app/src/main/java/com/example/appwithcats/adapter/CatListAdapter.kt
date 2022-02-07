package com.example.appwithcats.adapter

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.Util
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.databinding.ItemBinding
import com.example.appwithcats.ui.authorization.AuthorizationViewModel
import com.example.appwithcats.ui.cats.CatsFragmentDirections
import com.example.appwithcats.ui.cats.MainViewModel

private val mainViewModel: MainViewModel = MainViewModel(application = Application())
class CatListAdapter :
    androidx.recyclerview.widget.ListAdapter<CatModel, CatListAdapter.CatViewHolder>(CatDiffCallback()) {

    var _id: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentItem = getItem(position)
        _id = currentItem.id
        holder.bind(currentItem)
    }

    inner class CatViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: CatModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(cat.url)
                    .placeholder(R.drawable.progress_animation)
                    .into(image)
            }
            binding.image.apply {
                setOnClickListener {
                    val action =
                        CatsFragmentDirections.actionCatsFragmentToCatFragment(urlCat = cat.url)
                    Navigation.findNavController(itemView).navigate(action)
                }
            }
            binding.like.setOnClickListener {

            }

            binding.like.setOnClickListener {
                mainViewModel.updateLike()
                Util.id = _id
                mainViewModel.postRequest()
                binding.dislike.setImageResource(R.drawable.dislike)
                binding.like.setImageResource(R.drawable.like_click)
            }
            binding.dislike.setOnClickListener {
                mainViewModel.updateDislike()
                Util.id = _id
                mainViewModel.postRequest()
                binding.dislike.setImageResource(R.drawable.dislike_click)
                binding.like.setImageResource(R.drawable.like)
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
