package com.torrydo.beenalone.models

data class mOption(
    val image : Int?,
    val title : String?,
    val content : String?,
    val hasSwitch : Boolean?,
    var choosed : Boolean?,
    val isAd : Boolean,
    val order : Int,
)
