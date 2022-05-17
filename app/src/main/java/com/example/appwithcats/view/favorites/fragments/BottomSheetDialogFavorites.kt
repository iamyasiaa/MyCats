package com.example.appwithcats.view.favorites.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogFavorites: BottomSheetDialogFragment() {
    private lateinit var image2: ImageView
    private val args: BottomSheetDialogFavoritesArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view: View = inflater.inflate(R.layout.bottom_sheet_dialog_favorites, container)

        image2 = view.findViewById(R.id.showCatsFavorites)
        val url = args.urlFavorites
        Glide.with(this)
            .load(url)
            .fitCenter()
            .centerCrop()
            .placeholder(R.drawable.progress_animation)
            .into(image2)

        return view

    }
    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}