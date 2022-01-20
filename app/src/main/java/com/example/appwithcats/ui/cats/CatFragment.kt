package com.example.appwithcats.ui.cats

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.appwithcats.R

class CatFragment : Fragment() {
    private lateinit var image2: ImageView
    private val args: CatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cat, container, false)

        image2 = view!!.findViewById(R.id.showCat)
        val url = args.urlCat
        Glide.with(this)
            .load(url)
            .into(image2)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.back, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId

        if (id == R.id.back){
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.add(R.id.nav_host_fragment_container, CatsFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()


            Toast.makeText(activity, "Назад", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}