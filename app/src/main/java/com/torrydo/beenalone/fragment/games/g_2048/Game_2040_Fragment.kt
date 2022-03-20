package com.torrydo.beenalone.fragment.games.g_2048

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.torrydo.beenalone.R
import com.torrydo.beenalone.databinding.FragmentGame2048Binding
import com.torrydo.beenalone.utility.Utils
import com.torrydo.beenalone.vModel.MyViewModel
import kotlin.math.abs


class Game_2040_Fragment : Fragment() {

    private val mViewModel: MyViewModel by activityViewModels()
    private var binding: FragmentGame2048Binding? = null

    private var deviceWidth = 0


    private var mLastBackPress: Long = 0
    private var mLastTouch: Long = 0
    private var mWebView: WebView? = null
    private var pressBackToast: Toast? = null


    @SuppressLint("SetJavaScriptEnabled", "SdCardPath")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGame2048Binding.inflate(inflater, container, false)
        defaultStuffs()
        defaultUI()


//        requestWindowFeature(1)
//        applyFullScreen(isFullScreen)
//        var isOrientationEnabled = false
//        try {
//            isOrientationEnabled =
//                if (Settings.System.getInt(contentResolver, "accelerometer_rotation") === 1) {
//                    true
//                } else {
//                    false
//                }
//        } catch (e: SettingNotFoundException) {
//        }
//        val screenLayout = resources.configuration.screenLayout and 15
//        if ((screenLayout == 3 || screenLayout == 4) && isOrientationEnabled) {
//            requestedOrientation = 4
//        }
        mWebView = binding!!.mainWebView as WebView
        val settings = mWebView!!.settings
        val packageName = requireActivity().packageName
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.databasePath = "/data/data/$packageName/databases"
        if (savedInstanceState != null) {
            mWebView!!.restoreState(savedInstanceState)
        } else {
            mWebView!!.loadUrl("file:///android_asset/2048/index.html")
        }

//        mWebView!!.setOnTouchListener { v, event ->
//
//            /* class com.uberspot.a2048.MainActivity.AnonymousClass1 */
//            var toggledFullScreen = true
//            val currentTime = System.currentTimeMillis()
//            if (event.action != 2) {
//                if (event.action == 1 && abs(currentTime - mLastTouch) > mTouchThreshold) {
//                    if (isFullScreen) {
//                        toggledFullScreen = false
//                    }
//                    saveFullScreen(toggledFullScreen)
//                    applyFullScreen(toggledFullScreen)
//                } else if (event.action == 0) {
//                    mLastTouch = currentTime
//                }
//            }
//            false
//        }

        clicker()
        return binding!!.root
    }

    private fun clicker() {
        binding!!.g2048Back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun defaultStuffs() {
        pressBackToast =
            Toast.makeText(
                requireContext(),
                R.string.press_back_again_to_exit,
                Toast.LENGTH_SHORT
            )

    }

    private fun defaultUI() {
        deviceWidth = Utils.getDeviceWidth_and_Height(requireActivity(),0)

        binding!!.mainWebView.also {
            it.requestLayout()

            it.layoutParams.height = ((deviceWidth.toLong()*1.5)).toInt()
        }
    }



    /* access modifiers changed from: protected */
    override fun onSaveInstanceState(outState: Bundle) {
        mWebView!!.saveState(outState)
    }

//    fun onBackPressed() {
//        val currentTime = System.currentTimeMillis()
//        if (Math.abs(currentTime - mLastBackPress) > mBackPressThreshold) {
//            pressBackToast!!.show()
//            mLastBackPress = currentTime
//            return
//        }
//        pressBackToast!!.cancel()
//        requireActivity().onBackPressed()
//    }

    companion object {
        private const val DEF_FULLSCREEN = true
        private const val IS_FULLSCREEN_PREF = "is_fullscreen_pref"
        private const val mBackPressThreshold: Long = 3500
        private const val mTouchThreshold: Long = 2000
    }
}
