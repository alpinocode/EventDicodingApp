package com.example.eventdicoding.ui.detailevent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.eventdicoding.R
import com.example.eventdicoding.databinding.ActivityDetailEventBinding

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding
    private val viewModel: DetailViewModel by viewModels()

    companion object {
        private const val TAG = "DetailEvent"
        const val EVENTID = "eventId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val eventId = intent.getIntExtra(EVENTID, 0)
        supportActionBar?.title = "Detail View "
        if (eventId != 0) {
            viewModel.getEventDataById(eventId)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.listEventDetail.observe(this) { eventList ->
            val eventId = intent.getIntExtra(EVENTID, -1)
            val data = eventList.find { it.id == eventId }

            if (data != null) {
                Log.d(TAG, "Event ditemukan: ${data.id}")

                binding.namaEvent.text = data.name
                binding.namaPenyelenggara.text = data.ownerName
                val dataQuota = data.quota?.toInt() ?: 0
                val dataRegistrants = data.registrants?.toInt() ?: 0

                val sisaQuota = dataQuota - dataRegistrants

                binding.sesiRegister.text = "Sisa Kuota: $sisaQuota Ticket"
                binding.beginTime.text = "${data.beginTime}"
                binding.descriptionDetail.text = HtmlCompat.fromHtml(
                    data.description.toString(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )

                binding.toUrlEventDicoding.setOnClickListener {
                    val dataUrl = data.link
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataUrl))
                    startActivity(intent)
                }
                data.imageLogo?.let { imageUrl ->
                    Glide.with(this)
                        .load(imageUrl)
                        .into(binding.imageViewEvent)
                }
            } else {
                Log.e(TAG, "Event tidak ditemukan!")
            }
        }

        viewModel.isLoading.observe(this) {
           showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.toUrlEventDicoding.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.toUrlEventDicoding.visibility = View.VISIBLE
        }
    }
}
