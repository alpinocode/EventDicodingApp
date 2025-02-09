package com.example.eventdicoding.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventdicoding.data.response.ListEventsItem
import com.example.eventdicoding.databinding.FinishedRowEventBinding
import com.example.eventdicoding.ui.detailevent.DetailEventActivity

class EventIsComingAdapter : ListAdapter<ListEventsItem, EventIsComingAdapter.MyViewHolder>(
    DIFF_CALLBACK) {
    class MyViewHolder(private val binding: FinishedRowEventBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imageEvent)

            binding.titleEventDicoding.text = event.name

            binding.cardViewFinished.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailEventActivity::class.java).apply {
                    putExtra(DetailEventActivity.EVENTID, event.id)
                }
                context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventIsComingAdapter.MyViewHolder {
        val binding = FinishedRowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventIsComingAdapter.MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }
}