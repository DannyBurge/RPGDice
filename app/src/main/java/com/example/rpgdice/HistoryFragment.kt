package com.example.rpgdice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rpgdice.databinding.HistoryFragmentBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: HistoryFragmentBinding
    private lateinit var rollResultHistory: LiveData<MutableList<RollResult>?>
    private lateinit var historyWasChanged: LiveData<Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rollResultHistory = (activity as MainActivity).getRollResultHistory()
        historyWasChanged = (activity as MainActivity).getHistorySize()
        binding = DataBindingUtil.inflate(inflater, R.layout.history_fragment, container, false)
        binding.rollResultHistoryList.layoutManager = LinearLayoutManager(this.context)

        binding.rollResultHistoryList.adapter =
            RollsResultsHistoryAdapter(this.context!!, rollResultHistory.value)

        historyWasChanged.observe(viewLifecycleOwner) {
            Log.i("LiveData", rollResultHistory.value!!.size.toString())
            binding.rollResultHistoryList.adapter =
                RollsResultsHistoryAdapter(this.context!!, rollResultHistory.value!!.reversed())
        }

        return binding.root
    }
}