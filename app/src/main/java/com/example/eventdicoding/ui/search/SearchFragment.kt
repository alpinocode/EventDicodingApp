package com.example.eventdicoding.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.R
import com.example.eventdicoding.data.response.ListEventsItem
import com.example.eventdicoding.databinding.FragmentSearchBinding
import com.example.eventdicoding.ui.adapter.EventAdapter
import com.example.eventdicoding.ui.home.HomeViewModel
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var _binding: FragmentSearchBinding
    private val binding get() = _binding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: EventAdapter

    companion object{
        private const val TAG = "searchFragment"
    }


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
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]


        adapter = EventAdapter()
        binding.rvSearchView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchView.adapter = adapter
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Search "
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        searchViewModel.searchList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        searchViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        setUpSearchView()
    }


    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.searchView(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.searchView(newText.toString())
                return false
            }
        })
    }




    private fun showLoading(isLoading:Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}