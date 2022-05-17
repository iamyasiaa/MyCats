package com.example.appwithcats.view.cats.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentCatsBinding
import com.example.appwithcats.view.CatListAdapter
import com.example.appwithcats.view.cats.viemodel.MainViewModel


class CatsFragment : Fragment() {


    private var catListAdapter: CatListAdapter? = null
    private lateinit var image2: ImageView


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCatsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cats, container, false
        )
        binding.recyclerCats.apply {
            adapter = CatListAdapter(viewLifecycleOwner) {
                val action =
                    CatsFragmentDirections.actionCatsFragmentToBottomSheetDialogCats(it.url)
                findNavController().navigate(action)
            }.also { catListAdapter = it }
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        mainViewModel.catsLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = false
            catListAdapter?.submitData(viewLifecycleOwner.lifecycle, it)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            mainViewModel.postRequest()
        }
        catListAdapter?.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.catsBar.visibility = View.VISIBLE
                binding.recyclerCats.visibility = View.GONE
            } else {
                binding.catsBar.visibility = View.GONE
                binding.recyclerCats.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}