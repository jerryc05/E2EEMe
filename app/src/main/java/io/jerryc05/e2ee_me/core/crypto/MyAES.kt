package io.jerryc05.e2ee_me.core.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import io.jerryc05.e2ee_me.core.AES_CIPHER_ALGORITHM
import io.jerryc05.e2ee_me.core.AES_CIPHER_TRANSMISSION
import io.jerryc05.e2ee_me.core.AES_KEY_SIZE
import io.jerryc05.e2ee_me.core.KEYSTORE_ALIAS
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

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

internal fun generateAESKey(): KeyPair {
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

internal fun encryptAES(data: ByteArray,
                        key: SecretKey,
                        iv: IvParameterSpec): ByteArray {
  cipher.init(Cipher.ENCRYPT_MODE, key, iv)
  return cipher.doFinal(data)
}


internal fun decryptAES(data: ByteArray,
                        key: SecretKey,
                        iv: IvParameterSpec): ByteArray {
  cipher.init(Cipher.DECRYPT_MODE, key, iv)
  return cipher.doFinal(data)
}