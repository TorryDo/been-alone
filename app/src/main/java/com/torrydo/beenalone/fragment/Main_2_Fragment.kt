package com.torrydo.beenalone.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.torrydo.beenalone.R
import com.torrydo.beenalone.adapter.Adapter_games
import com.torrydo.beenalone.databinding.FragmentMain2Binding
import com.torrydo.beenalone.interfaces.GodClick
import com.torrydo.beenalone.interfaces.GodTouch
import com.torrydo.beenalone.models.Mgame
import com.torrydo.beenalone.utility.Utils
import com.torrydo.beenalone.vModel.MyViewModel
import nl.joery.animatedbottombar.AnimatedBottomBar
import kotlin.math.abs


class Main_2_Fragment : Fragment() {

    private val mViewModel: MyViewModel by activityViewModels()
    private var binding: FragmentMain2Binding? = null

    private var mAdapter: Adapter_games? = null
    private var mLm: LinearLayoutManager? = null
    private var mGameList: ArrayList<Mgame>? = null

    private var deviceWidth = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMain2Binding.inflate(inflater, container, false)
        defaultUI()
        defaultStuffs()

//        binding!!.main2Button.setOnClickListener {
//            this.findNavController().navigate(R.id.action_mainFragment_to_game_2040_Fragment)
//        }

//        getPicture()


        return binding!!.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun defaultStuffs() {
        binding!!.main2Viewpager.also { vp2 ->
            mGameList = arrayListOf<Mgame>(
                Mgame(
                    getString(R.string.game_2048),
                    R.color.color_2048,
                    R.color.black,
                    false
                ),
                Mgame(
                    getString(R.string.game_beat_the_mouse),
                    R.color.color_btm,
                    R.color.black,
                    false
                )
            )
            mAdapter = Adapter_games(mGameList!!,
                object : GodTouch {
                    override fun onTouch(v: View) {
                        requireActivity().findViewById<ViewPager2>(R.id.main_viewPager).isUserInputEnabled =
                            false
                    }
                },
                object : GodClick {
                    override fun onClick(v: View) {
                        if (binding!!.main2Viewpager.currentItem == 0) {
                            findNavController().navigate(R.id.action_mainFragment_to_game_2040_Fragment)
                        }
                    }
                }
            )

            val cpt = CompositePageTransformer()
            cpt.addTransformer(MarginPageTransformer(8))
            cpt.addTransformer { page, position ->
                val f: Float = 1 - abs(position)
                page.scaleY = 0.8f + f * 0.2f
                page.scaleX = 0.8f + f * 0.2f
            }

            val dw = Utils.getDeviceWidth_and_Height(requireActivity(), 0)
            vp2.requestLayout()
            vp2.layoutParams.height = (dw.toFloat() / 2).toInt()


//            vp2.offscreenPageLimit = 3
            vp2.setPageTransformer(cpt)
            vp2.adapter = mAdapter

        }

        binding!!.root.setOnTouchListener { view, motionEvent ->
            requireActivity().findViewById<ViewPager2>(R.id.main_viewPager).isUserInputEnabled =
                true
            return@setOnTouchListener false
        }
    }

    private fun defaultUI() {
        deviceWidth = Utils.getDeviceWidth_and_Height(requireActivity(),0)

        try {
            binding!!.main2ShopBackground.also {
//                it.requestLayout()
                it.layoutParams.height = (deviceWidth.toFloat()*0.25).toInt()
            }
            binding!!.main2FeederBackground.also {
//                it.requestLayout()
                it.layoutParams.height = (deviceWidth.toFloat()*0.25).toInt()
            }
            binding!!.main2Viewpager.also {
                it.layoutParams.height = (deviceWidth.toFloat()*0.5).toInt()
            }
        }catch (e:Exception){
            Log.e("BUGLOL","main2 device width",e)
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