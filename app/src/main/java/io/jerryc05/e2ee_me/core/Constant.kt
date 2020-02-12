package io.jerryc05.e2ee_me.core

import android.security.keystore.KeyProperties
import io.jerryc05.e2ee_me.BuildConfig
import okhttp3.OkHttpClient

const val CIPHER_ALGORITHM = KeyProperties.KEY_ALGORITHM_RSA
const val CIPHER_TRANSMISSION = CIPHER_ALGORITHM + '/' +
        "NONE" + '/' +
        KeyProperties.ENCRYPTION_PADDING_RSA_OAEP
const val RSA_KEY_SIZE = 2048
const val KEYSTORE_ALIAS = BuildConfig.APPLICATION_ID

val okHttpClient by lazy { OkHttpClient() }

const val repoName = "jerryc05/E2EEMe"
const val githubBotToken = "c78822bd80a7a3a2ee55559b968c55a7f95c714e"