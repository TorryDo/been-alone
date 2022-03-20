package com.torrydo.beenalone.bottomFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.torrydo.beenalone.R
import com.torrydo.beenalone.adapter.Adapter_songs
import com.torrydo.beenalone.databinding.FragmentDashboardBottomBinding
import com.torrydo.beenalone.interfaces.GodTouch
import com.torrydo.beenalone.utility.Utils
import com.torrydo.beenalone.vModel.MyViewModel

class DashboardBottomFragment : BottomSheetDialogFragment() {

    private val mViewModel: MyViewModel by activityViewModels()
    private var binding: FragmentDashboardBottomBinding? = null

    private var mAdapter: Adapter_songs? = null
    private var lm: LinearLayoutManager? = null
    private val songarrays = arrayListOf<Int>(
        R.raw.bensound_cute,
        R.raw.bensound_buddy,
        R.raw.bensound_clearday,
        R.raw.bensound_creativeminds,
        R.raw.bensound_funnysong,
        R.raw.bensound_jazzcomedy,
        R.raw.bensound_littleidea,
        R.raw.bensound_retrosoul,
        R.raw.bensound_ukulele
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBottomBinding.inflate(inflater, container, false)
        defaultUI()
        defaultStuffs()

        clicker()
        myEyes()
        return binding!!.root
    }

    private fun myEyes() {

        mViewModel.audio_stopped.observe(viewLifecycleOwner,{
            if(it){
                binding!!.dashStop.alpha = 0.5f
                binding!!.dashStop.isEnabled = false

                binding!!.dashPause.visibility = View.INVISIBLE
                binding!!.dashPlay.visibility = View.VISIBLE
            }else{
                binding!!.dashStop.alpha = 1f
                binding!!.dashStop.isEnabled = true

                binding!!.dashPause.visibility = View.VISIBLE
                binding!!.dashPlay.visibility = View.INVISIBLE
            }
        })

        mViewModel.audio_paused.observe(viewLifecycleOwner,{
            if(it){
                binding!!.dashPause.visibility = View.INVISIBLE
                binding!!.dashPlay.visibility = View.VISIBLE
            }else{
                binding!!.dashPause.visibility = View.VISIBLE
                binding!!.dashPlay.visibility = View.INVISIBLE
            }
        })

        mViewModel.audioLength.observe(viewLifecycleOwner, { len ->
            binding!!.dashProgress.progress = len
        })
        mViewModel.audioDuration.observe(viewLifecycleOwner, {
            binding!!.dashProgress.max = it
        })
    }

    private fun clicker() {
        binding!!.dashPause.setOnClickListener {
            mViewModel.pauseAudio()

            it.visibility = View.INVISIBLE
            binding!!.dashPlay.visibility = View.VISIBLE

        }

        binding!!.dashStop.setOnClickListener {
            mViewModel.stopAudio()

            binding!!.dashStop.alpha = 0.5f
            binding!!.dashStop.isEnabled = false

            mViewModel.audioLength.value = 0

            binding!!.dashPause.visibility = View.INVISIBLE
            binding!!.dashPlay.also {
                it.visibility = View.VISIBLE
            }

        }

        binding!!.dashPlay.setOnClickListener {
            if (mViewModel.audio_stopped.value ?: false) {
                mViewModel.playAudio(mViewModel.getRandomSong())
            } else {
                mViewModel.resumeAudio()
            }

            it.visibility = View.INVISIBLE
            binding!!.dashPause.visibility = View.VISIBLE
            binding!!.dashStop.also { stop ->
                stop.alpha = 1f
                stop.isEnabled = true
            }
        }
    }

    private fun defaultStuffs() {

        binding!!.dashRecyclerSongs.also { recycler ->
            mAdapter = Adapter_songs(
                songarrays,
                object : GodTouch {
                    override fun onTouch(v: View) {
                        val tempPosition = binding!!.dashRecyclerSongs.getChildLayoutPosition(v)
                        mViewModel.stopAudio()
                        mViewModel.playAudio(songarrays[tempPosition])

                    }
                }
            )

            lm = LinearLayoutManager(requireContext())

            recycler.setHasFixedSize(true)
            recycler.layoutManager = lm
            recycler.adapter = mAdapter

            recycler.requestLayout()
            recycler.layoutParams.height =
                (Utils.getDeviceWidth_and_Height(requireActivity(), 0).toFloat() * 0.8).toInt()
            recycler.layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT


        }

    }

    private fun defaultUI() {

        if (mViewModel.audio_stopped.value ?: false == true) {
            binding!!.dashStop.also {
                it.alpha = 0.5f
                it.isEnabled = false
            }
        } else {
            binding!!.dashStop.also {
                it.alpha = 1f
                it.isEnabled = true
            }
        }
//        if (mViewModel.checkAudio()) {
//            binding!!.dashPause.visibility = View.VISIBLE
//            binding!!.dashPlay.visibility = View.INVISIBLE
//
//
//        } else {
//            binding!!.dashPause.visibility = View.INVISIBLE
//            binding!!.dashPlay.visibility = View.VISIBLE
//
//        }
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

}