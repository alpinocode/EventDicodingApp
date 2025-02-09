package com.example.eventdicoding.ui.finished

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.data.response.ListEventsItem
import com.example.eventdicoding.databinding.FragmentFinishedBinding
import com.example.eventdicoding.ui.adapter.EventAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FinishedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FinishedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private lateinit var finishedViewModel: FinishedViewModel
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
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finishedViewModel = ViewModelProvider(this)[FinishedViewModel::class.java]

        adapter = EventAdapter()
        binding.contentViewFinished.layoutManager = LinearLayoutManager(requireContext())
        binding.contentViewFinished.adapter = adapter
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Finished "
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)





        finishedViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        finishedViewModel.listEventIsSuccess.observe(viewLifecycleOwner) { event ->
            event?.let { setData(it) }
        }
    }

    private fun setData(eventList: List<ListEventsItem>) {
        adapter.submitList(eventList)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}