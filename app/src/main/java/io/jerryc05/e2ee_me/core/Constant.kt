package io.jerryc05.e2ee_me.core

import android.security.keystore.KeyProperties
import io.jerryc05.e2ee_me.BuildConfig
import okhttp3.OkHttpClient

internal const val RSA_CIPHER_ALGORITHM = KeyProperties.KEY_ALGORITHM_RSA
internal const val AES_CIPHER_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
internal const val ECDH_KEY_EXCHANGE = "ECDH"
internal const val EC_KEYPAIR_ALGORITHM = KeyProperties.KEY_ALGORITHM_EC

internal const val RSA_CIPHER_TRANSMISSION = "" +
        RSA_CIPHER_ALGORITHM + '/' +
        "NONE" + '/' +
        KeyProperties.ENCRYPTION_PADDING_NONE
internal const val AES_CIPHER_TRANSMISSION = "" +
        AES_CIPHER_ALGORITHM + '/' +
        KeyProperties.BLOCK_MODE_CTR + '/' +
        KeyProperties.ENCRYPTION_PADDING_NONE

internal const val RSA_KEY_SIZE = 2048
internal const val EC_KEY_SIZE = 256
internal const val AES_KEY_SIZE = 128
internal const val KEYSTORE_ALIAS = BuildConfig.APPLICATION_ID

internal val okHttpClient by lazy { OkHttpClient() }

internal const val repoName = "jerryc05/E2EEMe"
internal const val authToken = "#\$`k8h{q7F6-cg7tZpq5_V`kLUXa1AYDr4h:\\cFi<+b.Fzyr4xu6#"