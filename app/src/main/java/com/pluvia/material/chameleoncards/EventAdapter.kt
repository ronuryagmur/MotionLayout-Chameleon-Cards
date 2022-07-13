package com.pluvia.material.chameleoncards

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.pluvia.material.chameleoncards.databinding.ListItemEventBinding
import com.pluvia.material.chameleoncards.ui.list.ListViewModel
import com.pluvia.material.chameleoncards.utils.AutoAlignLayoutManager

class EventAdapter(private val vm: ListViewModel) :
    RecyclerView.Adapter<EventAdapter.EventHolder>() {
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

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        mRecyclerView = recyclerView
        (mRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        val llm = mRecyclerView.layoutManager as AutoAlignLayoutManager

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    vm.setSnappedEventItemPosition(llm.findFirstCompletelyVisibleItemPosition())
                    Log.d("eventadapter: ","state idle ${llm.findFirstCompletelyVisibleItemPosition()}")
                }
            }
        })
    }

    inner class EventHolder(private val binding: ListItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            binding.apply {
                clEventItem.setOnClickListener {
                    mRecyclerView.smoothScrollToPosition(position)
                }

                if (vm.snappedEventItemPosition.value == position) {
                    clEventItem.transitionToEnd()
                    clContainer.transitionToEnd()
                } else {
                    clEventItem.transitionToStart()
                    clContainer.transitionToStart()
                }
            }
        }
    }

}