package com.example.appwithcats.view.cats.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentAutorizationBinding
import com.example.appwithcats.databinding.FragmentCatsBinding
import com.example.appwithcats.view.CatListAdapter
import com.example.appwithcats.view.cats.viemodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class CatsFragment : Fragment() {


    private var catListAdapter: CatListAdapter? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var image2: ImageView
    private lateinit var recyclerCats: RecyclerView


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        recyclerCats = view.findViewById(R.id.recyclerCats)
            recyclerCats.apply {
                adapter = CatListAdapter(viewLifecycleOwner) {
                    onClickImageItem(it.url)
                }.also { catListAdapter = it }
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

        mainViewModel.catsLiveData.observe(viewLifecycleOwner) {
            mSwipeRefreshLayout?.isRefreshing = false
            catListAdapter?.submitList(it)
        }
        mSwipeRefreshLayout?.setOnRefreshListener {
            mSwipeRefreshLayout?.isRefreshing = false
            mainViewModel.postRequest()
        }
    }

    fun onClickImageItem(url: String) {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this.requireContext())
        image2 = view!!.findViewById(R.id.showCats)
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.progress_animation)
            .into(image2)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCatsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cats, container, false
        )

        return binding.root
    }
}