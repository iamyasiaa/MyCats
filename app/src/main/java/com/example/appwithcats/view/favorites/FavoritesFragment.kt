package com.example.appwithcats.view.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentFavoritesBinding
import com.example.appwithcats.domain.FavoritesModel
import com.example.appwithcats.view.FavoritesAdapter
import com.example.appwithcats.view.cats.viemodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class FavoritesFragment : Fragment() {
    private var favoritesAdapter: FavoritesAdapter? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var image3: ImageView
    private lateinit var recyclerFavorites: RecyclerView
    private lateinit var favoritesModel: FavoritesModel


    private val favoritesViewModel: FavoritesViewModel by lazy {
        ViewModelProvider(this)[FavoritesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutFav)
        recyclerFavorites = view.findViewById(R.id.recyclerFavorites)
        recyclerFavorites.apply {
            adapter = FavoritesAdapter(viewLifecycleOwner) {
                onClickImageItem(it.image.url)
            }.also { favoritesAdapter = it }
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        favoritesViewModel.favLiveData.observe(viewLifecycleOwner) {
            mSwipeRefreshLayout?.isRefreshing = false
//            favoritesViewModel.postRequest()
            favoritesAdapter?.submitList(it)
        }
        mSwipeRefreshLayout?.setOnRefreshListener {
            mSwipeRefreshLayout?.isRefreshing = false
            favoritesViewModel.postRequest()
            favoritesViewModel.postRequestVotes()
        }
    }




    fun onClickImageItem(url: String) {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFavoritesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorites, container, false
        )

        return binding.root
    }
}