package com.torrydo.beenalone.utility

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.SpannableStringBuilder
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.core.text.*
import com.torrydo.beenalone.R


class Utils {

    companion object {


        fun getTextWithPartialColor(
            color: Int,
            normalText: String?,
            colorText: String?,
            underline: Boolean,
            bold: Boolean
        ): SpannableStringBuilder {
            val ssb = SpannableStringBuilder()
                .append(normalText)
                .color(color) {
                    if (underline) {
                        underline {
                            if (bold) {
                                bold { append(colorText) }
                            } else {
                                append(colorText)
                            }
                        }
                    } else {
                        if (bold) {
                            bold { append(colorText) }
                        } else {
                            append(colorText)
                        }
                    }
                }
//                .bold { underline { color(color){append(colorText)} } }
//                .scale(0.5F) { append("Text at half size ") }
//                .backgroundColor(color) { append("Background green") }

            return ssb
        }

        fun getDeviceWidth_and_Height(activity: Activity, num: Int): Int {
            var returnNum = 0

            val outMetrics = DisplayMetrics()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val display = activity.display
                display?.getRealMetrics(outMetrics)

                if (num == 0) {
                    returnNum = outMetrics.widthPixels
                } else {
                    returnNum = outMetrics.heightPixels
                }

            } else {
                @Suppress("DEPRECATION")
                val display = activity.windowManager.defaultDisplay
                @Suppress("DEPRECATION")
                display.getRealMetrics(outMetrics)

                if (num == 0) {
                    returnNum = outMetrics.widthPixels
                } else {
                    returnNum = outMetrics.heightPixels
                }

            }
            Log.i(
                "screenInfo",
                "width = ${outMetrics.widthPixels} & height = ${outMetrics.heightPixels}"
            )

            return returnNum

        }

        fun hideKeyboard(view: View, context: Context) {
            var imm: InputMethodManager? = null
            if (imm == null) {
                imm =
                    context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.rootView.windowToken, 0)
            } else {
                imm.hideSoftInputFromWindow(view.rootView.windowToken, 0)
            }
        }


        fun open_tiny_flyup(view: View){
            var animation : Animation? = null
            if(animation == null){
                animation = AnimationUtils.loadAnimation(view.context,R.anim.open_tiny_flyup)
            }

            animation!!.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(p0: Animation?) {
//                    view.visibility = View.INVISIBLE
                }

                override fun onAnimationEnd(p0: Animation?) {
//                    view.visibility = View.
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })

            view.startAnimation(animation)
        }

        fun ifinite_rotate(view: View) {

            val rotate = RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotate.duration = 10000
            rotate.repeatCount = Animation.INFINITE
            rotate.interpolator = LinearInterpolator()

            view.startAnimation(rotate)

        }

        fun exit_fade(context: Context, view: View) {
            var animation: Animation? = null

            if (animation == null) {
                animation = AnimationUtils.loadAnimation(context, R.anim.exit_fade)
            }
            animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    view.visibility = View.INVISIBLE
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })
            view.startAnimation(animation)

        }

        fun open_fade(context: Context, view: View) {
            var animation: Animation? = null

            if (animation == null) {
                animation = AnimationUtils.loadAnimation(context, R.anim.open_fade)
            }

            animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    view.visibility = View.INVISIBLE
                }

                override fun onAnimationEnd(p0: Animation?) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })
            view.startAnimation(animation)

        }

        fun open_fade_fast(context: Context, view: View) {
            var animation: Animation? = null

            if (animation == null) {
                animation = AnimationUtils.loadAnimation(context, R.anim.open_fade_fast)
            }

            animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    view.visibility = View.INVISIBLE
                }

                override fun onAnimationEnd(p0: Animation?) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })
            view.startAnimation(animation)

        }

        fun exit_tiny_scroll_down(context: Context, view: View) {
            var animation: Animation? = null

            if (animation == null) {
                animation = AnimationUtils.loadAnimation(context, R.anim.exit_tiny_scroll_down)
            }

            animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    view.visibility = View.INVISIBLE
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })
            view.startAnimation(animation)

        }

        fun open_tiny_scroll_down(context: Context, view: View) {
            var animation: Animation? = null

            if (animation == null) {
                animation = AnimationUtils.loadAnimation(context, R.anim.open_tiny_scroll_down)
            }

            animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    view.visibility = View.INVISIBLE
                }

                override fun onAnimationEnd(p0: Animation?) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })
            view.startAnimation(animation)


        }

        fun hideSystemUI(activity: Activity) {

//        this.requestWindowFeature()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                activity.window.setDecorFitsSystemWindows(false)
                val controller = activity.window.insetsController
                if (controller != null) {
                    controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    controller.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } else {
                // All below using to hide navigation bar
                val currentApiVersion = Build.VERSION.SDK_INT
                val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

                // This work only for android 4.4+
                if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
                    activity.window.decorView.systemUiVisibility = flags
                    // Code below is to handle presses of Volume up or Volume down.
                    // Without this, after pressing volume buttons, the navigation bar will
                    // show up and won't hide
                    val decorView = activity.window.decorView
                    decorView.setOnSystemUiVisibilityChangeListener { visibility: Int ->
                        if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                            decorView.systemUiVisibility = flags
                        }
                    }
                }
            }
        }
    }

}