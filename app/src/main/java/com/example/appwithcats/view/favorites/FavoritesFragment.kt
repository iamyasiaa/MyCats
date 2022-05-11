package com.example.appwithcats.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentFavoritesBinding
import com.example.appwithcats.view.FavoritesAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class FavoritesFragment : Fragment() {
    private var favoritesAdapter: FavoritesAdapter? = null
    private lateinit var image3: ImageView



    private val favoritesViewModel: FavoritesViewModel by lazy {
        ViewModelProvider(this)[FavoritesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFavoritesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorites, container, false
        )


        binding.recyclerFavorites.apply {
            adapter = FavoritesAdapter(viewLifecycleOwner) {
                onClickImageItem(it.image.url)
            }.also { favoritesAdapter = it }
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
//        favoritesAdapter?.apply {
//            if (itemCount ==0) {
//                binding.smile?.visibility = View.VISIBLE
//                binding.recyclerFavorites.visibility = View.GONE
//            } else {
//                binding.text?.visibility = View.GONE
//                binding.recyclerFavorites.visibility = View.VISIBLE
//            }
//        }



        favoritesViewModel.favLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayoutFav.isRefreshing = false
            favoritesAdapter?.submitList(it)
        }
        binding.swipeRefreshLayoutFav.setOnRefreshListener {
            binding.swipeRefreshLayoutFav.isRefreshing = false
            favoritesViewModel.postRequest()
            favoritesViewModel.postRequestVotes()
        }
        return binding.root
    }
    private fun onClickImageItem(url: String) {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_dialog_favorites, null)

        val dialog = BottomSheetDialog(this.requireContext())
        image3 = view.findViewById(R.id.showCatsFavorites)
        Glide.with(this)
            .load(url)
            .fitCenter()
            .centerCrop()
            .placeholder(R.drawable.progress_animation)
            .into(image3)
        dialog.setContentView(view)
        dialog.show()
    }
}