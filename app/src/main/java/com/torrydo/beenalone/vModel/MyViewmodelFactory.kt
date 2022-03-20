package com.torrydo.beenalone.vModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyViewmodelFactory(val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MyViewModel::class.java)){
                return MyViewModel(application) as T
            }
            throw IllegalArgumentException()

    }
}