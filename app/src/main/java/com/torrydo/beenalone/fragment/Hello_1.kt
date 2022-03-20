package com.torrydo.beenalone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.torrydo.beenalone.R
import com.torrydo.beenalone.databinding.FragmentHello1Binding
import com.torrydo.beenalone.utility.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Hello_1 : Fragment() {

    private var binding : FragmentHello1Binding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHello1Binding.inflate(inflater,container,false)
        defaultUI()

        clicker()
        return binding!!.root
    }

    private fun clicker() {
        binding!!.hello1ButtonPositive.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main){
//                binding!!.hello1CircleImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.cat_selfie))

                binding!!.hello1ButtonPositive.isEnabled = false

                hideSomeWidget()
                delay(1000L)

                binding!!.hello1ReuseAvatar.lottieHeart.also { lt ->
                    lt.visibility = View.VISIBLE
                    lt.playAnimation()

                }

                delay(2000L)
                requireActivity().findViewById<ViewPager2>(R.id.helloViewPager).currentItem = 1
            }

        }
    }

    private fun defaultUI() {
        animateUI()
    }

    private fun animateUI() {
        lifecycleScope.launch(Dispatchers.Main){
            delay(500L)
            Utils.open_tiny_scroll_down(requireContext(),binding!!.hello1Txt1)
            delay(2000L)
            Utils.open_tiny_scroll_down(requireContext(),binding!!.hello1Txt2)
            delay(2000L)
            Utils.open_tiny_scroll_down(requireContext(),binding!!.hello1Txt3)
            delay(2500L)
            Utils.open_tiny_scroll_down(requireContext(),binding!!.hello1ReuseAvatar.circleImageView)
            Utils.open_tiny_scroll_down(requireContext(),binding!!.hello1ButtonPositive)
            Utils.open_tiny_scroll_down(requireContext(),binding!!.hello1ButtonNegative)
        }
    }
    private fun hideSomeWidget(){
        Utils.exit_tiny_scroll_down(requireContext(),binding!!.hello1Txt1)
        Utils.exit_tiny_scroll_down(requireContext(),binding!!.hello1Txt2)
        Utils.exit_tiny_scroll_down(requireContext(),binding!!.hello1Txt3)
        Utils.exit_tiny_scroll_down(requireContext(),binding!!.hello1ButtonNegative)
//        Utils.exit_tiny_scroll_down(requireContext(),binding!!.hello1ButtonPositive)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (isDetached) {

            if (binding != null) {
                binding!!.root.removeAllViews()
                binding = null
            }

        }

    }


}