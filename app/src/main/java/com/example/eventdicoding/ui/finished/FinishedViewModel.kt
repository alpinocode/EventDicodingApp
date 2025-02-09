package com.example.eventdicoding.ui.finished

import android.util.Log
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.refrofit.ApiConfig
import com.example.eventdicoding.data.response.EventDicodingResponse
import com.example.eventdicoding.data.response.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel(){
    private val _listEventIsSuccess = MutableLiveData<List<ListEventsItem>>()
    val listEventIsSuccess: LiveData<List<ListEventsItem>> = _listEventIsSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    companion object{
        private const val TAGFINISHEDVIEWMODEL = "FinishedViewModel"
        private const val EVENTISSUCCESSFULL = 0
    }

    init {
        getEventDataIsSuccessfull()
    }

    private fun getEventDataIsSuccessfull() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(EVENTISSUCCESSFULL)
        client.enqueue(object : Callback<EventDicodingResponse> {
            override fun onResponse(
                call: Call<EventDicodingResponse>,
                response: Response<EventDicodingResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _listEventIsSuccess.value = response.body()?.listEvents
                } else {
                    Log.e(TAGFINISHEDVIEWMODEL, "onResponse Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAGFINISHEDVIEWMODEL, "onFailure : ${t.message}")
            }
        })
    }

}