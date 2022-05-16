package edu.miu.quizapp.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class Constants() {

    var PRIVATE_MODE = 0
    private val PREF_NAME = "quizapp-welcome"
    private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"

    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var _context: Context? = null

    @SuppressLint("CommitPrefEdits")
    constructor(context: Context?) : this() {
        _context = context
        pref = _context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref?.edit()
    }

    fun launchFirstTime(isFirstTime: Boolean) {
        editor?.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor?.commit()
    }

    fun checkLaunch(): Boolean {
        return pref!!.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }
}