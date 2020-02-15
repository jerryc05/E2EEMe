package io.jerryc05.e2ee_me.core.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import io.jerryc05.e2ee_me.core.*
import io.jerryc05.e2ee_me.core.log.logA
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import kotlin.random.Random

private const val TAG = "AES"

private val kg by lazy {
  KeyGenerator.getInstance(AES_CIPHER_ALGORITHM, ANDROID_KEYSTORE_PROVIDER)
}

private val kgParamSpec by lazy {
  KeyGenParameterSpec.Builder(KEYSTORE_ALIAS,
          KeyProperties.PURPOSE_ENCRYPT or
                  KeyProperties.PURPOSE_DECRYPT)
}

private val cipher by lazy {
  Cipher.getInstance(AES_CIPHER_TRANSMISSION, ANDROID_KEYSTORE_PROVIDER)
}

internal fun generateAesKey(): SecretKey {
  kgParamSpec.setKeySize(AES_KEY_SIZE)

  return try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      kgParamSpec.setIsStrongBoxBacked(true)

    kg.init(kgParamSpec.build())
    kg.generateKey()
  } catch (e: Exception) {
    // Ignore this exception
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      kgParamSpec.setIsStrongBoxBacked(false)

    kg.init(kgParamSpec.build())
    kg.generateKey()
  }
}

internal fun generateIv(): ByteArray {
  val ivByte = ByteArray(AES_KEY_SIZE / Byte.SIZE_BITS)
  Random.nextBytes(ivByte)
  return ivByte
}

internal fun encryptAes(data: ByteArray,
                        key: SecretKey,
                        iv: IvParameterSpec): ByteArray {
  logA(TAG, "encrypt iv = ${iv.iv?.contentToString()}")
  cipher.init(Cipher.ENCRYPT_MODE, key, iv)
  val result = cipher.doFinal(data)
  logA(TAG, "encrypt-ed = ${result?.contentToString()}")
  return result
}


internal fun decryptAes(data: ByteArray,
                        key: SecretKey,
                        iv: IvParameterSpec): ByteArray {
  logA(TAG, "decrypt iv = ${iv.iv?.contentToString()}")
  logA(TAG, "to-decrypt = ${data.contentToString()}")
  cipher.init(Cipher.DECRYPT_MODE, key, iv)
  return cipher.doFinal(data)
}
