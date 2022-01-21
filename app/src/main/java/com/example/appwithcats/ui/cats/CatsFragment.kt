package com.example.appwithcats.ui.cats


import android.os.Bundle
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
    private var myAdapter: RecyclerAdapter? = null


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerCats) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = RecyclerAdapter(requireContext(), arrayListOf())

        if (isShow) {
            mainViewModel.randomImage.observe(viewLifecycleOwner, { cats ->
                myAdapter?.catList = cats
                myAdapter?.notifyDataSetChanged()
            })
            isShow = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }
}