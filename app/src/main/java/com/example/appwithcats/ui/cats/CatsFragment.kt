package com.example.appwithcats.ui.cats


import android.content.ClipData
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.appwithcats.R
import com.example.appwithcats.adapter.CatListAdapter
import com.example.appwithcats.databinding.ActivityMainBinding.bind
import com.example.appwithcats.databinding.FragmentCatsBinding
import com.example.appwithcats.databinding.ItemBinding
import com.example.appwithcats.model.CatModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import timber.log.Timber


class CatsFragment : Fragment(R.layout.fragment_cats) {


    private val catListAdapter = CatListAdapter()
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var isShow = true
    private val args: CatsFragmentArgs by navArgs()


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)

    }
    private fun showErrorWindow(message: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.Error))
                .setMessage(message)
                .setPositiveButton("ОК") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCatsBinding.bind(view)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        if(isShow){
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
    companion object {

        fun newInstance() =
            CatsFragment().apply {

            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }
}