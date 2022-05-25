package com.example.appwithcats.view.favorites.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.BottomSheetDialogBinding
import com.example.appwithcats.databinding.BottomSheetDialogFavoritesBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogFavorites: BottomSheetDialogFragment() {
    private val args: BottomSheetDialogFavoritesArgs by navArgs()
    private lateinit var binding: BottomSheetDialogFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetDialogFavoritesBinding.inflate(inflater, container, false)
        val url = args.urlFavorites
        Glide.with(this)
            .load(url)
            .fitCenter()
            .centerCrop()
            .placeholder(R.drawable.progress_animation)
            .into(binding.showCatsFavorites)

        return binding.root
    }
    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}