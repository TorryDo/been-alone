package com.torrydo.beenalone.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.torrydo.beenalone.CONSTANT
import com.torrydo.beenalone.R
import com.torrydo.beenalone.models.mUser
import com.torrydo.beenalone.utility.Utils
import com.torrydo.beenalone.vModel.MyViewModel
import com.torrydo.beenalone.vModel.MyViewmodelFactory

class MainActivity : AppCompatActivity() {

    private val mViewModel : MyViewModel by lazy {
        ViewModelProvider(this,MyViewmodelFactory(application)).get(MyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.noStatusBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        defaultStuffs()

        defaultUI()
        myEyes()
    }

    private fun myEyes() {
        mViewModel.mDarkMode.observe(this,{
            if(it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            window.statusBarColor = ContextCompat.getColor(this,R.color.system_background_darker)
        })
    }

    private fun defaultStuffs() {
        val pref = getSharedPreferences(CONSTANT.PREF_NAME,Context.MODE_PRIVATE)
        val firstLaunch : Boolean = pref.getBoolean(CONSTANT.FIRST_LAUNCH,false)
        if(!firstLaunch){
            Toast.makeText(this,"lauched = false",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"lauched = true",Toast.LENGTH_SHORT).show()
        }

        checkLoggedIn()

    }

    private fun defaultUI() {
        supportActionBar?.hide()
//        Utils.hideSystemUI(this)
        window.statusBarColor = ContextCompat.getColor(applicationContext,R.color.white)
    }

    private fun checkLoggedIn(){
        val user = mViewModel.firebaseAuth.currentUser
        if(user != null){
//            Toast.makeText(this,"logged in",Toast.LENGTH_SHORT).show()
            Log.i("_info",user.displayName.toString() + "\n" + user.email + "\n" + user.uid)

            mViewModel.myUser.value = mUser(user.displayName ?: "null",user.email ?: "null",user.uid)
        }else{
//            Toast.makeText(this,"not loggedIn",Toast.LENGTH_SHORT).show()
            Log.e("_info","null")
            mViewModel.myUser.value = null
        }
    }

    override fun onDestroy() {
        try{
            mViewModel.stopAudio()
        }catch (e:Exception){
            Log.e("BUGLOL","mainActiity onDestroy")
        }
        super.onDestroy()
    }


}