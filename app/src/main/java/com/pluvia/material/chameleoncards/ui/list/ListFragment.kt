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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var eventAdapter: EventAdapter? = null
    private val vm: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        vm.snappedEventItemPosition.observe(viewLifecycleOwner, Observer {
            Log.d("listFragment","$it")
            eventAdapter!!.notifyItemChanged(it)
            eventAdapter!!.notifyItemChanged(it-1)
            eventAdapter!!.notifyItemChanged(it+1)
        })

        setUpEventAdapter()
        return binding.root
    }

    private fun setUpEventAdapter(){
        binding.rvList.layoutManager = AutoAlignLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvList.setHasFixedSize(true)
        eventAdapter = EventAdapter(vm)
        binding.rvList.adapter = eventAdapter
        StartSnapHelper().attachToRecyclerView(binding.rvList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}