package com.example.eventdicoding.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.refrofit.ApiConfig
import com.example.eventdicoding.data.response.EventDicodingResponse
import com.example.eventdicoding.data.response.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {
    private val _upcomingListEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingListEvents: LiveData<List<ListEventsItem>> = _upcomingListEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private val TAGUPCOMING ="UpComingViewModel"
        private const val EVENT_ACTIVE = 1
    }

    init {
        getEventIsComming()
    }

    private fun getEventIsComming() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(EVENT_ACTIVE)
        client.enqueue(object : Callback<EventDicodingResponse> {
            override fun onResponse(
                call: Call<EventDicodingResponse>,
                response: Response<EventDicodingResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _upcomingListEvents.value = response.body()?.listEvents ?: emptyList()
                } else {
                    Log.e(TAGUPCOMING, "onResponse Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAGUPCOMING, "onFailure : ${t.message}")
            }
        })
    }
}