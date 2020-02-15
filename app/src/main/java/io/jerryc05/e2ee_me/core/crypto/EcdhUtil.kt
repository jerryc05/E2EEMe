@file:Suppress("unused")

package io.jerryc05.e2ee_me.core.crypto

import io.jerryc05.e2ee_me.core.AES_CIPHER_ALGORITHM
import io.jerryc05.e2ee_me.core.ECDH_KEY_EXCHANGE_ALGORITHM
import io.jerryc05.e2ee_me.core.EC_CURVE_STD
import io.jerryc05.e2ee_me.core.EC_KEYPAIR_ALGORITHM
import io.jerryc05.e2ee_me.core.log.logE
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.ECGenParameterSpec
import javax.crypto.KeyAgreement
import javax.crypto.SecretKey

private const val TAG = "ECDH"

private val keyPairGen by lazy {
  // WARNING! Cannot use Android KeyStore!
  KeyPairGenerator.getInstance(EC_KEYPAIR_ALGORITHM)
}

private val kgParamSpec by lazy {
  ECGenParameterSpec(EC_CURVE_STD)
}

internal val keyAgreement by lazy {
  // WARNING! Cannot use Android KeyStore!
  KeyAgreement.getInstance(ECDH_KEY_EXCHANGE_ALGORITHM)
}

internal fun generateEcdhKeyPair(): KeyPair? {
  return try {
    keyPairGen.initialize(kgParamSpec)
    keyPairGen.genKeyPair()
  } catch (e: Exception) {
    logE(TAG, "generateEcdhKeyPair: ", e)
    null
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