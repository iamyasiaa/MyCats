package com.example.appwithcats.view.favorites.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentFavoritesBinding
import com.example.appwithcats.view.favorites.adapter.FavoritesAdapter
import com.example.appwithcats.view.favorites.viewmodel.FavoritesViewModel


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
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToBottomSheetDialogFavorites(
                        it.image.url
                    )
                findNavController().navigate(action)
            }.also { favoritesAdapter = it }.apply {

            }
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }



        favoritesViewModel.favLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayoutFav.isRefreshing = false
            favoritesAdapter?.submitList(it)
                if (favoritesAdapter?.currentList?.size == 0) {
                    binding.smile?.visibility = View.VISIBLE
                    binding.text?.visibility = View.VISIBLE
                } else if(favoritesAdapter?.itemCount !=0) {
                    binding.smile?.visibility = View.GONE
                    binding.text?.visibility = View.GONE

                }
        }
        binding.swipeRefreshLayoutFav.setOnRefreshListener {
            binding.swipeRefreshLayoutFav.isRefreshing = false
            favoritesViewModel.postRequest()
            favoritesViewModel.postRequestVotes()
        }
        return binding.root
    }

}