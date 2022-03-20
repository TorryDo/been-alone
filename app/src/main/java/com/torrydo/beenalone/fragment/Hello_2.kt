package com.torrydo.beenalone.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.torrydo.beenalone.CONSTANT
import com.torrydo.beenalone.R
import com.torrydo.beenalone.databinding.FragmentHello2Binding
import com.torrydo.beenalone.utility.Utils
import com.torrydo.beenalone.vModel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class Hello_2 : Fragment() {

    private val mViewModel: MyViewModel by activityViewModels()
    private var binding: FragmentHello2Binding? = null

    private var userName = "Seiya"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHello2Binding.inflate(inflater, container, false)
        defaultStuffs()
        defaultUI()



        myEyes()
        clicker()
        return binding!!.root
    }

    private fun myEyes() {
        mViewModel.lonelyDate.observe(viewLifecycleOwner, {
            binding!!.hello2ButtonDate.text = it
        })
    }

    private fun defaultStuffs() {

    }


    private fun clicker() {
        binding!!.hello2ReuseSearchbar.buttonConfirm.setOnClickListener {

            val tempName = binding!!.hello2ReuseSearchbar.searchEdittext.text.toString()

            if (tempName.isNotEmpty()) {
                userName = tempName
            }

            mViewModel.mPref.edit().putString(CONSTANT.USER_NAME, userName).apply()

            hideSomeWidgets_1()

            displaySomeUI()

            Utils.hideKeyboard(binding!!.root, requireContext())

        }

        binding!!.hello2ButtonDate.setOnClickListener {
            findNavController().navigate(R.id.action_helloFragment_to_customDatePickerFragment)
        }

        binding!!.hello2Button2.setOnClickListener {

            hideSomeWidgets_2()

            displaySomeUI_2()

            it.isEnabled = false

            mViewModel.mPref.edit().putString(CONSTANT.LONELY_DATE, mViewModel.lonelyDate.value)
                .apply()

        }

        binding!!.hello2ButtonFinal.setOnClickListener {

            mViewModel.mPref.edit().putBoolean(CONSTANT.FIRST_LAUNCH, true).apply()

            it.isEnabled = false

            lifecycleScope.launch {
                hideSomeWidgets_3()
                delay(1500L)

                binding!!.hello2ReuseAvatar.lottieHeart.also {
                    it.visibility = View.VISIBLE
                    it.playAnimation()
                }

                delay(3000L)
                findNavController().navigate(R.id.action_helloFragment_to_mainFragment)
            }
        }

    }

    private fun defaultUI() {

        lifecycleScope.launch(Dispatchers.Main) {
            delay(500L)
            Utils.open_fade(requireContext(), binding!!.hello2ReuseAvatar.circleImageView)
            Utils.open_fade(requireContext(), binding!!.hello2ReuseAvatar.lottieHeart)

            delay(500L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt1)
            delay(1500L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt2)
            delay(1500L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt3)
            delay(1000L)
            Utils.open_fade(requireContext(), binding!!.hello2ReuseSearchbar.buttonConfirm)
            Utils.open_fade(requireContext(), binding!!.hello2ReuseSearchbar.searchEdittext)

        }
    }

    private fun displaySomeUI() {
        lifecycleScope.launch() {
            delay(500L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt4)
            delay(1500L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt5)
            delay(1000L)
            Utils.open_fade(requireContext(), binding!!.hello2ButtonDate)

            delay(2000L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Button2)

        }

    }

    private fun displaySomeUI_2() {
        lifecycleScope.launch() {
            delay(500L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt6)
            delay(1500L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt7)
            delay(2500L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt8)
            delay(1000L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt9)
            delay(2000L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2Txt10)

            delay(2000L)
            Utils.open_tiny_scroll_down(requireContext(), binding!!.hello2ButtonFinal)

        }

    }

    private fun hideSomeWidgets_3() {

        lifecycleScope.launch() {
            Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt6)
            Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt7)
            Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt8)
            Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt9)
            Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt10)
            Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2ButtonFinal)
        }


    }

    private fun hideSomeWidgets_1() {
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt1)
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt2)
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt3)
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2ReuseSearchbar.searchEdittext)
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2ReuseSearchbar.buttonConfirm)
    }

    private fun hideSomeWidgets_2() {
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt4)
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Txt5)
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2ButtonDate)
        Utils.exit_tiny_scroll_down(requireContext(), binding!!.hello2Button2)
    }

    override fun onPause() {
        super.onPause()
        if (isRemoving) {
            if (binding != null) {
                binding!!.root.removeAllViews()
                binding = null
            }
            Toast.makeText(requireContext(), "removing in hello_2", Toast.LENGTH_SHORT).show()
        }
    }

}