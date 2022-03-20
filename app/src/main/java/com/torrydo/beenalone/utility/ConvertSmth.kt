package com.torrydo.beenalone.utility

import android.content.res.Resources

class ConvertSmth {

    companion object {
        fun getMyPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }

        fun getMyDp(px: Int): Int {
            return (px / Resources.getSystem().displayMetrics.density).toInt()
        }
    }

}