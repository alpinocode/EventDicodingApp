package com.example.eventdicoding.ui.home

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

class HomeViewModel : ViewModel() {

    private val _listEventsSuccess = MutableLiveData<List<ListEventsItem>>()
    val listEventsSuccess: LiveData<List<ListEventsItem>> = _listEventsSuccess

    private val _listEventsIsComming = MutableLiveData<List<ListEventsItem>>()
    val listEventsIsComing: LiveData<List<ListEventsItem>> = _listEventsIsComming

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "HomeViewModel"
        private const val EVENT_ACTIVE = 1
        private const val EVENT_IS_SUCCESSFULL = 0
    }

    init {
        findEventIsSuccessful()
        findEventActive()
    }


    private fun findEventActive() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(EVENT_ACTIVE)
        client.enqueue(object : Callback<EventDicodingResponse> {
            override fun onResponse(
                call: Call<EventDicodingResponse>,
                response: Response<EventDicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listEventsIsComming.value = response.body()?.listEvents ?: emptyList()
                } else {
                    Log.e(TAG, "onResponse Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.localizedMessage}")
            }
        })
    }
    private fun findEventIsSuccessful() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(EVENT_IS_SUCCESSFULL)
        client.enqueue(object : Callback<EventDicodingResponse> {
            override fun onResponse(
                call: Call<EventDicodingResponse>,
                response: Response<EventDicodingResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _listEventsSuccess.value = response.body()?.listEvents
                } else{
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.localizedMessage}")
            }
        })
    }

}
