package com.example.appwithcats.view.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentCatsBinding
import com.example.appwithcats.databinding.FragmentFavoritesBinding
import com.example.appwithcats.domain.FavoritesModel
import com.example.appwithcats.view.CatListAdapter
import com.example.appwithcats.view.FavoritesAdapter
import com.example.appwithcats.view.cats.viemodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class FavoritesFragment : Fragment() {
    private var favoritesAdapter: FavoritesAdapter? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var image2: ImageView
    private lateinit var recyclerFavorites: RecyclerView


    private val favoritesViewModel: FavoritesViewModel by lazy {
        ViewModelProvider(this)[FavoritesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutFav)
        recyclerFavorites = view.findViewById(R.id.recyclerFavorites)
        recyclerFavorites.apply {
            adapter = FavoritesAdapter(viewLifecycleOwner) {
                onClickImageItem(it.url)
            }.also { favoritesAdapter = it }
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        favoritesViewModel.favLiveData.observe(viewLifecycleOwner) {
            mSwipeRefreshLayout?.isRefreshing = false
            favoritesAdapter?.submitData(viewLifecycleOwner.lifecycle, it)
        }
        mSwipeRefreshLayout?.setOnRefreshListener {
            mSwipeRefreshLayout?.isRefreshing = false
            favoritesViewModel.postRequest()
        }
    }

    fun onClickImageItem(url: String) {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this.requireContext())
        image2 = view.findViewById(R.id.showCats)
        Glide.with(this)
            .load(url)
            .fitCenter()
            .centerCrop()
            .placeholder(R.drawable.progress_animation)
            .into(image2)
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