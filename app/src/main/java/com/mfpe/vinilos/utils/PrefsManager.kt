package com.mfpe.vinilos.utils

import android.content.Context

class PrefsManager private constructor(private val mCtx: Context) {

    fun saveUserType(userType: String) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_type", userType)
        editor.apply()
    }

    val getUserType: String?
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("user_type", "")
        }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "my_shared_prefs"
        private var mInstance: PrefsManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): PrefsManager {
            if (mInstance == null) {
                mInstance = PrefsManager(mCtx)
            }
            return mInstance as PrefsManager
        }
    }
}