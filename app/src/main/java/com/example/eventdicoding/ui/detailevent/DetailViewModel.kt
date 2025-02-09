package com.example.eventdicoding.ui.detailevent

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

class DetailViewModel : ViewModel() {
    private val _listEventDetail = MutableLiveData<List<ListEventsItem>>()
    val listEventDetail: LiveData<List<ListEventsItem>> = _listEventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getEventDataById(eventId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(eventId)

        client.enqueue(object : Callback<EventDicodingResponse> {
            override fun onResponse(
                call: Call<EventDicodingResponse>,
                response: Response<EventDicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val eventData = response.body()?.listEvents
                    _listEventDetail.value = eventData ?: emptyList()
                } else {
                    Log.e(TAG, "Gagal mengambil event: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Kesalahan koneksi: ${t.message}")
            }
        })
    }
}
