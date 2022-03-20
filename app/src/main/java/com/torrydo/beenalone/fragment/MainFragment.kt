package com.torrydo.beenalone.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.torrydo.beenalone.adapter.Adapter_main_viewPager
import com.torrydo.beenalone.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    //    private val mViewModel: MyViewModel by activityViewModels()
    private var binding: FragmentMainBinding? = null

    private var mAdapter: Adapter_main_viewPager? = null


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        defaultStuffs()

        binding!!.mainViewPager.also { vp2 ->
            vp2.adapter = mAdapter
//            vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageScrolled(
//                    position: Int,
//                    positionOffset: Float,
//                    positionOffsetPixels: Int
//                ) {
////                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                    when (position) {
//                        0 -> binding!!.mainBottomNavView.menu.findItem(R.id.main1).isChecked = true
//                        1 -> binding!!.mainBottomNavView.menu.findItem(R.id.main2).isChecked = true
//                        2 -> binding!!.mainBottomNavView.menu.findItem(R.id.main3).isChecked = true
//                    }
//                }
//
//                override fun onPageSelected(position: Int) {
////                    super.onPageSelected(position)
//                    when (position) {
//                        0 -> binding!!.mainBottomNavView.menu.findItem(R.id.main1).isChecked = true
//                        1 -> binding!!.mainBottomNavView.menu.findItem(R.id.main2).isChecked = true
//                        2 -> binding!!.mainBottomNavView.menu.findItem(R.id.main3).isChecked = true
//                    }
//                }
//
//            })
        }


        binding!!.mainBottomNavView.setupWithViewPager2(binding!!.mainViewPager)

        return binding!!.root
    }

    private fun defaultStuffs() {
        mAdapter = Adapter_main_viewPager(
            childFragmentManager, lifecycle, arrayListOf(
                Main_1_Fragment()/*, Main_2_Fragment()*/, Main_3_Fragment()
            )
        )
    }

//    override fun onPause() {
//        super.onPause()
//        if (requireActivity().isFinishing) {
//
//            if (binding != null) {
//                binding!!.root.removeAllViews()
//                binding = null
//            }
//        }
//    }

}