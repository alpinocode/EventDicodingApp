package com.example.eventdicoding.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.refrofit.ApiConfig
import com.example.eventdicoding.data.response.ListEventsItem
import com.example.eventdicoding.data.response.EventDicodingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _searchList = MutableLiveData<List<ListEventsItem>>()
    val searchList: LiveData<List<ListEventsItem>> = _searchList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "SearchViewModel"
        private const val EVENT_ACTIVE_ID = 0
    }

    fun searchView(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEventSearch(EVENT_ACTIVE_ID,query)
        Log.d(TAG, "Cek Apinya ${client.request()}")
        client.enqueue(object : Callback<EventDicodingResponse> {
            override fun onResponse(
                call: Call<EventDicodingResponse>,
                response: Response<EventDicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchList.value = response.body()?.listEvents ?: emptyList()
                    val eventSearchItem = response.body()?.listEvents
                    Log.d(TAG, "Cek Apakah Datanya Ada ${eventSearchItem}")
                } else {
                    Log.e(TAG, "onResponse Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure: ${t.message}")
            }
        })
    }
}
