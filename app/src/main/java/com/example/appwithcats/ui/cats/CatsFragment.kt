package com.example.appwithcats.ui.cats


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appwithcats.R
import com.example.appwithcats.adapter.CatListAdapter
import com.example.appwithcats.databinding.FragmentCatsBinding
import com.example.appwithcats.repository.SharedPreferenceRepository


class CatsFragment : Fragment(R.layout.fragment_cats) {

    private var isShow = true
    private val catListAdapter = CatListAdapter()


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val action1 = CatsFragmentDirections.actionCatsFragmentToAuthorization()
        if (mainViewModel.sharedPreferenceRepository.email == "") {
            Navigation.findNavController(view).navigate(action1)
        }

        val binding = FragmentCatsBinding.bind(view)

        binding.apply {
            recyclerCats.apply {
                adapter = catListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        if (isShow) {
            mainViewModel.randomImage.observe(viewLifecycleOwner) {
                catListAdapter.submitList(it)
                isShow = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_cats, container, false)


        return view
    }
}