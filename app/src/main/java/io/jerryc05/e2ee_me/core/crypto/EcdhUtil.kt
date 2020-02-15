@file:Suppress("unused")

package io.jerryc05.e2ee_me.core.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import io.jerryc05.e2ee_me.core.*
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.KeyAgreement
import javax.crypto.SecretKey

private const val TAG = "ECDH"

private val keyPairGen by lazy {
  KeyPairGenerator.getInstance(EC_KEYPAIR_ALGORITHM, ANDROID_KEYSTORE_PROVIDER)
}

private val kgParamSpec by lazy {
  KeyGenParameterSpec.Builder(
          KEYSTORE_ALIAS,
          KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
  )
}

internal val keyAgreement by lazy {
  // WARNING! Cannot use Android KeyStore!
  KeyAgreement.getInstance(ECDH_KEY_EXCHANGE_ALGORITHM)
}

internal fun generateEcdhKeyPair(): KeyPair {
  kgParamSpec.setKeySize(EC_KEY_SIZE)

  return try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      kgParamSpec.setIsStrongBoxBacked(true)

    keyPairGen.initialize(kgParamSpec.build())
    keyPairGen.genKeyPair()
  } catch (e: Exception) {
    // Ignore this exception
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      kgParamSpec.setIsStrongBoxBacked(false)

    keyPairGen.initialize(kgParamSpec.build())
    keyPairGen.genKeyPair()
  }
}

internal fun exchangeEcdhAes(myPrivateKey: PrivateKey,
                             vararg otherPublicKeys: PublicKey): SecretKey {
  exchangeEcdhInternal(myPrivateKey, otherPublicKeys)
  return keyAgreement.generateSecret(AES_CIPHER_ALGORITHM)
}

internal fun exchangeEcdhRaw(myPrivateKey: PrivateKey,
                             vararg otherPublicKeys: PublicKey): ByteArray {
  exchangeEcdhInternal(myPrivateKey, otherPublicKeys)
  return keyAgreement.generateSecret()
}

private fun exchangeEcdhInternal(myPrivateKey: PrivateKey,
                                 otherPublicKeys: Array<out PublicKey>) {
  if (otherPublicKeys.isEmpty())
    throw Exception("`otherPublicKeys` cannot be empty!")

  keyAgreement.init(myPrivateKey)

  otherPublicKeys.sort()
  for (pubKey in otherPublicKeys.sliceArray(
          0 until otherPublicKeys.size - 1))
    keyAgreement.doPhase(pubKey, false)

  keyAgreement.doPhase(otherPublicKeys.last(), true)
}