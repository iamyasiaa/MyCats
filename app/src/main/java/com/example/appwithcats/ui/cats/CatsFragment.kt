package com.example.appwithcats.ui.cats


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.appwithcats.R
import com.example.appwithcats.adapter.CatListAdapter
import com.example.appwithcats.databinding.FragmentCatsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class CatsFragment : Fragment(R.layout.fragment_cats) {


    private val catListAdapter = CatListAdapter()
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var isShow = true



    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)

    }

    fun showErrorWindow(message: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.Error))
                .setMessage(R.string.ErrorVote)
                .setPositiveButton("ОК") { dialog, _ -> dialog.dismiss() }
                .show()
        }
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
                adapter = catListAdapter
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
            catListAdapter.submitList(it)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }
}