package com.example.appwithcats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.Util
import com.example.appwithcats.databinding.ItemBinding
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.ui.cats.CatsFragment
import com.example.appwithcats.ui.cats.CatsFragmentDirections
import com.example.appwithcats.ui.cats.VoteViewModel

class CatListAdapter :
    androidx.recyclerview.widget.ListAdapter<CatModel, CatListAdapter.CatViewHolder>(CatDiffCallback()) {

    var _id: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val voteViewModel = VoteViewModel()
        val catsFragment = CatsFragment()
        val currentItem = getItem(position)
        _id = currentItem.id
        holder.bind(currentItem)
//        when {
//            like.isPressed -> {
//                dislike.setImageResource(R.drawable.dislike)
//                like.setImageResource(R.drawable.like_click)
//            }
//            dislike.isClickable -> {
//                dislike.setImageResource(R.drawable.dislike_click)
//                like.setImageResource(R.drawable.like)
//            }
//            voteViewModel.voteInLiveData.value?.message != "SUCCESS" -> {
//                catsFragment.showErrorWindow("Ошибка")
//            }
//        }
    }

    inner class CatViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val voteViewModel = VoteViewModel()
        private val catsFragment = CatsFragment()


        fun bind(cat: CatModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(cat.url)
                    .placeholder(R.drawable.progress_animation)
                    .into(image)
//        when {
//            like.isPressed -> {
//                dislike.setImageResource(R.drawable.dislike)
//                like.setImageResource(R.drawable.like_click)
//            }
//            dislike.isClickable -> {
//                dislike.setImageResource(R.drawable.dislike_click)
//                like.setImageResource(R.drawable.like)
//            }
//            voteViewModel.voteInLiveData.value?.message != "SUCCESS" -> {
//                catsFragment.showErrorWindow("Ошибка")
//            }
//        }
            }
            binding.image.apply {
                setOnClickListener {
                    val action =
                        CatsFragmentDirections.actionCatsFragmentToCatFragment(urlCat = cat.url)
                    Navigation.findNavController(itemView).navigate(action)
                }
            }


            binding.like.setOnClickListener {
                voteViewModel.updateLike()
                Util.id = _id
                voteViewModel.postRequest()
//                if(voteViewModel.voteInLiveData.value?.message != "SUCCESS"){
//                    catsFragment.showErrorWindow("Ошибка")
//
//                }else {
//                    binding.dislike.setImageResource(R.drawable.dislike)
//                    binding.like.setImageResource(R.drawable.like_click)
//                }

            }
            binding.dislike.setOnClickListener {
                voteViewModel.updateDislike()
                Util.id = _id
                voteViewModel.postRequest()
//                if(voteViewModel.voteInLiveData.value?.message != "SUCCESS"){
//                    catsFragment.showErrorWindow("Ошибка")
//                } else{
//                    binding.dislike.setImageResource(R.drawable.dislike_click)
//                    binding.like.setImageResource(R.drawable.like)
//                }

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
