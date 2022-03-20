package com.torrydo.beenalone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.torrydo.beenalone.databinding.FragmentSplashScreenBinding
import com.torrydo.beenalone.vModel.MyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : Fragment() {

    private val mViewModel : MyViewModel by activityViewModels()
    private var binding: FragmentSplashScreenBinding? = null
//    private val mContext = WeakReference<Context>(requireContext()).get()!!
//    private val mActivity = WeakReference<Activity>(requireActivity()).get()!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(LayoutInflater.from(requireContext()))


        lifecycleScope.launch {
            val check = mViewModel.mPref.getBoolean(CONSTANT.FIRST_LAUNCH,false)
            delay(1000L)
            defaultStuffs()


            delay(500L)

            if(check){
                findNavController().navigate(R.id.action_splashScreen_to_mainFragment)
            }else{
                findNavController().navigate(R.id.action_splashScreen_to_helloFragment)
            }


        }

        myEyes()
        return binding!!.root
    }

    private fun myEyes() {

    }

    private fun defaultStuffs() {
        if(mViewModel.mPlayMusic.value == true){
            mViewModel.playAudio(mViewModel.getRandomSong())
        }

    }

    override fun onPause() {
        super.onPause()
        if (isRemoving) {

            if (binding != null) {
                binding!!.root.removeAllViews()
                binding = null
            }

        }
    }


}