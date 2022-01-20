package com.example.appwithcats.ui.cats


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appwithcats.R
import com.example.appwithcats.adapter.RecyclerAdapter

class CatsFragment : Fragment() {

    private var isShow = true
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerAdapter? = null


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cats, container, false)

        recyclerView = view.findViewById(R.id.recyclerCats) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
        if (isShow) {
            mainViewModel.randomImage.observe(viewLifecycleOwner, { cats ->
                adapter = context?.let { RecyclerAdapter(it, cats) }
                adapter?.notifyDataSetChanged()
                recyclerView!!.adapter = adapter
            })
            isShow = false
        }

        return view
    }

}