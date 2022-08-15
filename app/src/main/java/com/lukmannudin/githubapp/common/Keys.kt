package com.lukmannudin.githubapp.common

import android.util.Base64

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    private external fun getTokenNative(): String
    private external fun getUserAgentNative(): String

    fun getToken() = getDecodedString(getTokenNative())
    fun getUserAgent() = getDecodedString(getUserAgentNative())

    private fun getDecodedString(key: String): String {
        return String(Base64.decode(key, Base64.DEFAULT), Charsets.UTF_8)
    }
}