package io.jerryc05.e2ee_me.core.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import io.jerryc05.e2ee_me.core.AES_CIPHER_ALGORITHM
import io.jerryc05.e2ee_me.core.AES_CIPHER_TRANSMISSION
import io.jerryc05.e2ee_me.core.AES_KEY_SIZE
import io.jerryc05.e2ee_me.core.KEYSTORE_ALIAS
import io.jerryc05.e2ee_me.core.log.logA
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import kotlin.random.Random

private const val TAG = "AES"

private val kpg: KeyPairGenerator by lazy {
  KeyPairGenerator.getInstance(AES_CIPHER_ALGORITHM)
}

private val kgParamSpec by lazy {
  KeyGenParameterSpec.Builder(KEYSTORE_ALIAS,
          KeyProperties.PURPOSE_ENCRYPT or
                  KeyProperties.PURPOSE_DECRYPT)
}

private val cipher by lazy {
  Cipher.getInstance(AES_CIPHER_TRANSMISSION)
}

internal fun generateAesKey(): KeyPair {
  kgParamSpec.setKeySize(AES_KEY_SIZE)

  return try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      kgParamSpec.setIsStrongBoxBacked(true)

    kpg.initialize(kgParamSpec.build())
    kpg.genKeyPair()
  } catch (e: Exception) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      kgParamSpec.setIsStrongBoxBacked(false)

    kpg.initialize(kgParamSpec.build())
    kpg.genKeyPair()
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
  return cipher.doFinal(data)
}


internal fun decryptAes(data: ByteArray,
                        key: SecretKey,
                        iv: IvParameterSpec): ByteArray {
  logA(TAG, "decrypt iv = ${iv.iv?.contentToString()}")
  cipher.init(Cipher.DECRYPT_MODE, key, iv)
  return cipher.doFinal(data)
}