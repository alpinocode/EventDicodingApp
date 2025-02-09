package com.example.eventdicoding.ui.upcoming

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.transition.Transition.ViewAdapter
import com.example.eventdicoding.R
import com.example.eventdicoding.data.response.ListEventsItem
import com.example.eventdicoding.databinding.FragmentUpcomingBinding
import com.example.eventdicoding.ui.adapter.EventAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpcomingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpcomingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingViewModel: UpcomingViewModel
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentUpcomingBinding.inflate(inflater, container,false )
        return  binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        upcomingViewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]

        adapter = EventAdapter()
        binding.contentRvUpComing.layoutManager = LinearLayoutManager(requireContext())
        binding.contentRvUpComing.adapter = adapter
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Upcoming "
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        upcomingViewModel.isLoading.observe(this@UpcomingFragment) {
            showLoading(it)
        }

        upcomingViewModel.upcomingListEvents.observe(viewLifecycleOwner){ event ->
            event?.let { setData(it) }
        }

    }

    private fun setData(eventList: List<ListEventsItem>) {
        adapter.submitList(eventList)
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressbarUpcomming.visibility = View.VISIBLE
        } else {
            binding.progressbarUpcomming.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}