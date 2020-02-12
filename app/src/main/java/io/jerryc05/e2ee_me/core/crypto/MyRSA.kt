package io.jerryc05.e2ee_me.core.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import io.jerryc05.e2ee_me.core.CIPHER_ALGORITHM
import io.jerryc05.e2ee_me.core.CIPHER_TRANSMISSION
import io.jerryc05.e2ee_me.core.KEYSTORE_ALIAS
import io.jerryc05.e2ee_me.core.RSA_KEY_SIZE
import java.security.*
import javax.crypto.Cipher

private val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(
        CIPHER_ALGORITHM, "AndroidKeyStore")

private val kgParamSpec = KeyGenParameterSpec.Builder(KEYSTORE_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)

fun generateKey(): KeyPair {
  kgParamSpec.setKeySize(RSA_KEY_SIZE)

  return try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      kgParamSpec.setIsStrongBoxBacked(true)

    kpg.initialize(kgParamSpec.build())
    kpg.genKeyPair()
  } catch (e: KeyStoreException) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      kgParamSpec.setIsStrongBoxBacked(false)

    kpg.initialize(kgParamSpec.build())
    kpg.genKeyPair()
  }
}

fun encrypt(data: ByteArray, publicKey: PublicKey): ByteArray {
  val cipher = Cipher.getInstance(CIPHER_TRANSMISSION)
  cipher.init(Cipher.ENCRYPT_MODE, publicKey)
  return cipher.doFinal(data)
}


fun encrypt(data: ByteArray, privateKey: PrivateKey): ByteArray {
  val cipher = Cipher.getInstance(CIPHER_TRANSMISSION)
  cipher.init(Cipher.DECRYPT_MODE, privateKey)
  return cipher.doFinal(data)
}