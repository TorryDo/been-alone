package com.torrydo.beenalone.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.torrydo.beenalone.R
import com.torrydo.beenalone.adapter.Adapter_main_viewPager
import com.torrydo.beenalone.databinding.FragmentHelloBinding
import java.lang.ref.WeakReference

class HelloFragment : Fragment() {

    private var binding: FragmentHelloBinding? = null
//    private val mContext = WeakReference<Context>(requireContext()).get()!!
//    private val mActivity = WeakReference<Activity>(requireActivity()).get()!!

    private var mAdapter : Adapter_main_viewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelloBinding.inflate(inflater,container,false)
        defaultStuffs()

        return binding?.root
    }

    private fun defaultStuffs() {

        val listFrags = arrayListOf<Fragment>(
            Hello_1(),
            Hello_2()
        )
        mAdapter = Adapter_main_viewPager(childFragmentManager,lifecycle,listFrags)

        binding!!.helloViewPager.also { vp2 ->
            vp2.adapter = mAdapter
//            vp2.isUserInputEnabled = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (requireActivity().isFinishing) {

            if (binding != null) {
                binding!!.root.removeAllViews()
                binding = null
            }

        }
    }
}