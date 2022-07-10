package com.pluvia.material.chameleoncards

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pluvia.material.chameleoncards.databinding.ListItemEventBinding
import com.pluvia.material.chameleoncards.ui.list.ListViewModel

class EventAdapter(private val vm: ListViewModel): RecyclerView.Adapter<EventAdapter.EventHolder>() {
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
        val llm = mRecyclerView.layoutManager as LinearLayoutManager

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    vm.setSnappedEventItemPosition(llm.findFirstCompletelyVisibleItemPosition())
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("eventadapter pos fcv: ","${llm.findFirstCompletelyVisibleItemPosition()}")
                Log.d("eventadapter pos fv: ","${llm.findFirstVisibleItemPosition()}")
                Log.d("eventadapter pos lcv: ","${llm.findLastCompletelyVisibleItemPosition()}")
                Log.d("eventadapter pos lv: ","${llm.findLastVisibleItemPosition()}")

//                vm.setSnappedEventItemPosition(llm.findFirstCompletelyVisibleItemPosition())
            }
        })
    }

    inner class EventHolder(private val binding: ListItemEventBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(position: Int){
            binding.apply {
                clEventItem.setOnClickListener {
                    mRecyclerView.smoothScrollToPosition(position)
                }
            }

            if (vm.snappedEventItemPosition.value == position){
                onEventItemClicked()
            }
            else{
                binding.clEventItem.transitionToStart()
            }
        }

        private fun onEventItemClicked(){
            binding.apply {
                if (clEventItem.progress == 0.0f){
                    clEventItem.transitionToEnd()
                }
                else{
                    clEventItem.transitionToStart()
                }
            }
        }
    }

}