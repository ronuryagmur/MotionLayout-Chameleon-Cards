package com.pluvia.material.chameleoncards.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pluvia.material.chameleoncards.EventAdapter
import com.pluvia.material.chameleoncards.databinding.FragmentListBinding
import com.pluvia.material.chameleoncards.utils.AutoAlignLayoutManager
import com.pluvia.material.chameleoncards.utils.StartSnapHelper

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var eventAdapter: EventAdapter? = null
    private val vm: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        initObservables()
        setUpEventAdapter()
        return binding.root
    }

    private fun initObservables(){
        vm.snappedEventItemPosition.observe(viewLifecycleOwner, Observer {
            updateEventItems(it)
        })
    }

    /***
     * whenever new event item snapped to the start of the screen
     * it notifies the snapped item, previous item and next item
     * to trigger the animation.
     ***/
    private fun updateEventItems(position: Int){
        eventAdapter!!.notifyItemChanged(position)
        eventAdapter!!.notifyItemChanged(position-1)
        eventAdapter!!.notifyItemChanged(position+1)
    }

    private fun setUpEventAdapter(){
        binding.rvList.layoutManager = AutoAlignLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvList.setHasFixedSize(true)
        eventAdapter = EventAdapter(vm)
        binding.rvList.adapter = eventAdapter
        StartSnapHelper().attachToRecyclerView(binding.rvList)
    }
}