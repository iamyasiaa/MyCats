package com.example.appwithcats.ui.cats

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.navigation.Navigation

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.appwithcats.R

class CatFragment : Fragment() {
    private lateinit var image2: ImageView
    private val args: CatFragmentArgs by navArgs()
    lateinit var back: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cat, container, false)
        back = view.findViewById(R.id.imageButton)
        back.setOnClickListener {
            val action = CatFragmentDirections.actionCatFragmentToCatsFragment()
            Navigation.findNavController(view).navigate(action)

        }

        image2 = view!!.findViewById(R.id.showCat)
        val url = args.urlCat
        Glide.with(this)
            .load(url)
            .into(image2)
        return view
    }

//    override fun onBackPressed() {
//        AlertDialog.Builder(this).apply {
//            setTitle("accept")
//            setMessage("Are you sure, exit?")
//
//            setPositiveButton("Yes") { _, _ ->
//                super.onBackPressed()
//            }
//
//            setNegativeButton("No") { _, _ ->
//                Timber.d("Exit", "Thank you")
//            }
//            setCancelable(true)
//        }.create().show()
//    }
}