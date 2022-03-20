package com.torrydo.beenalone.vModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.torrydo.beenalone.CONSTANT
import com.torrydo.beenalone.R
import com.torrydo.beenalone.models.mUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class MyViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    var lonelyDate = MutableLiveData<String>()
    var lonelyDay = 1
    var lonelyMonth = 1
    var lonelyYear = 2020

    var currentDay = 1
    var currentMonth = 1
    var currentYear = 2020
    var currentDate = "$currentDay/$currentMonth/$currentYear"

    var restDayOfYear = 0
    var currentDayOfYear = 0


    var mName = MutableLiveData<String>()
    var mLevel = MutableLiveData<Int>()
    var mExp = MutableLiveData<Int>()
    var mRank = MutableLiveData<String>()
    var mCoin = MutableLiveData<Int>()
    var mLonelyFor = MutableLiveData<String>()
    var mRankIcon = MutableLiveData<Int>()
    var mDarkMode = MutableLiveData<Boolean>()
    var mPlayMusic = MutableLiveData<Boolean>()

    var mPref: SharedPreferences =
        context.getSharedPreferences(CONSTANT.PREF_NAME, Context.MODE_PRIVATE)
    var motionChecker = MutableLiveData<Boolean>()

    var timer = MutableLiveData<Boolean>()
    val cdt = object : CountDownTimer(60000, 1000) {
        override fun onTick(p0: Long) {

        }

        override fun onFinish() {
            timer.value = true

            val userCoinn: Int = mCoin.value ?: 0
            var newCoin = 0
            newCoin = if (userCoinn >= 2147000000) {
                2147000000
            } else {
                userCoinn + Random.nextInt(50, 101)
            }
            mPref.edit().putInt(CONSTANT.USER_CURRENCY, newCoin).apply()
            mCoin.value = newCoin

            //--------------------------

            val userexpp: Int = mExp.value ?: 0
            var newExp = 0
            newExp = if (userexpp >= 2147000000) {
                2147000000
            } else {
                userexpp + Random.nextInt(1000, 2000)
            }

            mPref.edit().putInt(CONSTANT.USER_EXP, newExp).apply()
            mExp.value = newExp
        }
    }

    init {
        val calendar = Calendar.getInstance()

        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        currentMonth = calendar.get(Calendar.MONTH) + 1
        currentYear = calendar.get(Calendar.YEAR)

        lonelyDay = calendar.get(Calendar.DAY_OF_MONTH)
        lonelyMonth = calendar.get(Calendar.MONTH) + 1
        lonelyYear = calendar.get(Calendar.YEAR)

        refrehStatus()

//        motionChecker.value = mPref.getBoolean(CONSTANT.MOTION_CHECKER,false)
        cdt.start()
    }

    val myUser = MutableLiveData<mUser>()
    val firebaseAuth = Firebase.auth
    fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return  // already logged out
        }
        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/permissions/",
            null,
            HttpMethod.DELETE
        ) {
            LoginManager.getInstance().logOut()
        }.executeAsync()
    }


    private var mediaPlayer: MediaPlayer? = null
    var audio_stopped = MutableLiveData<Boolean>()
    var audio_paused = MutableLiveData<Boolean>()
    val audioLength = MutableLiveData<Int>()
    val audioDuration = MutableLiveData<Int>()
    fun playAudio(song: Int) {
        if(mediaPlayer!=null){
            mediaPlayer = null
        }
        mediaPlayer = MediaPlayer.create(context, song)
        viewModelScope.launch(Dispatchers.IO) {
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.seekTo(0)
            withContext(Dispatchers.Main) {
                audioDuration.value = mediaPlayer?.duration ?: 0
                audio_paused.value = false
                audio_stopped.value = false
            }
            mediaPlayer!!.start()

            audioProgress()

            for (i in 1..10) {
                delay(300L)
                try {
                    mediaPlayer!!.setVolume((i.toFloat()) / 10, (i.toFloat()) / 10)
                }catch (e: Exception){
                    Log.e("notImportant", e.cause.toString())
                }

            }
        }
    }

    val songsArrayList = arrayListOf(
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
    fun getRandomSong() : Int{
        val max = songsArrayList.size
        return songsArrayList[Random.nextInt(0,max)]
    }

    fun resumeAudio() {
        if (mediaPlayer != null) {
            audioLength.value?.let { mediaPlayer!!.seekTo(it) }
            mediaPlayer!!.start()
            audio_stopped.value = false
            audio_paused.value = false
        }

    }

    fun pauseAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer!!.isPlaying) {
                audioLength.value = mediaPlayer!!.currentPosition
                mediaPlayer!!.pause()
                audio_paused.value = true
            }
        }
    }

    fun stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
            mediaPlayer = null
            audio_paused.value = true
            audio_stopped.value = true

            audioLength.value = 0
        }
    }

    //    var audio_run = MutableLiveData<Boolean>()
    fun checkAudio(): Boolean {
        var b = false
        if (mediaPlayer != null) {
            try {
                b = mediaPlayer!!.isPlaying
            }catch (e: IllegalStateException){
                Log.e("notImportant", e.cause.toString())
            }
        }
        return b
    }

    private fun audioProgress() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000L)

                if (audio_stopped.value ?: false == false || checkAudio()) {
                    withContext(Dispatchers.Main) {
                        audioLength.value = mediaPlayer?.currentPosition ?: 0
                    }
                } else {
                    break
                }

                if (audioLength.value!! >= audioDuration.value!!) {
                    break
                }
            }
        }
    }


    fun refrehStatus() {
        mName.value = mPref.getString(CONSTANT.USER_NAME,"Null")

        mExp.value = mPref.getInt(CONSTANT.USER_EXP, 0)
        mLevel.value = mPref.getInt(CONSTANT.USER_LEVEL, 1)
        mRank.value = mPref.getString(CONSTANT.USER_RANK, context.getString(R.string.human))
        mCoin.value = mPref.getInt(CONSTANT.USER_CURRENCY, 0)
        mRankIcon.value = mPref.getInt(CONSTANT.USER_RANK_ICON,CONSTANT.PEASANT)
        mPlayMusic.value = mPref.getBoolean(CONSTANT.PLAY_MUSIC,true)
        mDarkMode.value = mPref.getBoolean(CONSTANT.DARKMODE,false)

        lonelyDate.value =
            mPref.getString(CONSTANT.LONELY_DATE, "$lonelyDay/$lonelyMonth/$lonelyYear")

        val tempArrs = lonelyDate.value?.split("/")

        if (!tempArrs.isNullOrEmpty()) {
            lonelyDay = tempArrs[0].toInt()
            lonelyMonth = tempArrs[1].toInt()
            lonelyYear = tempArrs[2].toInt()
        }

        restDayOfYear = getDayFromYear("$lonelyYear-$lonelyMonth-$lonelyDay", lonelyYear)
        currentDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

        howManyDay()

    }

    fun saveImageToExternalStorage(bitmap: Bitmap,resourceName : String){
        Thread {
            var fos: FileOutputStream? = null

            try {
                val externalFileDir = context.getExternalFilesDir(CONSTANT.FILE_FOLDER)!!
                val file = File(externalFileDir, resourceName)

                Log.d("_info", externalFileDir.absolutePath)

//
                fos = FileOutputStream(file)
                val byteArrOP = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrOP)
                fos.write(byteArrOP.toByteArray())

            } catch (e: Exception) {
                Log.e("BUGLOL", "cant save image to externalstorage")
            } finally {
                fos?.close()
            }
        }.start()
    }

    fun getCurrentLvl(exp : Long = mExp.value?.toLong() ?:0) : Int{
        val lvl = (exp / CONSTANT.level_ratio).toInt()
        if(exp <= 0.toLong() || lvl < 1) return 1
        return lvl
    }


    fun howManyDay() {
        var mResult = "null"

        var tempDay: Int = 0
        var checkRestDay = if (currentYear % 4 == 0) {
            currentDayOfYear - (366 - restDayOfYear)
        } else {
            currentDayOfYear - (365 - restDayOfYear)
        }

        if (lonelyYear < currentYear) {

            tempDay = restDayOfYear + currentDayOfYear
            for (i in lonelyYear + 1 until currentYear) {
                tempDay += if (i % 4 == 0) {
                    366
                } else {
                    365
                }
            }
            mResult = if (tempDay <= 0) {
                "0 ${context.getString(R.string.day)}"
            } else if (tempDay == 1) {
                "1 ${context.getString(R.string.day)}"
            } else {
                "$tempDay ${context.getString(R.string.days)}"
            }

        } else if (lonelyYear == currentYear && checkRestDay > 0) {
            tempDay = checkRestDay
//            Log.e("_info","checkrestday = $checkRestDay , currentDofY = $currentDayOfYear , restDofY = $restDayOfYear")
            mResult = if (tempDay <= 0) {
                "0 ${context.getString(R.string.day)}"
            } else if (tempDay == 1) {
                "1 ${context.getString(R.string.day)}"
            } else {
                "$tempDay ${context.getString(R.string.days)}"
            }
        } else {
            mResult = "0 ${context.getString(R.string.day)}"
        }


        mLonelyFor.value = mResult
    }

    fun aloneFor() {

        var mResult = ""
        var checkRestDay = if (currentYear % 4 == 0) {
            currentDayOfYear - (366 - restDayOfYear)
        } else {
            currentDayOfYear - (365 - restDayOfYear)
        }

        if (lonelyYear < currentYear) {
            var td = restDayOfYear + currentDayOfYear
//        Log.i("_info", "restD = $restDayOfYear -- currentD = $currentDayOfYear  $td")
            var ty = if (currentYear - lonelyYear <= 0) {
                0
            } else if (currentYear - lonelyYear  == 1) {
                1
            } else {
                currentYear - lonelyYear
            }

            if (td > 365) {
                td -= 365
            } else {
                ty -= 1
            }

            val tempD: String = if (td <= 0) {
                "0 ${context.getString(R.string.day)}"
            } else if (td == 1) {
                "1 ${context.getString(R.string.day)}"
            } else {
                "$td ${context.getString(R.string.days)}"
            }

            val tempY: String = if (ty <= 0) {
                "0 ${context.getString(R.string.year)}"
            } else if (ty == 1) {
                "1 ${context.getString(R.string.year)}"
            } else {
                "$ty ${context.getString(R.string.years)}"
            }

            mResult = "$tempY & $tempD"
        } else if (currentYear == lonelyYear && checkRestDay > 0) {
            var dayInt = 0
            var dayString = ""

            if (currentYear % 4 == 0) {
                dayInt = currentDayOfYear - (366 - restDayOfYear)
            } else {
                dayInt = currentDayOfYear - (365 - restDayOfYear)
            }

            if (dayInt <= 1) {
                dayString = dayInt.toString() + context.getString(R.string.day)
            } else {
                dayString = dayInt.toString() + context.getString(R.string.days)
            }
            mResult = "0 ${context.getString(R.string.year)} $dayString "

        } else {
            mResult = "0 ${context.getString(R.string.year)} 0 ${context.getString(R.string.day)}"
        }

        mLonelyFor.value = mResult
    }

    fun getDayFromYear(mDate: String, mLonelyYear: Int): Int {
        var mResult = 0
        try {

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(mDate)
            val cal = Calendar.getInstance()
            cal.time = date

            if (mLonelyYear % 4 == 0) {
                mResult = (366 - cal.get(Calendar.DAY_OF_YEAR))
            } else {
                mResult = (365 - cal.get(Calendar.DAY_OF_YEAR))
            }
        } catch (e: Exception) {
            Log.e("buglol", "get day from year error")
        }

        return mResult
    }



}