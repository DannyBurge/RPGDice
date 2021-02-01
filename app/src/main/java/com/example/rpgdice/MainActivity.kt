package com.example.rpgdice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.rpgdice.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        val adapter = FragmentTabAdapter(supportFragmentManager)
        adapter.addFragment(DiceFragment())
        adapter.addFragment(HistoryFragment())

        binding.viewPagerMainActivity.adapter = adapter
        binding.viewPagerMainActivity.currentItem = 0
        binding.tabsMainActivity.setupWithViewPager(binding.viewPagerMainActivity)
    }
}