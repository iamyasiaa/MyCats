package com.example.appwithcats.ui.cats

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.appwithcats.R

class CatFragment : Fragment() {
    private lateinit var image2: ImageView
    private val args: CatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cat, container, false)

        image2 = view!!.findViewById(R.id.showCat)
        val url = args.urlCat
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.progress_animation)
            .into(image2)
        return view
    }
}