package com.torrydo.beenalone.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter_main_viewPager(fm:FragmentManager, lifecycle: Lifecycle, val listFragment : List<Fragment>) : FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}