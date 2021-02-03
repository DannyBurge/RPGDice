package com.example.rpgdice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rpgdice.databinding.MainActivityBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    private var rollResultHistoryList = MutableLiveData<MutableList<RollResult>?>()
    fun getRollResultHistory(): LiveData<MutableList<RollResult>?> {
        return rollResultHistoryList
    }

    fun setRollResultHistory(mutList: MutableList<RollResult>?) {
        rollResultHistoryList.value = mutList
    }

    private val historySize = MutableLiveData<Int>()
    fun getHistorySize(): LiveData<Int> {
        return historySize
    }

    fun setHistorySize(size: Int) {
        historySize.value = size
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        val adapter = FragmentTabAdapter(supportFragmentManager)
        adapter.addFragment(DiceFragment())
        adapter.addFragment(HistoryFragment())

        binding.viewPagerMainActivity.adapter = adapter
        binding.viewPagerMainActivity.currentItem = savedInstanceState?.getInt("activeTab") ?: 0
        binding.tabsMainActivity.setupWithViewPager(binding.viewPagerMainActivity)
        setRollResultHistory(savedInstanceState?.getSerializable("rollHistory") as MutableList<RollResult>?)
        savedInstanceState?.clear()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (rollResultHistoryList.value != null) {
            outState.putSerializable("rollHistory", rollResultHistoryList.value as Serializable)
        }
        outState.putInt("activeTab", binding.viewPagerMainActivity.currentItem)
        super.onSaveInstanceState(outState)
    }
}