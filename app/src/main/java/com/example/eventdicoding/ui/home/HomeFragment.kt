package com.example.eventdicoding.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentHomeBinding
import com.example.eventdicoding.ui.adapter.EventAdapter
import com.example.eventdicoding.ui.adapter.EventIsComingAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: EventAdapter
    private lateinit var adapterFinished: EventIsComingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]


        adapter = EventAdapter()
        binding.viewEvent.layoutManager = LinearLayoutManager(requireContext())
        binding.viewEvent.adapter = adapter

        adapterFinished = EventIsComingAdapter()
        binding.rvEventUpcoming.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEventUpcoming.adapter = adapterFinished

       
        homeViewModel.isLoading.observe(this@HomeFragment) {
            showLoading(it)
        }


        homeViewModel.listEventsSuccess.observe(viewLifecycleOwner){ events ->
            events?.let { adapter.submitList(it) }
        }

        homeViewModel.listEventsIsComing.observe(viewLifecycleOwner){ events ->
            events?.let { adapterFinished.submitList(it) }
        }

    }


    private fun showLoading(isLoading:Boolean) {
        if(isLoading) {
            binding.progressbar.visibility = View.VISIBLE
            binding.textFinished.visibility = View.GONE
            binding.iconFinished.visibility = View.GONE
        } else {
            binding.progressbar.visibility = View.GONE
            binding.textFinished.visibility = View.VISIBLE
            binding.iconFinished.visibility = View.VISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
