package com.pluvia.material.chameleoncards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pluvia.material.chameleoncards.databinding.ListItemEventBinding

class EventAdapter: RecyclerView.Adapter<EventAdapter.EventHolder>() {
    private lateinit var binding: ListItemEventBinding
    private lateinit var mRecyclerView: RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.EventHolder {
        binding = ListItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapter.EventHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    inner class EventHolder(private val binding: ListItemEventBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(position: Int){
            binding.clEventItem.setOnClickListener {
                mRecyclerView.smoothScrollToPosition(position)
            }
        }
    }

}