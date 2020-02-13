package io.jerryc05.e2ee_me.core.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import io.jerryc05.e2ee_me.core.KEYSTORE_ALIAS
import io.jerryc05.e2ee_me.core.RSA_CIPHER_ALGORITHM
import io.jerryc05.e2ee_me.core.RSA_CIPHER_TRANSMISSION
import io.jerryc05.e2ee_me.core.RSA_KEY_SIZE
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

private val kpg: KeyPairGenerator by lazy {
  KeyPairGenerator.getInstance(RSA_CIPHER_ALGORITHM)
}

private val kgParamSpec by lazy {
  KeyGenParameterSpec.Builder(KEYSTORE_ALIAS,
          KeyProperties.PURPOSE_ENCRYPT or
                  KeyProperties.PURPOSE_DECRYPT)
}

private val cipher by lazy {
  Cipher.getInstance(RSA_CIPHER_TRANSMISSION)
}

internal fun generateRsaKeyPair(): KeyPair {
  kgParamSpec.setKeySize(RSA_KEY_SIZE)

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

internal fun encryptRsa(data: ByteArray, publicKey: PublicKey): ByteArray {
  cipher.init(Cipher.ENCRYPT_MODE, publicKey)
  return cipher.doFinal(data)
}


internal fun decryptRsa(data: ByteArray, privateKey: PrivateKey): ByteArray {
  cipher.init(Cipher.DECRYPT_MODE, privateKey)
  return cipher.doFinal(data)
}