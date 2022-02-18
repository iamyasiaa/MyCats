package com.example.appwithcats.ui.cats


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.appwithcats.R
import com.example.appwithcats.adapter.CatListAdapter
import com.example.appwithcats.databinding.FragmentCatsBinding



class CatsFragment : Fragment(R.layout.fragment_cats) {


    private var catListAdapter: CatListAdapter? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var isShow = true


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCatsBinding.bind(view)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        if (isShow) {
            onInitVM()
        }
        isShow = false



        binding.apply {
            recyclerCats.apply {
                adapter = CatListAdapter(viewLifecycleOwner) {
                    val action =
                        CatsFragmentDirections.actionCatsFragmentToCatFragment(it.url)
                    findNavController().navigate(action)
                }.also { catListAdapter = it }
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)


            }
        }
        mSwipeRefreshLayout!!.setOnRefreshListener {
            onInitVM()
            mSwipeRefreshLayout!!.isRefreshing = false
        }
    }

    fun onInitVM() {
        mainViewModel.randomImage.observe(viewLifecycleOwner) {
            catListAdapter?.submitList(it)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }
}